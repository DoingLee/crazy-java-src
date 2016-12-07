package org.crazyit.transaction.service.impl;

import java.util.Date;

import org.crazyit.transaction.dao.CommentDao;
import org.crazyit.transaction.dao.TransactionDao;
import org.crazyit.transaction.dao.UserDao;
import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.service.CommentService;
import org.crazyit.transaction.util.ViewUtil;

public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;
	
	private TransactionDao transactionDao;
	
	private UserDao userDao;
	
	public CommentServiceImpl(CommentDao commentDao, TransactionDao transactionDao, 
			UserDao userDao) {
		this.commentDao = commentDao;
		this.transactionDao = transactionDao;
		this.userDao = userDao;
	}
	
	public void save(Comment comment) {
		this.commentDao.save(comment);
	}

}
