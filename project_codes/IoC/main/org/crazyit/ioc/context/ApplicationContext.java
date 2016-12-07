package org.crazyit.ioc.context;

/**
 * 容器接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ApplicationContext {
	/**
	 * 根据id找到bean
	 * @param id
	 * @return
	 */
	Object getBean(String id);
	/**
	 * IoC容器中是否包含id为参数的bean
	 * @param id
	 * @return
	 */
	boolean containsBean(String id);
	
	/**
	 * 判断一个bean是否为单态
	 * @param name
	 * @return
	 */
	boolean isSingleton(String id);
	
	/**
	 * 获得bean, 如果从容器中找不到该bean, 返回null
	 * @param id
	 * @return
	 */
	Object getBeanIgnoreCreate(String id);
}
