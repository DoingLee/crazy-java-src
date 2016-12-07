package org.crazyit.transaction.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.transaction.dao.UserTransferDao;
import org.crazyit.transaction.model.UserTransfer;

public class UserTransferDaoImpl extends BaseDaoImpl implements UserTransferDao {

	public void save(UserTransfer ut) {
		StringBuffer sql = new StringBuffer("insert into USER_TRANSFER values(ID, '");
		sql.append(ut.getTS_ID() + "', '")
		.append(ut.getUSER_ID() + "', '")
		.append(ut.getTARGET_USER_ID() + "', '")
		.append(ut.getOPERATE_DATE() + "')");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public List<UserTransfer> find(String userId) {
		String sql = "select * from USER_TRANSFER ut where ut.USER_ID = '" + userId + "'";
		return (List<UserTransfer>)getDatas(sql, new ArrayList(), UserTransfer.class);
	}

	
}
