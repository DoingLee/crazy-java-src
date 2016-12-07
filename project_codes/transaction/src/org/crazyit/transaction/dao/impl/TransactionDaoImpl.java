package org.crazyit.transaction.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.transaction.dao.TransactionDao;
import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.model.TransactionState;

public class TransactionDaoImpl extends BaseDaoImpl implements TransactionDao {

	public List<Transaction> findHandlerTransactions(String state, String userId) {
		StringBuffer sql = new StringBuffer("select * from T_TRANSACTION ts");
		sql.append(" where ts.HANDLER_ID = '")
		.append(userId + "'")
		.append(" and ts.TS_STATE = '")
		.append(state + "' order by ts.IS_HURRY desc");
		List<Transaction> result = (List<Transaction>)getDatas(sql.toString(), 
				new ArrayList(), Transaction.class);
		return result;
	}
	
	
	public List<Transaction> findInitiatorTransactions(String state,
			String userId) {
		StringBuffer sql = new StringBuffer("select * from T_TRANSACTION ts");
		sql.append(" where ts.INITIATOR_ID = '")
		.append(userId + "'")
		.append(" and ts.TS_STATE = '")
		.append(state + "' order by ts.IS_HURRY desc");
		List<Transaction> result = (List<Transaction>)getDatas(sql.toString(), 
				new ArrayList(), Transaction.class);
		return result;
	}

	/*
	 * 保存一个事务对象
	 * @see org.crazyit.transaction.dao.TransactionDao#save(org.crazyit.transaction.model.Transaction)
	 */
	public void save(Transaction t) {
		StringBuffer sql = new StringBuffer("insert into T_TRANSACTION values(ID, '");
		sql.append(t.getTS_TITLE() + "', '")
		.append(t.getTS_CONTENT() + "', '")
		.append(t.getTS_TARGETDATE() + "', '")
		.append(t.getTS_FACTDATE() + "', '")
		.append(t.getTS_CREATEDATE() + "', ")
		.append(t.getINITIATOR_ID() + ", ")
		.append(t.getHANDLER_ID() + ", ")
		.append(t.getPRE_HANDLER_ID() + ", '")
		.append(t.getTS_STATE() + "', '0')");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public void hurry(String id) {
		String sql = "update T_TRANSACTION ts set ts.IS_HURRY = '1' " +
				"where ts.ID = '" + id + "'";
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public void invalid(String id) {
		String sql = "update T_TRANSACTION ts set ts.TS_STATE = '" + 
		TransactionState.INVALID + "' where ts.ID = '" + id + "'";
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public Transaction find(String id) {
		String sql = "select * from T_TRANSACTION ts where ts.ID = '" + id + "'";
		List<Transaction> result = (List<Transaction>)getDatas(sql.toString(), 
				new ArrayList(), Transaction.class);
		return result.size() == 1 ? result.get(0) : null;
	}

	public void forAWhile(String id) {
		String sql = "update T_TRANSACTION ts set ts.TS_STATE = '" + 
		TransactionState.FOR_A_WHILE + "' where ts.ID = '" + id + "'";
		getJDBCExecutor().executeUpdate(sql.toString());
	}
	
	public void notToDo(String id) {
		String sql = "update T_TRANSACTION ts set ts.TS_STATE = '" + 
		TransactionState.NOT_TO_DO + "' where ts.ID = '" + id + "'";
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public void finish(String id, String date) {
		String sql = "update T_TRANSACTION ts set ts.TS_STATE = '" + 
		TransactionState.FINISHED + "', ts.TS_FACTDATE = '" + date + 
		"', ts.IS_HURRY='0' where ts.ID = '" + id + "'";
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public void changeHandler(String currentHandlerId, String preHandlerId, 
			String transactionId) {
		StringBuffer sql = new StringBuffer("update T_TRANSACTION ts set");
		sql.append(" ts.HANDLER_ID = '" + currentHandlerId + "', ")
		.append(" ts.PRE_HANDLER_ID = '" + preHandlerId + "' ")
		.append("where ts.ID = '" + transactionId + "'");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	
}
