package org.crazyit.transaction.dao;

import java.util.List;

import org.crazyit.transaction.model.User;

public interface UserDao {

	/**
	 * 根据用户名与密码查找用户
	 * @param userName
	 * @param passwd
	 * @return
	 */
	User findUser(String userName, String passwd);
	
	/**
	 * 查找全部的用户
	 * @return
	 */
	List<User> findUsers();
	
	/**
	 * 保存新用户
	 * @param user
	 */
	void save(User user);
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	User findUser(String userName);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void delete(String id);
	
	/**
	 * 返回用户数
	 * @return
	 */
	int getUserCount();
	
	/**
	 * 根据用户模糊查找用户
	 * @param userName
	 * @return
	 */
	List<User> query(String realName);
	
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	User find(String id);
}
