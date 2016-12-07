package org.crazyit.flashget.state;

import java.util.Timer;

import javax.swing.ImageIcon;

import org.crazyit.flashget.ContextHolder;
import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.util.FileUtil;
import org.crazyit.flashget.util.ImageUtil;

public class Finished extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.FINISHED_IMAGE;
	}

	public String getState() {
		return "finished";
	}

	public void init(Resource resource) {
		//删除临时文件
		FileUtil.deletePartFiles(resource);
		//资源下载完成后取消任务
		ContextHolder.dh.stopTimer(resource);
	}
	
	
}
