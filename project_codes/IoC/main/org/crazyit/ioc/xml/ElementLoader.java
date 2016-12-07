package org.crazyit.ioc.xml;

import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 元素加载接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ElementLoader {
	
	/**
	 * 加入一份doc的所有Element
	 * @param doc
	 */
	void addElements(Document doc);
	
	/**
	 * 根据元素id获得Element对象
	 * @param id
	 * @return
	 */
	Element getElement(String id);
	
	/**
	 * 返回全部的Element
	 * @return
	 */
	Collection<Element> getElements();
}
