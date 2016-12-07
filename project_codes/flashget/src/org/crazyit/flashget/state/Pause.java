package org.crazyit.flashget.state;

import javax.swing.ImageIcon;

import org.crazyit.flashget.ContextHolder;
import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.util.ImageUtil;

public class Pause extends AbstractState {
	
	@Override
	public ImageIcon getIcon() {
		return ImageUtil.PAUSE_IMAGE;
	}

	@Override
	public String getState() {
		return "pause";
	}

	@Override
	public void init(Resource resource) {
		//资源暂停后取消任务
		ContextHolder.dh.stopTimer(resource);
	}

}
