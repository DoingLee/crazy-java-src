package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.BookInRecord;

/**
 * 书本入库记录DAO接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
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
