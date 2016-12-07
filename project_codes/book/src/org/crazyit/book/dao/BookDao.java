package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.Book;

/**
 * 书本DAO接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface BookDao {

	/**
	 * 查找全部的书本
	 * @return
	 */
	Collection<Book> findAll();
	
	/**
	 * 根据书本ID获取书
	 * @param id
	 * @return
	 */
	Book find(String id);
	
	/**
	 * 添加一本书, 并返回添加后书的id
	 * @param book
	 * @return
	 */
	String add(Book book);
	
	/**
	 * 修改一本书, 返回书的id
	 * @param book
	 * @return
	 */
	String update(Book book);
	
	/**
	 * 根据书名称模糊查找书
	 * @param name
	 * @return
	 */
	Collection<Book> findByName(String name);
	
	/**
	 * 修改书的库存
	 * @param b
	 */
	void updateRepertory(Book b);
	
}
