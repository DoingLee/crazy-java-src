package org.crazyit.transaction.util;

import org.crazyit.transaction.dao.CommentDao;
import org.crazyit.transaction.dao.LogDao;
import org.crazyit.transaction.dao.RoleDao;
import org.crazyit.transaction.dao.TransactionDao;
import org.crazyit.transaction.dao.UserDao;
import org.crazyit.transaction.dao.UserTransferDao;
import org.crazyit.transaction.dao.impl.CommentDaoImpl;
import org.crazyit.transaction.dao.impl.LogDaoImpl;
import org.crazyit.transaction.dao.impl.RoleDaoImpl;
import org.crazyit.transaction.dao.impl.TransactionDaoImpl;
import org.crazyit.transaction.dao.impl.UserDaoImpl;
import org.crazyit.transaction.dao.impl.UserTransferDaoImpl;
import org.crazyit.transaction.model.User;
import org.crazyit.transaction.service.CommentService;
import org.crazyit.transaction.service.RoleService;
import org.crazyit.transaction.service.TransactionService;
import org.crazyit.transaction.service.UserService;
import org.crazyit.transaction.service.impl.CommentServiceImpl;
import org.crazyit.transaction.service.impl.RoleServiceImpl;
import org.crazyit.transaction.service.impl.TransactionServiceImpl;
import org.crazyit.transaction.service.impl.UserServiceImpl;

/**
 * 应用上下文
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ApplicationContext {

	//系统登录用户
	public static User loginUser;

	public static UserDao userDao;
	
	public static RoleDao roleDao;
	
	public static RoleService roleService;
	
	public static UserService userService;
	
	public static TransactionDao transactionDao;
	
	public static TransactionService transactionService;
	
	public static CommentDao commentDao;
	
	public static CommentService commentService;
	
	public static UserTransferDao userTransferDao;
	
	public static LogDao logDao;
	
	static {
		logDao = new LogDaoImpl();
		roleDao = new RoleDaoImpl();
		roleService = new RoleServiceImpl(roleDao);
		userDao = new UserDaoImpl();
		userTransferDao = new UserTransferDaoImpl();
		userService = new UserServiceImpl(userDao, roleDao);
		transactionDao = new TransactionDaoImpl();
		commentDao = new CommentDaoImpl();
		transactionService = new TransactionServiceImpl(transactionDao, userDao, 
				commentDao, userTransferDao, logDao);
		
		commentService = new CommentServiceImpl(commentDao, transactionDao, userDao);
	}

}
