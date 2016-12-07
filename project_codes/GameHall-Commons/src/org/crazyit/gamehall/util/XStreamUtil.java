package org.crazyit.gamehall.util;

import org.crazyit.gamehall.commons.Request;

import com.thoughtworks.xstream.XStream;

/**
 * XStream工具类, 用于xml与对象之间的转换
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class XStreamUtil {

	private static XStream xstream = new XStream();
	
	/**
	 * 将XML转换成对象
	 * @param xml
	 * @return
	 */
	public static Object fromXML(String xml) {
		return xstream.fromXML(xml);
	}
	
	/**
	 * 将对象转换成XML字段串
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj) {
		String xml = xstream.toXML(obj);
		//去掉换行
		String a = xml.replaceAll("\n", "");
		String s = a.replaceAll("\r", "");
		return s;
	}

}
