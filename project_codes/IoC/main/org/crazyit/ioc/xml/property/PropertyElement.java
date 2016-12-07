package org.crazyit.ioc.xml.property;

import org.crazyit.ioc.xml.construct.DataElement;

/**
 * property节点
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class PropertyElement {

	//property元素的name属性值
	private String name;
	
	//property元素下的ref或者value属性对象
	private DataElement dataElement;

	public PropertyElement(String name, DataElement dataElement) {
		this.name = name;
		this.dataElement = dataElement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataElement getDataElement() {
		return dataElement;
	}

	public void setDataElement(DataElement dataElement) {
		this.dataElement = dataElement;
	}

}
