package org.crazyit.book.service;

import java.util.Collection;

import org.crazyit.book.vo.Concern;

/**
 * 出版社业务接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ConcernService {

	/**
	 * 获取全部的出版社
	 * @return
	 */
	Collection<Concern> getAll();
	
	/**
	 * 根据id查找一个出版社
	 * @param id 出版社id
	 * @return
	 */
	Concern find(String id);
	
	/**
	 * 添加一个出版社
	 * @param c
	 * @return
	 */
	Concern add(Concern c);
	
	/**
	 * 修改一个出版社
	 * @param c
	 * @return
	 */
	Concern update(Concern c);
	
	/**
	 * 根据出版社名字模糊查找
	 * @param name
	 * @return
	 */
	Collection<Concern> query(String name);
}
