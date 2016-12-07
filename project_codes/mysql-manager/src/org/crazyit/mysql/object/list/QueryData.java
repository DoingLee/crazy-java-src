package org.crazyit.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;

import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.QueryObject;
import org.crazyit.mysql.object.tree.Database;

/**
 * 执行查询时的数据对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class QueryData implements QueryObject {

	//此次查询的SQL语句
	private String sql;
	//对应的数据库对象
	private Database database;
	
	public QueryData(Database database, String sql) {
		this.sql = sql;
		this.database = database;
	}

	public int getDataCount() {
		try {
			String countSQL = getSelectCount();
			if (countSQL == null) return 0;
			Statement stmt = this.database.getStatement();
			ResultSet rs = stmt.executeQuery(countSQL);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("查询错误：" + this.sql);
		}
	}
	
	//执行SQL
	public void execute() {
		try {
			Statement stmt = this.database.getStatement();
			stmt.execute(this.sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("执行SQL错误：" + e.getMessage());
		}
	}
	
	/**
	 * 返回select count语句
	 * @return
	 */
	private String getSelectCount() {
		StringBuffer newSQL = new StringBuffer();
		int selectCount = this.sql.toLowerCase().indexOf("select count");
		if (selectCount == -1) {
			int from = this.sql.toLowerCase().indexOf("from");
			if (from == -1) return null;
			newSQL.append("select count(*) " + 
					this.sql.substring(from, this.sql.length()));
		} else {
			newSQL.append(this.sql);
		}
		return newSQL.toString();
	}

	public ResultSet getDatas(String orderString) {
		try {
			String newSQL = getQuerySQL(orderString);
			Statement stmt = this.database.getStatement();
			return stmt.executeQuery(newSQL);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("查询数据错误：" + this.sql);
		}
	}

	public String getQueryName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQuerySQL(String orderString) {
		if (this.sql.toLowerCase().indexOf("call") == 0) return this.sql;
		StringBuffer buffer = new StringBuffer(this.sql);
		if (orderString == null || orderString.trim().equals("")) {
			return buffer.toString();
		} else {
			buffer.append(" ORDER BY " + orderString);
			return buffer.toString();
		}
	}

}
