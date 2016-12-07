package org.crazyit.ioc.xml.construct;

/**
 * 数据节点
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface DataElement {

	/**
	 * 返回数据的类型(ref或者value)
	 * @return
	 */
	String getType();
	
	/**
	 * 返回数据的值
	 * @return
	 */
	Object getValue();
}
