package org.crazyit.ioc.context;


/**
 * 使用该类创建IoC容器, 将会实例化所有bean
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class XmlApplicationContext extends AbstractApplicationContext {
		
	
	public XmlApplicationContext(String[] xmlPaths) {
		//初始化文档和元素
		setUpElements(xmlPaths);
		//创建bean实例
		createBeans();
	}
}
