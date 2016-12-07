package org.crazyit.book;

import org.crazyit.book.dao.UserDao;
import org.crazyit.book.dao.impl.UserDaoImpl;
import org.crazyit.book.service.UserService;
import org.crazyit.book.service.impl.UserServiceImpl;
import org.crazyit.book.ui.LoginFrame;

/**
 * 程序入口类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Main {


	public static void main(String[] args) {
		UserDao userDao = new UserDaoImpl();
		UserService userService = new UserServiceImpl(userDao);
		LoginFrame loginFrame = new LoginFrame(userService);
	}

}
