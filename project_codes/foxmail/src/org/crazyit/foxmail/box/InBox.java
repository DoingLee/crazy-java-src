package org.crazyit.foxmail.box;

import javax.swing.ImageIcon;


/**
 * 收件箱
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class InBox extends AbstractBox {
	public String getText() {
		return "收件箱";
	}
	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/in-tree.gif");
	}
}
