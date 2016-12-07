package org.crazyit.mysql.object;

import java.sql.ResultSet;

/**
 * 进行查询的接口, 在DataFrame中使用
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface QueryObject {
	
	/**
	 * 得到数据
	 * @return
	 */
	ResultSet getDatas(String orderString);
	
	/**
	 * 得到查询的名称
	 * @return
	 */
	String getQueryName();
	
	/**
	 * 返回查询的SQL
	 * @return
	 */
	String getQuerySQL(String orderString);
	
}
