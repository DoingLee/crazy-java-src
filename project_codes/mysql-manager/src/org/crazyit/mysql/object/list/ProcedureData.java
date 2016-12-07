package org.crazyit.mysql.object.list;

import javax.swing.Icon;

import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.util.ImageUtil;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 列表中的存储过程显示对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ProcedureData extends AbstractData {

	//存储过程或者函数
	private String type;
	
	private Database database;
	//存储过程或者函数定义
	private String content;
	//参数
	private String arg;
	//返回值（函数才有）
	private String returnString;
	
	public ProcedureData(Database database, String type, String content) {
		this.type = type;
		this.database = database;
		this.content = content;
	}
	
	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public String getReturnString() {
		return returnString;
	}

	public void setReturnString(String returnString) {
		this.returnString = returnString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 创建存储过程
	 */
	public void createProcedure() {
		try {
			String sql = MySQLUtil.CREATE_PROCEDURE + this.name + 
			" (" + this.arg + ") " + this.content;
			this.database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("创建存储过程错误：" + e.getMessage());
		}
	}
	
	/**
	 * 修改存储过程
	 */
	public void updateProcedure() {
		try {
			//修改存储过程需要先把原来的先删除
			//删除语句
			String dropSQL = MySQLUtil.DROP_PROCEDURE + this.name;
			this.database.getStatement().execute(dropSQL);
			//创建语句
			String createSQL = MySQLUtil.CREATE_PROCEDURE + this.name + 
			" (" + this.arg + ") " + this.content;
			this.database.getStatement().execute(createSQL);
		} catch (Exception e) {
			throw new QueryException("修改存储过程错误：" + e.getMessage());
		}
	}
	
	/**
	 * 删除存储过程或者函数
	 */
	public void drop() {
		try {
			String dropSQL = "";
			if (this.type.equals(MySQLUtil.PROCEDURE_TYPE)) {
				//删除存储过程
				dropSQL = MySQLUtil.DROP_PROCEDURE + this.name;
			} else {
				//删除函数
				dropSQL = MySQLUtil.DROP_FUNCTION + this.name;
			}
			this.database.getStatement().execute(dropSQL);
		} catch (Exception e) {
			throw new QueryException("修改存储过程错误：" + this.name);
		}
	}
	
	/**
	 * 创建函数
	 */
	public void createFunction() {
		try {
			String sql = MySQLUtil.CREATE_FUNCTION + this.name + 
			" (" + this.arg + ") returns " + this.returnString + " " + this.content;
			this.database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("创建函数错误：" + e.getMessage());
		}
	}
	
	/**
	 * 修改函数
	 */
	public void updateFunction() {
		try {
			//修改存储过程需要先把原来的先删除
			String dropSQL = MySQLUtil.DROP_FUNCTION + this.name;
			this.database.getStatement().execute(dropSQL);
			String createSQL = MySQLUtil.CREATE_FUNCTION + this.name + 
			" (" + this.arg + ") returns " + this.returnString + " \n" + this.content;
			this.database.getStatement().execute(createSQL);
		} catch (Exception e) {
			throw new QueryException("修改函数错误：" + e.getMessage());
		}
	}

	public Icon getIcon() {
		if (MySQLUtil.FUNCTION_TYPE.equals(this.type)) return ImageUtil.FUNCTION_DATA_ICON;
		return ImageUtil.PROCEDURE_DATA_ICON;
	}

	public String toString() {
		return this.name;
	}

}
