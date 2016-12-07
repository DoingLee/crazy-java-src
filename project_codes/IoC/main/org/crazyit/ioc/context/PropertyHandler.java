package org.crazyit.ioc.context;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.crazyit.ioc.xml.property.PropertyElement;

/**
 * 属性处理接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface PropertyHandler {

	/**
	 * 为对象obj设置属性
	 * @param obj
	 * @param properties 属性集合
	 * @return
	 */
	Object setProperties(Object obj, Map<String, Object> properties);
	
//	/**
//	 * 以属性获得setter方法名称
//	 * @param keys
//	 * @return
//	 */
//	List<String> getSetterMethodName(Map<String, Object> properties);
	
	/**
	 * 返回一个对象里面所有的setter方法, 封装成map, key为setter方法名不要set
	 * @param obj
	 * @return
	 */
	Map<String, Method> getSetterMethodsMap(Object obj);
	
	/**
	 * 执行一个方法
	 * @param object 需要执行方法的对象
	 * @param argBean 参数的bean
	 * @param method 方法对象
	 */
	void executeMethod(Object object, Object argBean, Method method);

	
}
