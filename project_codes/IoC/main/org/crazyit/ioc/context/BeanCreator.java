package org.crazyit.ioc.context;

import java.lang.reflect.Constructor;
import java.util.List;

import org.crazyit.ioc.xml.construct.DataElement;

/**
 * Bean创建接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface BeanCreator {

	/**
	 * 使用无参的构造器创建bean实例, 不设置任何属性
	 * @param className
	 * @return
	 */
	Object createBeanUseDefaultConstruct(String className);
	
	/**
	 * 使用有参数的构造器创建bean实例, 不设置任何属性
	 * @param className 
	 * @param args 参数集合
	 * @return
	 */
	Object createBeanUseDefineConstruce(String className, List<Object> args);

}
