package org.crazyit.book.service;

import java.util.Collection;
import java.util.List;

import org.crazyit.book.vo.Type;

/**
 * 书本种类业务接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface TypeService {

	/**
	 * 查找所有的种类
	 * @return 返回种类值对象集合
	 */
	Collection<Type> getAll();
	
	/**
	 * 根据种类名字模糊查找种类
	 * @param name 种类名称
	 * @return 查找的结果集
	 */
	Collection<Type> query(String name);
	
	/**
	 * 新增一个书本种类
	 * @param type 需要新增的对象
	 * @return 新增后的种类对象
	 */
	Type add(Type type);
	
	/**
	 * 修改一个书本种类
	 * @param type 需要修改的对象
	 * @return 修改后的对象
	 */
	Type update(Type type);
	
	/**
	 * 根据主键查找一个种类
	 * @param id
	 * @return
	 */
	Type get(String id);
}
