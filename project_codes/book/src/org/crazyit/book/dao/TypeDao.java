package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.Type;

/**
 * 书本种类DAO接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface TypeDao {

	/**
	 * 查找所有的种类
	 * @return 种类的集合
	 */
	Collection<Type> find();
	
	/**
	 * 根据名字模糊查找种类
	 * @param name 种类名字
	 * @return
	 */
	Collection<Type> findByName(String name);
	
	/**
	 * 根据id查找种类
	 * @param id 种类id
	 * @return 种类的值对象
	 */
	Type find(String id);
	
	/**
	 * 添加一个种类
	 * @param type 需要添加的种类
	 */
	String add(Type type);
	
	/**
	 * 修改一个种类
	 * @param type 修改的种类
	 */
	String update(Type type);
	
}
