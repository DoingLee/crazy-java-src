package org.crazyit.transaction.service;

import java.util.List;

import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.model.User;

public interface TransactionService {

	/**
	 * 根据处理人获取相应状态的事务
	 * @param user
	 * @return
	 */
	List<Transaction> getHandlerTransaction(User user, String state);
	 
	/**
	 * 根据发起人获取相应状态的事务
	 * @param user
	 * @return
	 */
	List<Transaction> getInitiatorTransaction(User user, String state);
	
	/**
	 * 根据id获取事务对象
	 * @param id
	 * @return
	 */
	Transaction get(String id);
	
	/**
	 * 新增一个事务
	 * @param t
	 */
	void save(Transaction t);
	
	/**
	 * 催办事务
	 * @param t
	 */
	void hurry(String id);
	
	/**
	 * 将事务置为无效
	 * @param id
	 */
	void invalid(String id);
	
	/**
	 * 将事务置为暂时不做的状态
	 * @param id
	 */
	void forAWhile(String id, String userId, Comment comment);
	
	/**
	 * 将事务置为不做的状态
	 * @param id
	 */
	void notToDo(String id, String userId, Comment comment);
	
	/**
	 * 完成事务
	 * @param id
	 */
	void finish(String id, String userId, Comment comment);
	
	/**
	 * 转发事务
	 * @param targetId 目标用户id
	 * @param sourceId 转发的用户id
	 * @param comment 评论
	 */
	void transfer(String targetUserId, String sourceUserId, Comment comment);
	
	/**
	 * 查看事务
	 * @param id
	 * @return
	 */
	Transaction view(String id);
}
