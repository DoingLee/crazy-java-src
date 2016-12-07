package org.crazyit.flashget.object;

import java.io.File;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.Icon;

import org.crazyit.flashget.DownloadContext;
import org.crazyit.flashget.state.TaskState;
import org.crazyit.flashget.util.FileUtil;

public class Resource implements Serializable {

	//标识该资源的id
	private String id;
	//资源地址
	private String url;
	//资源保存路径
	private String filePath;
	//资源下载后的名称
	private String fileName;
	//资源名称
	private String sourceName;
	//资源下载后的文件对象
	private File saveFile;
	//状态
	private TaskState state;
	//文件大小
	private int size = -1;
	//下载日期
	private Date downloadDate;
	//进度
	private float progress;
	//下载速度
	private float speed;
	//使用的时间
	private int costTime;
	//剩下的时间
	private int spareTime;
	//文件所拆分的块
	private List<Part> parts;
	//下载的线程数
	private int threadSize;
	//上一次下载的大小
	private int preLength;
	
	public Resource(String url, String filePath, String fileName, int threadSize) {
		this.id = UUID.randomUUID().toString();
		this.url = url;
		this.filePath = filePath;
		this.fileName = fileName;
		this.parts = new ArrayList<Part>();
		this.saveFile = new File(filePath + File.separator + fileName);
		this.state = DownloadContext.CONNECTION;
		this.threadSize = threadSize;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSourceName() {
		this.sourceName = FileUtil.getFileName(this.url);
		return this.sourceName;
	}
	
	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public File getSaveFile() {
		return this.saveFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		if (this.state != null) {
			//判断参数的状态与本对象的状态是否一致
			if (!this.state.equals(state)) {
				//两个状态一致, 不需要进行任何动作, 不一致, 执行该状态的方法
				//执行原来状态的销毁方法
				this.state.destory(this);
				//执行新状态的init方法
				state.init(this);
			}
		}
		this.state = state;
	}

	/**
	 * 返回文件大小
	 * @return
	 */
	public int getSize() {
		try {
			//进行一次文件连接
			URL resourceURL = new URL(this.url);
			//判断之前是否已经取过文件大小
			if (this.size == -1) {
				HttpURLConnection urlConnection = (HttpURLConnection)resourceURL.openConnection();
				urlConnection.connect();
				this.size = urlConnection.getContentLength();
				//取得文件大小后返回
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.size;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public float getProgress() {
		this.progress = Math.round(100.0f * getCurrentLength() / getSize());
		return progress;
	}
	
	/**
	 * 返回下载速度, 需要得到全部已下载的长度
	 * @return
	 */
	public float getSpeed() {
		//得到当前所有块下载的大小
		int currentLength = getCurrentLength();
		//将当前下载的长度减去前一次下载的长度, 得到总下载量并计算出速度
		speed = (currentLength - preLength) / 1024.0f;
		//将当前下载的长度设置为前一次下载的长度(本次速度计算已经完成)
		preLength = currentLength;
		speed = Math.round(speed * 100) / 100.0f;
		return speed;
	}
	
	/**
	 * 得到该资源已经下载的长度(计算所有块的长度)
	 * @return
	 */
	public int getCurrentLength() {
		int result = 0;
		for (Part p : this.parts) {
			result += p.getCurrentLength();
		}
		return result;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public int getCostTime() {
		return this.costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public int getSpareTime() {
		//得到剩余长度
		int spareSize = getSize() - getCurrentLength();
		if (this.speed == 0) return this.spareTime; 
		return (spareSize / (int)this.speed) / 1000;
	}
	
	public Icon getStateIcon() {
		return state.getIcon();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}
	

}
