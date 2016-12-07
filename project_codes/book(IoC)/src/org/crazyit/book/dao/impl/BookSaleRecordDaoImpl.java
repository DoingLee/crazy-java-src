package org.crazyit.book.dao.impl;

import java.util.Collection;
import java.util.Vector;

import org.crazyit.book.dao.BookSaleRecordDao;
import org.crazyit.book.vo.BookSaleRecord;

public class BookSaleRecordDaoImpl extends CommonDaoImpl implements
		BookSaleRecordDao {

	@Override
	//根据销售记录id获得书的销售记录集合
	public Collection<BookSaleRecord> findBySaleRecord(String saleRecordId) {
		String sql = "SELECT * FROM T_BOOK_SALE_RECORD r WHERE r.T_SALE_RECORD_ID_FK='" + 
		saleRecordId + "'";
		return getDatas(sql, new Vector(), BookSaleRecord.class);
	}

	@Override
	public String saveBookSaleRecord(BookSaleRecord record) {
		String sql = "INSERT INTO T_BOOK_SALE_RECORD VALUES (ID, '" + record.getBook().getID() + 
		"', '" + record.getT_SALE_RECORD_ID_FK() + "', '" + record.getTRADE_SUM() + "')";
		return String.valueOf(getJDBCExecutor().executeUpdate(sql));
	}

}
