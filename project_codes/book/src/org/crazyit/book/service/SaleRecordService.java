package org.crazyit.book.service;

import java.util.Collection;
import java.util.Date;

import org.crazyit.book.vo.SaleRecord;

/**
 * 销售业务接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface SaleRecordService {

	/**
	 * 新增一条销售记录
	 * @param record
	 */
	void saveRecord(SaleRecord record);
	
	/**
	 * 根据日期获取该日期对应的销售记录
	 * @param date
	 * @return
	 */
	Collection<SaleRecord> getAll(Date date);
	
	/**
	 * 根据id获取销售记录
	 * @param id
	 * @return
	 */
	SaleRecord get(String id);
	
}
