package org.crazyit.book.service;

public interface UserService {

	/**
	 * 用户登录的方法, 如果登录失败，则抛出BusinessException
	 * @param name
	 * @param password
	 */
	void login(String name, String password);
}
