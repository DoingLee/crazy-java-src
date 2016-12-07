package org.crazyit.ioc.xml.autowire;

/**
 * 根据名称自动装配的实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ByNameAutowire implements Autowire {
	private String value;
	public ByNameAutowire(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
