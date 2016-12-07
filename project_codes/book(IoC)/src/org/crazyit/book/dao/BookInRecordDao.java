package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.BookInRecord;

public interface BookInRecordDao {

	/**
	 * 根据入库记录查找全部的书的入库记录
	 * @param inRecordId
	 * @return
	 */
	Collection<BookInRecord> findByInRecord(String inRecordId);
	
	/**
	 * 保存一条书的入库记录, 并返回该记录的id
	 * @param r
	 * @return
	 */
	String save(BookInRecord r);
}
