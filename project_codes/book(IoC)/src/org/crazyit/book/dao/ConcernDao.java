package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.Concern;

public interface ConcernDao {

	/**
	 * 查找全部的出版社
	 * @return
	 */
	Collection<Concern> findAll();
	
	/**
	 * 根据ID查找出版社
	 * @param id
	 * @return
	 */
	Concern find(String id);
	
	/**
	 * 添加一个出版社
	 * @param concern 
	 * @return
	 */
	String add(Concern concern);
	
	/**
	 * 修改一个出版社
	 * @param concern
	 * @return
	 */
	String update(Concern concern);
	
	/**
	 * 根据名字模糊查找出版社
	 * @param name
	 * @return
	 */
	Collection<Concern> findByName(String name);
}
