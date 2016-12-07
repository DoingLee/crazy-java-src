package org.crazyit.ioc.xml.construct;

/**
 * value节点
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ValueElement implements DataElement {

	private Object value;
		
	/**
	 * 参数value为该value节点的值, clazz为value节点的属性type
	 * @param value
	 * @param clazz
	 */
	public ValueElement(Object value) {
		this.value = value;
	}
	
	public String getType() {
		return "value";
	}

	public Object getValue() {
		return this.value;
	}
	

}
