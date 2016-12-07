package org.crazyit.foxmail.box;

import javax.swing.ImageIcon;

/**
 * 导航树的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface MailBox {

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
