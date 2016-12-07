package org.crazyit.flashget.state;

import javax.swing.ImageIcon;

import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.util.ImageUtil;

public class Downloading extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.DOWNLOADING_IMAGE;
	}
	public String getState() {
		return "downloading";
	}
}
