package org.crazyit.foxmail.box;

import javax.swing.ImageIcon;

/**
 * 已经成功发送的邮件
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SentBox extends AbstractBox {

	@Override
	public String getText() {
		return "已发送";
	}
	
	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/sent-tree.gif");
	}
}
