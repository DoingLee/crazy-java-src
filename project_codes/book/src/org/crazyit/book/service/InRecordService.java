package org.crazyit.book.service;

import java.util.Collection;
import java.util.Date;

import org.crazyit.book.vo.InRecord;

/**
 * 入库记录业务接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface InRecordService {

	/**
	 * 保存一条入库记录
	 * @param r
	 */
	void save(InRecord r);
	
	/**
	 * 根据日期查找对应的入库记录
	 * @param date
	 * @return
	 */
	Collection<InRecord> getAll(Date date);
	
	/**
	 * 根据id获得入库记录
	 * @param id
	 * @return
	 */
	InRecord get(String id);
}
