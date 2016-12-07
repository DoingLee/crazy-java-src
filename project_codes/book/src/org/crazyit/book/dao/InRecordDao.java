package org.crazyit.book.dao;

import java.util.Collection;

import org.crazyit.book.vo.InRecord;

/**
 * 入库记录DAO接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface InRecordDao {

	/**
	 * 根据日期区间查找入库记录
	 * @param begin 开始日期的字符串
	 * @param end 结束日期的字符
	 * @return
	 */
	Collection<InRecord> findByDate(String begin, String end);
	
	/**
	 * 根据入库记录id获得入库记录对象
	 * @param id
	 * @return
	 */
	InRecord findById(String id);
	
	/**
	 * 保存一个入库记录
	 * @param r
	 * @return
	 */
	String save(InRecord r);
}
