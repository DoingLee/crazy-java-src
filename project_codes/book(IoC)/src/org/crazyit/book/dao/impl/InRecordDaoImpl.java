package org.crazyit.book.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.crazyit.book.dao.InRecordDao;
import org.crazyit.book.vo.InRecord;

public class InRecordDaoImpl extends CommonDaoImpl implements InRecordDao {

	@Override
	public Collection<InRecord> findByDate(String begin, String end) {
		String sql = "SELECT * FROM T_IN_RECORD r WHERE " +
		"r.RECORD_DATE > '" + begin + "' AND r.RECORD_DATE < '" + end + "'";
		return getDatas(sql, new Vector(), InRecord.class);
	}

	@Override
	public InRecord findById(String id) {
		String sql = "SELECT * FROM T_IN_RECORD r WHERE r.ID='" + id + "'";
		List<InRecord> datas = (List<InRecord>)getDatas(sql, new ArrayList(), InRecord.class);
		return (datas.size() == 1) ? datas.get(0) : null;
	}

	@Override
	public String save(InRecord r) {
		String sql = "INSERT INTO T_IN_RECORD VALUES(ID, '" + r.getRECORD_DATE() + "')";
		return String.valueOf(getJDBCExecutor().executeUpdate(sql));
	}

}
