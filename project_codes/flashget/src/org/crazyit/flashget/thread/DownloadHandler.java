package org.crazyit.flashget.thread;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.flashget.ContextHolder;
import org.crazyit.flashget.DownloadContext;
import org.crazyit.flashget.object.Part;
import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.state.Finished;

public class DownloadHandler {
	
	public static Map<String, Timer> timers = new HashMap<String, Timer>();
	
	public void stopTimer(Resource r) {
		Timer t = timers.get(r.getId());
		if (t != null) {
			t.cancel();
		}
	}
	
	public void doDownload(Resource r) {
		try {
			//设置下载日期
			if (r.getDownloadDate() == null) r.setDownloadDate(new Date());
			r.setState(DownloadContext.CONNECTION);
			//计算出每一块的大小
			int partLength = r.getSize() / r.getThreadSize() + 1;
			//时间计算任务
			CountTimeTask timeTask = new CountTimeTask(r);
			Timer timer = new Timer();
			timer.schedule(timeTask, 0, 1000);
			//将Timer对象放到Map中, key为该资源的id
			timers.put(r.getId(), timer);
			for (int i = 0; i < r.getThreadSize(); i++) {
				int length = partLength;
				//如果是最后一块, 则使用总数来减去前面块的总和
				if (i == (r.getThreadSize() - 1)) {
					length = r.getSize() - i * partLength;
				}
				//创建各个Part对象
				Part p = new Part((i * partLength), length, 0);
				r.getParts().add(p);
				RandomAccessFile rav = new RandomAccessFile(r.getFilePath() + 
						File.separator + p.getPartName(), "rw");
				DownloadThread t = new DownloadThread(r, rav, p);
				//设置线程优先级
				t.setPriority(6);
				t.start();
			}
		} catch (Exception e) {
			r.setState(DownloadContext.FAILED);
			e.printStackTrace();
		}
	}
	
	public void resumeDownload(Resource r) {
		if (r.getState() instanceof Finished) return;
		try {
			CountTimeTask timeTask = new CountTimeTask(r);
			Timer timer = new Timer();
			timer.schedule(timeTask, 0, 1000);
			//将Timer对象放到Map中, key为该资源的id
			timers.put(r.getId(), timer);
			for (int i = 0; i < r.getParts().size(); i++) {
				Part p = r.getParts().get(i);
				RandomAccessFile rav = new RandomAccessFile(r.getFilePath() + 
						File.separator + p.getPartName(), "rw");
				DownloadThread t = new DownloadThread(r, rav, p);
				t.start();
			}
		} catch (Exception e) {
			r.setState(DownloadContext.FAILED);
			e.printStackTrace();
		}
	}
	
	//计算时间任务
	class CountTimeTask extends TimerTask {
		private Resource r;
		public CountTimeTask(Resource r) {
			this.r = r;
		}
		public void run() {
			r.setCostTime(r.getCostTime() + 1);
		}
	}
}
