package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.BookSaleRecord;

/**
 * 书本销售记录DAO接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
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
