package org.crazyit.book.service;

/**
 * 用户业务接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface UserService {

	/**
	 * 用户登录的方法, 如果登录失败，则抛出BusinessException
	 * @param name
	 * @param password
	 */
	void login(String name, String password);
}
