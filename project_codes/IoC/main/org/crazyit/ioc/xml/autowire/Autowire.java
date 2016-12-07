package org.crazyit.ioc.xml.autowire;

/**
 * 自动装配接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface Autowire {

	/**
	 * 返回自动装配的配置值
	 * @return
	 */
	String getValue();
}
