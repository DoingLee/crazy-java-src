package org.crazyit.transaction.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.transaction.dao.CommentDao;
import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.model.Transaction;

public class CommentDaoImpl extends BaseDaoImpl implements CommentDao {

	public Integer save(Comment comment) {
		StringBuffer sql = new StringBuffer("insert into T_COMMENT VALUES (ID, '");
		sql.append(comment.getCM_TITLE() + "', '")
		.append(comment.getCM_CONTENT() + "', '")
		.append(comment.getCM_DATE() + "', '")
		.append(comment.getUSER_ID() + "', '")
		.append(comment.getTRANSACTION_ID() + "')");
		return getJDBCExecutor().executeUpdate(sql.toString());
	}

	public Comment find(String id) {
		String sql = "select * from T_COMMENT tc where tc.ID = '" + id + "'";
		List<Comment> result = (List<Comment>)getDatas(sql.toString(), 
				new ArrayList(), Comment.class);
		return result.size() == 1 ? result.get(0) : null;
	}

	
}
