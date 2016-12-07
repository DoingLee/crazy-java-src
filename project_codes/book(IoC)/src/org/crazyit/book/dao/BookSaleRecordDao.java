package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.BookSaleRecord;

public interface BookSaleRecordDao {

	/**
	 * 根据销售记录id获取该销售记录下所有的书的销售记录
	 * @param saleRecordId
	 * @return
	 */
	Collection<BookSaleRecord> findBySaleRecord(String saleRecordId);

	/**
	 * 保存一条书的销售记录
	 * @param record
	 * @return
	 */
	String saveBookSaleRecord(BookSaleRecord record);
	
}
