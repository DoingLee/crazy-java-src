package org.crazyit.transaction.dao;

import java.util.List;

import org.crazyit.transaction.model.Transaction;

public interface TransactionDao {

	/**
	 * 根据处理人ID与事务状态查找事务
	 * @param state
	 * @param userId
	 * @return
	 */
	List<Transaction> findHandlerTransactions(String state, String userId);
	
	/**
	 * 保存事务
	 * @param t
	 */
	void save(Transaction t);
	
	/**
	 * 根据发起人ID与事务状态查找事务
	 * @param state
	 * @param userId
	 * @return
	 */
	List<Transaction> findInitiatorTransactions(String state, String userId);
	
	/**
	 * 催办事务
	 * @param id
	 */
	void hurry(String id);
	
	/**
	 * 将事务置为无效
	 * @param id
	 */
	void invalid(String id);
		
	/**
	 * 根据ID查找事务
	 * @param id
	 * @return
	 */
	Transaction find(String id);
	
	/**
	 * 将事务状态改变为暂时不做
	 * @param id
	 */
	void forAWhile(String id);
	
	/**
	 * 将事务状态改变为不做
	 * @param id
	 */
	void notToDo(String id);
	
	/**
	 * 将事务状态改变为完成
	 * @param id
	 * @param date 完成日期
	 */
	void finish(String id, String date);
	
	/**
	 *  改变事务的处理人
	 * @param currentHandlerId
	 * @param preHandlerId
	 * @param transactionId
	 */
	void changeHandler(String currentHandlerId, String preHandlerId, 
			String transactionId);
}
