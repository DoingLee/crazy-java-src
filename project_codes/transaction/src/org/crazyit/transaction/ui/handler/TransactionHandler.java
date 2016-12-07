package org.crazyit.transaction.ui.handler;

import org.crazyit.transaction.model.Comment;

/**
 * 事务处理接口
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface TransactionHandler {

	/**
	 * 处理某个事务
	 * @param comment
	 */
	void handler(Comment comment);
}
