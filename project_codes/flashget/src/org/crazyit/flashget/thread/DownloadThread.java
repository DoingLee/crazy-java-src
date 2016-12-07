package org.crazyit.flashget.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.flashget.ContextHolder;
import org.crazyit.flashget.DownloadContext;
import org.crazyit.flashget.exception.URLException;
import org.crazyit.flashget.object.Part;
import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.state.Pause;
import org.crazyit.flashget.util.FileUtil;

public class DownloadThread extends Thread {

	private URL url;
	
	private RandomAccessFile raf;
	
	//下载的资源对象
	private Resource resource;
	
	//本线程需要下载的块
	private Part part;
	
	/**
	 * 下载线程构造器
	 */
	public DownloadThread(Resource resource, RandomAccessFile raf, Part part) {
		this.url = createURL(resource.getUrl());
		this.raf = raf;
		this.part = part;
		this.resource = resource;
	}
	
	private URL createURL(String urlPath) {
		try {
			return new URL(urlPath);
		} catch (Exception e) {
			throw new URLException("create url error");
		}
	}
	
	public final static int MAX_BUFFER_SIZE = 1024;

	public void run() {
		try {
			//计算开始点与结束点
			int begin = part.getBegin() + part.getCurrentLength();
			int end = part.getBegin() + part.getLength() - 1;
			//如果是开始点大于结束点, 证明该块已经下载完成
			if (begin >= end) {
				this.raf.close();
				return;
			}
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Range", "bytes=" + begin + "-" + end); 
			//如果连接不上相应的地址, 抛出java.net.UnknownHostException
			urlConnection.connect();
			//如果找不到相应的资源, 将抛出java.io.FileNotFoundException
			InputStream is = urlConnection.getInputStream();
			byte[] buffer = new byte[MAX_BUFFER_SIZE];
			int perRead = 0;
			//设置状态为下载
			this.resource.setState(DownloadContext.DOWNLOADING);
			//在.part文件中设置当前所读取的指针
			this.raf.seek(this.part.getCurrentLength());
			while ((perRead = is.read(buffer)) != -1) {
				//判断资源对象的状态是否被修改成暂停
				if (this.resource.getState() instanceof Pause) {
					closeStream(is, urlConnection, this.raf);
					return;
				}
				//判断资源对象状态
				raf.write(buffer, 0, perRead);
				this.part.setCurrentLength(this.part.getCurrentLength() + perRead);
			}
			closeStream(is, urlConnection, this.raf);
			//判断是否下载完成, 如果下载完成, 则进行合并文件
			//注意这里需要得到整个文件的大小, 而不是某个.part文件的大小
			if (isFinished(this.resource.getSize())) uniteParts();
		} catch (Exception e) {
			this.resource.setState(DownloadContext.FAILED);
			e.printStackTrace();
		}
	}
	
	private void closeStream(InputStream is, HttpURLConnection urlConnection, 
			RandomAccessFile raf) throws IOException {
		is.close();
		urlConnection.disconnect();
		raf.close();
	}

	/**
	 * 判断是否下载完成, 遍历下载文件的各个.part文件
	 * @param fileLength
	 * @return
	 */
	private boolean isFinished(int fileLength) {
		List<Part> parts = this.resource.getParts();
		//计算已下载的总数
		int downCount = 0;
		for (Part part : parts) downCount += part.getCurrentLength();
		return (downCount >= fileLength) ? true : false;
	}
	
	/**
	 * 合并part文件
	 */
	private void uniteParts() throws IOException {
		List<Part> parts = this.resource.getParts();
		//创建文件输出流, 输出到下载文件
		OutputStream bos = new FileOutputStream(this.resource.getSaveFile(), 
				false);
		for (Part part : parts) {
			//得到.part文件
			File partFile = new File(FileUtil.getPartFilePath(this.resource, 
					part));
			//获得文件输入流
			InputStream is = new FileInputStream(partFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            int temp = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
            	temp += bytesRead;
            	//写到文件中
                bos.write(buffer, 0, bytesRead);
            }
            is.close();
		}
		bos.close();
		this.resource.setState(DownloadContext.FINISHED);
	}
	
	public static void main(String[] args) throws Exception {
		int threadSize = 5;
		
		Resource f = new Resource("http://www.apache.org/dist/struts/library/struts-2.1.8.1-lib.zip", 
				"C:/test-download", "struts.zip", threadSize);
		ContextHolder.dh.doDownload(f);
	}
}
