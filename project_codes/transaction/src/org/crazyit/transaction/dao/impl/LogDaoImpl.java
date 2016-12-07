package org.crazyit.transaction.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.transaction.dao.LogDao;
import org.crazyit.transaction.model.Log;

public class LogDaoImpl extends BaseDaoImpl implements LogDao {

	public void save(Log log) {
		StringBuffer sql = new StringBuffer("insert into T_LOG values(ID, '");
		sql.append(log.getLOG_DATE() + "', '")
		.append(log.getHANDLER_ID() + "', '")
		.append(log.getCOMMENT_ID() + "', '")
		.append(log.getTS_ID() + "', '")
		.append(log.getTS_DESC() + "')");
		getJDBCExecutor().executeUpdate(sql.toString());
	}

	public List<Log> find(String transactionId) {
		StringBuffer sql = new StringBuffer("select * from T_LOG lo where lo.TS_ID = '");
		sql.append(transactionId + "' order by lo.LOG_DATE asc");
		return (List<Log>)getDatas(sql.toString(), new ArrayList(), Log.class);
	}

	
}
