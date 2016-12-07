package org.crazyit.book.service.impl;

import org.crazyit.book.commons.BusinessException;
import org.crazyit.book.dao.UserDao;
import org.crazyit.book.service.UserService;
import org.crazyit.book.vo.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void login(String name, String password) {
		User user = userDao.getUser(name, password);
		if (user == null) {
			throw new BusinessException("”√ªß√˚√‹¬Î¥ÌŒÛ");
		}
	}

}
