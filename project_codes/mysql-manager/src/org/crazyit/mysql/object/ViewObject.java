package org.crazyit.mysql.object;

import javax.swing.Icon;

/**
 * 外观显示的接口, 让树节点接口和JList数据的接口继承
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ViewObject {
	
	/**
	 * 返回显示的图片
	 * @return
	 */
	Icon getIcon();
}
