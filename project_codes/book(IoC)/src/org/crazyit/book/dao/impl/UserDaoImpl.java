package org.crazyit.book.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.book.dao.UserDao;
import org.crazyit.book.vo.User;

public class UserDaoImpl extends CommonDaoImpl implements UserDao {

	@Override
	public User getUser(String name, String password) {
		String sql = "SELECT * FROM T_USER user WHERE user.USER_NAME='" + name + "' and user.USER_PASSWORD='" + 
		password + "'";
		List<User> datas = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return (datas.size() == 1) ? datas.get(0) : null;
	}

}
