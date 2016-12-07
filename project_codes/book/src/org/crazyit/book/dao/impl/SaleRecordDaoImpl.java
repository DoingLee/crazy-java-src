package org.crazyit.book.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.crazyit.book.dao.SaleRecordDao;
import org.crazyit.book.vo.SaleRecord;

/**
 * 销售DAO实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SaleRecordDaoImpl extends CommonDaoImpl implements SaleRecordDao {

	@Override
	public Collection<SaleRecord> findByDate(String begin, String end) {
		String sql = "SELECT * FROM T_SALE_RECORD r WHERE " +
				"r.RECORD_DATE > '" + begin + "' AND r.RECORD_DATE < '" + end + "'";
		return getDatas(sql, new Vector(), SaleRecord.class);
	}

	@Override
	public SaleRecord findById(String id) {
		String sql = "SELECT * FROM T_SALE_RECORD r WHERE r.ID='" + id + "'";
		List<SaleRecord> list = (List<SaleRecord>)getDatas(sql, new ArrayList(), 
				SaleRecord.class);
		return list.size() == 0 ? null : list.get(0);
	}
	
	public String save(SaleRecord r) {
		String sql = "INSERT INTO T_SALE_RECORD VALUES(ID, '" + r.getRECORD_DATE() + "')";
		return String.valueOf(getJDBCExecutor().executeUpdate(sql));
	}

}
