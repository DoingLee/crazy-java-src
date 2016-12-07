package org.crazyit.transaction;

import org.crazyit.transaction.dao.RoleDao;
import org.crazyit.transaction.dao.UserDao;
import org.crazyit.transaction.dao.impl.RoleDaoImpl;
import org.crazyit.transaction.dao.impl.UserDaoImpl;
import org.crazyit.transaction.service.RoleService;
import org.crazyit.transaction.service.UserService;
import org.crazyit.transaction.service.impl.RoleServiceImpl;
import org.crazyit.transaction.service.impl.UserServiceImpl;
import org.crazyit.transaction.ui.LoginFrame;

/**
 * 事务管理系统入口
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Main {

	public static void main(String[] args) {
		LoginFrame lf = new LoginFrame();
	}

}
