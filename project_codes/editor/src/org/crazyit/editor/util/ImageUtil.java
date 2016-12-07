package org.crazyit.editor.util;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageUtil {
	
	//目录关闭图片
	public static String FOLDER_CLOSE = "images/folder-close.gif";
	//目录打开图片
	public static String FOLDER_OPEN = "images/folder-open.gif";
	//无子节点的文件图片
	public static String FILE = "images/file.gif";
	//使用ImageIO流读取图片
	public static Image getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ImageIcon getImageIcon(String path) {
		return new ImageIcon(getImage(path));
	}
}
