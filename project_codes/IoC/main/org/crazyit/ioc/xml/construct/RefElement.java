package org.crazyit.ioc.xml.construct;

/**
 * ref½Úµã
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>ÍøÕ¾: <a href="http://www.crazyit.org">·è¿ñJavaÁªÃË</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class RefElement implements DataElement {

	private Object value;
	
	public RefElement(Object value) {
		this.value = value;
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return "ref";
	}

	public Object getValue() {
		return this.value;
	}

}
