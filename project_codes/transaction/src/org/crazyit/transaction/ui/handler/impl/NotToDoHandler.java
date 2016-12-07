package org.crazyit.transaction.ui.handler.impl;

import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.ui.handler.TransactionHandler;
import org.crazyit.transaction.util.ApplicationContext;

/**
 * 决定不做的处理类
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class NotToDoHandler implements TransactionHandler {

	public void handler(Comment comment) {
		//选择将事务状态改变为不做, 再添加评论
		ApplicationContext.transactionService.notToDo(comment.getTRANSACTION_ID(), 
				comment.getUSER_ID(), comment);
//		ApplicationContext.commentService.save(comment);
	}

}
