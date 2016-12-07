package org.crazyit.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Icon;

import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.QueryObject;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.util.ImageUtil;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 列表中的视图显示对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ViewData extends AbstractData implements QueryObject {

	private Database database;
	
	private String content;
	
	public ViewData(Database database, String content) {
		this.database = database;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDataCount() {
		try {
			Statement stmt = this.database.getStatement();
			//得到全部记录数的SQL
			String sql = "SELECT COUNT(*) FROM " + this.name;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("查询视图错误：" + this.name);
		}
	}

	public ResultSet getDatas(String orderString) {
		try {
			String sql = getQuerySQL(orderString);
			Statement stmt = database.getStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			throw new QueryException("查询视图错误：" + this.name);
		}
	}
	
	//创建视图
	public void createView() {
		try {
			//拼装CREATE VIEW语句
			String sql = MySQLUtil.CREATE_VIEW + name + " " + 
			MySQLUtil.AS + " " + content;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("添加视图错误：" + e.getMessage());
		}
	}
	
	//删除视图
	public void dropView() {
		try {
			String sql = MySQLUtil.DROP_VIEW + this.name;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("删除视图错误：" + e.getMessage());
		}
	}
	
	//修改视图
	public void alterView() {
		try {
			String sql = MySQLUtil.ALTER_VIEW + name + " " + MySQLUtil.AS 
				+ " " + content;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("修改视图错误：" + e.getMessage());
		}
	}

	public String getQueryName() {
		return this.name;
	}

	public String getQuerySQL(String orderString) {
		StringBuffer sql = new StringBuffer("SELECT * FROM " + this.name);
		if (orderString == null || orderString.trim().equals("")) {
			return sql.toString();
		} else {
			sql.append(" ORDER BY " + orderString);
			return sql.toString();
		}
	}

	public String toString() {
		return this.name;
	}

	public Icon getIcon() {
		// TODO Auto-generated method stub
		return ImageUtil.VIEW_DATA_ICON;
	}
}
