package org.crazyit.transaction.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.crazyit.transaction.dao.UserDao;
import org.crazyit.transaction.model.User;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	public User findUser(String userName, String passwd) {
		String sql = "select * from T_USER u where u.USER_NAME = '" + 
			userName + "' and u.PASS_WD = '" +  passwd + "' and u.IS_DELETE = '0'";
		List<User> users = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return users.size() == 1 ? users.get(0) : null;
	}

	public List<User> findUsers() {
		String sql = "select * from T_USER u where u.IS_DELETE = '0'";
		List<User> users = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return users;
	}

	public void save(User user) {
		StringBuffer sql = new StringBuffer("insert into T_USER VALUES (ID, '");
		sql.append(user.getUSER_NAME() + "', '")
		.append(user.getROLE_ID() + "', '")
		.append(user.getREAL_NAME() + "', '")
		.append("0', '")
		.append(user.getPASS_WD() + "')");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public User findUser(String userName) {
		String sql = "select * from T_USER u where u.USER_NAME = '" 
			+ userName +"' and u.IS_DELETE = '0'";
		List<User> users = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return users.size() == 1 ? users.get(0) : null;
	}

	@Override
	public void delete(String id) {
		StringBuffer sql = new StringBuffer("update T_USER u");
		sql.append(" set u.IS_DELETE = '1'")
		.append(" where u.ID = '" + id + "'");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public int getUserCount() {
		String sql = "select count(*) from T_USER u where u.IS_DELETE = '0'";
		return getJDBCExecutor().count(sql);
	}

	@Override
	public List<User> query(String realName) {
		String sql = "select * from T_USER u where u.REAL_NAME like '%" 
			+ realName + "%' and u.IS_DELETE = '0'";
		List<User> users = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return users;
	}

	public User find(String id) {
		String sql = "select * from T_USER u where u.ID = '" + id + "'";
		List<User> users = (List<User>)getDatas(sql, new ArrayList(), User.class);
		return users.size() == 1 ? users.get(0) : null;
	}
	
	
}
