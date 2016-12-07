package org.crazyit.foxmail.box;

import javax.swing.ImageIcon;

/**
 * 导航树的节点抽象类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class AbstractBox implements MailBox {
	//该box所对应的图片(显示在树上的图片)
	private ImageIcon icon;
	//实现接口的方法
	public ImageIcon getImageIcon(String imagePath) {
		if (this.icon == null) {
			this.icon = new ImageIcon(imagePath);
		}
		return this.icon;
	}
	//重写toString方法, 调用接口的getText方法, getText方法由子类去实现
	public String toString() {
		return getText();
	}
}
