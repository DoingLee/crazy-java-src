package org.crazyit.flashget.navigation;

import javax.swing.ImageIcon;

public interface DownloadNode {

	/**
	 * 获得名字
	 * @return
	 */
	String getText();
	
	/**
	 * 返回对应图标
	 * @return
	 */
	ImageIcon getImageIcon();
}
