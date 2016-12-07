package org.crazyit.transaction.model;

/**
 * 用户
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class User extends ValueObject {
	
	//用户名称
	private String USER_NAME;
	
	//密码
	private String PASS_WD;
	
	//用户角色id, 数据库字段
	private String ROLE_ID;
	
	//用户真实名称
	private String REAL_NAME;
	
	//是否被删除, 0没有被删除, 1为已经删除
	private String IS_DELETE;
	
	//用户角色, 不保存在数据库
	private Role role;

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String role_id) {
		ROLE_ID = role_id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getREAL_NAME() {
		return REAL_NAME;
	}

	public void setREAL_NAME(String real_name) {
		REAL_NAME = real_name;
	}

	public String getIS_DELETE() {
		return IS_DELETE;
	}

	public void setIS_DELETE(String is_delete) {
		IS_DELETE = is_delete;
	}

	public String getPASS_WD() {
		return PASS_WD;
	}

	public void setPASS_WD(String pass_wd) {
		PASS_WD = pass_wd;
	}

	
}
