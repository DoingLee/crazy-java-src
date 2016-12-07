package org.crazyit.flashget.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {

	//任务节点图片
	public static final ImageIcon TASK_NODE_IMAGE = new ImageIcon("images/nav/flashget.gif");
	
	//下载完成节点图片
	public static final ImageIcon FINISH_NODE_IMAGE = new ImageIcon("images/nav/finish.gif");
	
	//下载失败节点图片
	public static final ImageIcon FAIL_NODE_IMAGE = new ImageIcon("images/nav/fail.gif");
	
	//下载完成的节点
	public static final ImageIcon DOWNLOADING_NODE_IMAGE = new ImageIcon("images/nav/downloading.gif");
	
	//正在下载状态图片
	public static final ImageIcon DOWNLOADING_IMAGE = new ImageIcon("images/state/downloading.gif");
	
	//正在连接资源的图片
	public static final ImageIcon CONNECTING_IMAGE = new ImageIcon("images/state/connecting.gif");

	//下载完成的图片
	public static final ImageIcon FINISHED_IMAGE = new ImageIcon("images/state/finished.gif");
	
	//不能连接的图片
	public static final ImageIcon FAILED_IMAGE = new ImageIcon("images/state/failed.gif");
	
	//暂停下载的图片
	public static final ImageIcon PAUSE_IMAGE = new ImageIcon("images/state/pause.gif");
	
	public static final String SUSPEND_IMAGE_PATH = "images/flashget.gif";
	//悬浮打开主窗体菜单
	public static final ImageIcon SUSPEND_OPEN_IAMGE = new ImageIcon("images/suspend/open.gif");
	//悬浮新任务菜单
	public static final ImageIcon SUSPEND_NEW_IAMGE = new ImageIcon("images/suspend/add.gif");
	//悬浮开始任务菜单
	public static final ImageIcon SUSPEND_START_IAMGE = new ImageIcon("images/suspend/start.gif");
	//悬浮暂停任务菜单
	public static final ImageIcon SUSPEND_PAUSE_IAMGE = new ImageIcon("images/suspend/pause.gif");
	//删除已完成任务
	public static final ImageIcon SUSPEND_REMOVE_IAMGE = new ImageIcon("images/suspend/remove.gif");
	//退出菜单
	public static final ImageIcon SUSPEND_QUIT_IAMGE = new ImageIcon("images/suspend/quit.gif");
	//工具栏图片
	public static final String TRAY_ICON_PATH = "images/flashget-trayicon.gif";
	
	/**
	 * 返回悬浮图片
	 * @return
	 */
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
