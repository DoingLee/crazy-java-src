package org.crazyit.transaction.dao;

import java.util.List;

import org.crazyit.transaction.model.UserTransfer;

/**
 * 事务转发记录
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface UserTransferDao {

	/**
	 * 保存一条转发记录
	 * @param ut
	 */
	void save(UserTransfer ut);
	
	/**
	 * 查找用户进行转发操作的转发记录
	 * @param userId
	 * @return
	 */
	List<UserTransfer> find(String userId);
}
