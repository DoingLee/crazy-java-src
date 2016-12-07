package org.crazyit.transaction.model;

/**
 * 记录用户转发的事务记录
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserTransfer extends ValueObject {

	//事务ID
	private String TS_ID;
	
	//进行转发操作的用户
	private String USER_ID;
	
	//进行转发操作的目标用户
	private String TARGET_USER_ID;
	
	//进行转发操作用户对该事务的转发时间
	private String OPERATE_DATE;

	public String getTS_ID() {
		return TS_ID;
	}

	public void setTS_ID(String ts_id) {
		TS_ID = ts_id;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}

	public String getTARGET_USER_ID() {
		return TARGET_USER_ID;
	}

	public void setTARGET_USER_ID(String target_user_id) {
		TARGET_USER_ID = target_user_id;
	}

	public String getOPERATE_DATE() {
		return OPERATE_DATE;
	}

	public void setOPERATE_DATE(String operate_date) {
		OPERATE_DATE = operate_date;
	}
	
	
}
