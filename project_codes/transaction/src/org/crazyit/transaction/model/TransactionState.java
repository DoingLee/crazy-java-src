package org.crazyit.transaction.model;

public class TransactionState {

	//事务处理中
	public final static String PROCESSING = "processing";
	
	//事务已完成
	public final static String FINISHED = "finished";
	
	//事务已转发
//	public final static String TRANSFER = "transfer";
	
	//暂时不做
	public final static String FOR_A_WHILE = "forAWhile";
	
	//事务决定不做
	public final static String NOT_TO_DO = "notToDo";
	
	//无效的事务
	public final static String INVALID = "invalid";
}
