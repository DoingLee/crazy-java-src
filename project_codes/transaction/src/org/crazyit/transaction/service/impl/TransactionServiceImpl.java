package org.crazyit.transaction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.transaction.dao.CommentDao;
import org.crazyit.transaction.dao.LogDao;
import org.crazyit.transaction.dao.TransactionDao;
import org.crazyit.transaction.dao.UserDao;
import org.crazyit.transaction.dao.UserTransferDao;
import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.model.Log;
import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.model.TransactionState;
import org.crazyit.transaction.model.User;
import org.crazyit.transaction.model.UserTransfer;
import org.crazyit.transaction.service.BusinessException;
import org.crazyit.transaction.service.TransactionService;
import org.crazyit.transaction.ui.table.State;
import org.crazyit.transaction.util.ViewUtil;

public class TransactionServiceImpl implements TransactionService {

	private TransactionDao transactionDao;
	
	private UserDao userDao;
	
	private CommentDao commentDao;
	
	private UserTransferDao userTransferDao;
	
	private LogDao logDao;
	
	public TransactionServiceImpl(TransactionDao transactionDao, UserDao userDao, 
			CommentDao commentDao, UserTransferDao userTransferDao, LogDao logDao) {
		this.transactionDao = transactionDao;
		this.userDao = userDao;
		this.commentDao = commentDao;
		this.userTransferDao = userTransferDao;
		this.logDao = logDao;
	}
	
	public List<Transaction> getHandlerTransaction(User user, String state) {
		//如果状态参数是transfer(转发的事务), 则查找我转发的事务
		if (state.equals(State.TRANSFER)) {
			List<Transaction> datas = new ArrayList<Transaction>();
			//查找转发记录
			List<UserTransfer> transfers = this.userTransferDao.find(user.getID());
			for (UserTransfer ut : transfers) {
				Transaction t = this.transactionDao.find(ut.getTS_ID());
				datas.add(t);
			}
			datas = removeRepeat(datas);
			return setUnion(datas);
		}
		//其他状态则直接根据状态去数据库查询
		List<Transaction> datas = this.transactionDao.findHandlerTransactions(state, 
				user.getID());
		return setUnion(datas);
	}
	
	/**
	 * 去掉重复的事务（查询转发事务时使用）
	 * @param datas
	 * @return
	 */
	private List<Transaction> removeRepeat(List<Transaction> datas) {
		Map<String, Transaction> map = new HashMap<String, Transaction>();
		for (Transaction t : datas) {
			map.put(t.getID(), t);
		}
		List<Transaction> result = new ArrayList<Transaction>();
		for (String id : map.keySet()) {
			result.add(map.get(id));
		}
		return result;
	}
	
	/**
	 * 设置关联属性
	 * @param datas
	 * @return
	 */
	private List<Transaction> setUnion(List<Transaction> datas) {
		for (Transaction t : datas) {
			setUser(t);
		}
		return datas;
	}
	
	/**
	 * 设置参数事务的用户关联对象
	 * @param t
	 */
	private void setUser(Transaction t) {
		User handler = this.userDao.find(t.getHANDLER_ID());
		User initiator = this.userDao.find(t.getINITIATOR_ID());
		User preHandler = this.userDao.find(t.getPRE_HANDLER_ID());
		t.setHandler(handler);
		t.setInitiator(initiator);
		t.setPreHandler(preHandler);
	}
	
	/*
	 * 根据事务发起人和状态查找该用户所有的事务
	 * @see org.crazyit.transaction.service.TransactionService#getInitiatorProcessing(org.crazyit.transaction.model.User)
	 */
	public List<Transaction> getInitiatorTransaction(User user, String state) {
		List<Transaction> datas = this.transactionDao.findInitiatorTransactions(state, 
				user.getID());
		return setUnion(datas);
	}

	public void save(Transaction t) {
		this.transactionDao.save(t);
	}

	public void hurry(String id) {
		//如果事务的状态为进行中或者暂时不做, 则可以催办
		Transaction t = this.transactionDao.find(id);
		if (t.getTS_STATE().equals(TransactionState.PROCESSING)
				|| t.getTS_STATE().equals(TransactionState.FOR_A_WHILE)) {
			this.transactionDao.hurry(id);
		} else {
			throw new BusinessException("事务非进行中, 不可以催办");
		}
	}

	public void invalid(String id) {
		//如果事务已经完成，则不可以置为无效
		Transaction t = this.transactionDao.find(id);
		if (t.getTS_STATE().equals(TransactionState.FINISHED)) {
			throw new BusinessException("事务已经完成，不可以设置为无效");
		} else {
			this.transactionDao.invalid(id);
		}
	}

	public void forAWhile(String id, String userId, Comment comment) {
		Transaction t = this.transactionDao.find(id);
		//只有自己的事务才可以置为暂时不做状态
		if (!t.getHANDLER_ID().equals(userId)) {
			throw new BusinessException("只能处理自己的事务");
		}
		//只有在进行中的事务才可以改变此状态
		if (t.getTS_STATE().equals(TransactionState.PROCESSING)) {
			this.transactionDao.forAWhile(id);
			//保存评论
			Integer commentId = this.commentDao.save(comment);
			createLog(id, userId, String.valueOf(commentId), " 暂时不做");
		} else {
			throw new BusinessException("事务非进行中, 不可以置为暂时不做状态");
		}
	}

	public void notToDo(String id, String userId, Comment comment) {
		Transaction t = this.transactionDao.find(id);
		//只有自己的事务才可以置为不做状态
		if (!t.getHANDLER_ID().equals(userId)) {
			throw new BusinessException("只能处理自己的事务");
		}
		//只有在进行中的事务与暂时不做的事务才可以改变此状态
		if (t.getTS_STATE().equals(TransactionState.PROCESSING) 
				|| t.getTS_STATE().equals(TransactionState.FOR_A_WHILE)) {
			this.transactionDao.notToDo(id);
			//保存评论
			Integer commentId = this.commentDao.save(comment);
			createLog(id, userId, String.valueOf(commentId), " 决定不做");
		} else {
			throw new BusinessException("不可以置为暂时不做状态");
		}
	}
	
	public void finish(String id, String userId, Comment comment) {
		Transaction t = this.transactionDao.find(id);
		//只有自己的事务才可以置为完成状态
		if (!t.getHANDLER_ID().equals(userId)) {
			throw new BusinessException("只能处理自己的事务");
		}
		//只有在进行中的事务与暂时不做的事务才可以改变此状态
		if (t.getTS_STATE().equals(TransactionState.PROCESSING) 
				|| t.getTS_STATE().equals(TransactionState.FOR_A_WHILE)) {
			this.transactionDao.finish(id, ViewUtil.formatDate(new Date()));
			//保存评论
			Integer commentId = this.commentDao.save(comment);
			createLog(id, userId, String.valueOf(commentId), "做完了");
		} else {
			throw new BusinessException("只有进行中或者暂时不做的事务才可以完成");
		}
	}
	
	public void transfer(String targetUserId, String sourceUserId,
			Comment comment) {
		Transaction t = this.transactionDao.find(comment.getTRANSACTION_ID());
		//只有自己的事务才可以转发
		if (!t.getHANDLER_ID().equals(sourceUserId)) {
			throw new BusinessException("只能处理自己的事务");
		}
		//只有在进行中的事务与暂时不做的事务才可以转发
		if (t.getTS_STATE().equals(TransactionState.PROCESSING) 
				|| t.getTS_STATE().equals(TransactionState.FOR_A_WHILE)) {
			UserTransfer ut = new UserTransfer();
			ut.setTS_ID(comment.getTRANSACTION_ID());
			ut.setUSER_ID(sourceUserId);
			ut.setTARGET_USER_ID(targetUserId);
			ut.setOPERATE_DATE(ViewUtil.formatDate(new Date()));
			//新增转发记录
			this.userTransferDao.save(ut);
			//保存评论
			Integer commentId = this.commentDao.save(comment);
			//改变事务记录的当前处理人id与前一处理人id
			this.transactionDao.changeHandler(targetUserId, 
					sourceUserId, comment.getTRANSACTION_ID());
			User targetUser = this.userDao.find(targetUserId);
			createLog(t.getID(), sourceUserId, String.valueOf(commentId), "转发给 " + targetUser.getREAL_NAME() + " ");
		} else {
			throw new BusinessException("只有进行中或者暂时不做的事务才可以转发");
		}
	}
	
	@Override
	public Transaction view(String id) {
		Transaction t = this.transactionDao.find(id);
		setUser(t);
		List<Log> logs = this.logDao.find(id);
		for (Log log : logs) {
			Comment comment = this.commentDao.find(log.getCOMMENT_ID());
			User user = this.userDao.find(log.getHANDLER_ID());
			log.setComment(comment);
			log.setHandler(user);
		}
		t.setLogs(logs);
		return t;
	}

	/**
	 * 创建日志
	 * @param tsId
	 * @param handlerId
	 * @param commentId
	 * @param desc 描述
	 */
	private void createLog(String tsId, String handlerId, String commentId, String desc) {
		Log log = new Log();
		log.setCOMMENT_ID(commentId);
		log.setHANDLER_ID(handlerId);
		log.setLOG_DATE(ViewUtil.timeFormatDate(new Date()));
		log.setTS_ID(tsId);
		log.setTS_DESC(desc);
		this.logDao.save(log);
	}

	public Transaction get(String id) {
		return this.transactionDao.find(id);
	}

	
}
