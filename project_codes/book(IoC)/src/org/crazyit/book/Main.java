package org.crazyit.book;

import org.crazyit.book.dao.UserDao;
import org.crazyit.book.dao.impl.UserDaoImpl;
import org.crazyit.book.service.UserService;
import org.crazyit.book.service.impl.UserServiceImpl;
import org.crazyit.book.ui.LoginFrame;
import org.crazyit.ioc.context.ApplicationContext;
import org.crazyit.ioc.context.XmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new XmlApplicationContext(new String[]{"/resource/daoContext.xml", 
				"/resource/serviceContext.xml", "/resource/uiContext.xml"});
		LoginFrame loginFrame = (LoginFrame)ctx.getBean("loginFrame");
		loginFrame.setVisible(true);
//		//以下代码是旧的创建方式
//		UserDao userDao = new UserDaoImpl();
//		UserService userService = new UserServiceImpl(userDao);
//		LoginFrame loginFrame = new LoginFrame(userService);

	}

}
