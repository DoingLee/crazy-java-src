package org.crazyit.mysql.ui;

import javax.swing.JFrame;

/**
 * 有确定按钮的各个JFrame的父类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class CommonFrame extends JFrame {

	protected abstract void confirm(String name);
}
