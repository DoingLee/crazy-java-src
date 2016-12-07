package org.crazyit.transaction.service;

import java.util.List;

import org.crazyit.transaction.model.User;

public interface UserService {

	/**
	 * 用户登录
	 * @param userName
	 * @param passwd
	 */
	void login(String userName, String passwd);
	
	/**
	 * 返回全部的用户
	 * @return
	 */
	List<User> getUsers();
	
	/**
	 * 添加一个用户
	 * @param user
	 */
	void addUser(User user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * 查询用户
	 * @param realName
	 * @return
	 */
	List<User> query(String realName);
}
