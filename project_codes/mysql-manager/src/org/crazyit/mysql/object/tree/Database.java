package org.crazyit.mysql.object.tree;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.crazyit.mysql.exception.ConnectionException;
import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.list.ProcedureData;
import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.object.list.ViewData;
import org.crazyit.mysql.util.ImageUtil;
import org.crazyit.mysql.util.MySQLUtil;


/**
 * 管理器树中的数据库对象
 * @author yangenxiong
 *
 */
public class Database extends ConnectionNode {

	//数据库名字
	private String databaseName;
	//数据库所属的服务器连接
	private ServerConnection serverConnection;
	
	//MySQL中查询表时的TABLE_NAME字段
	public final static String TABLE_NAME = "TABLE_NAME";
		
	public Database(String databaseName, ServerConnection serverConnection) {
		this.databaseName = databaseName;
		this.serverConnection = serverConnection;
	}
	
	//创建本类的连接对象
	public Connection connect() {
		//如果已经连接， 则返回
		if (super.connection != null) return super.connection;
		//创建数据库连接
		try {
			super.connection = this.serverConnection.createConnection(this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("创建数据库连接错误：" + this.databaseName);
		}
		return super.connection;
	}
	
	private Statement stmt;
	
	public Statement getStatement() throws Exception {
		if (this.stmt == null) {
			this.stmt = super.connection.createStatement();
		}
		return this.stmt;
	}
	
	/**
	 * 返回数据库的所有表的数据集
	 * @return
	 */
	public ResultSet getTablesResultSet() throws Exception {
		Statement stmt = getStatement();
		//去系统表information_schema查询
		String sql = "SELECT TABLE_NAME FROM " +
		"information_schema.TABLES sc WHERE (sc.TABLE_TYPE='" + MySQLUtil.TABLE_TYPE +
		"' OR sc.TABLE_TYPE='" + MySQLUtil.SYSTEM_VIEW_TYPE + "') AND sc.TABLE_SCHEMA='" + this.databaseName + "'";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	/**
	 * 根据名字得到表
	 * @return
	 */
	public TableData getTableByName(String tableName) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TABLE_NAME FROM information_schema.TABLES sc")
			.append(" WHERE sc.TABLE_SCHEMA='" + this.databaseName + "'")
			.append(" AND sc.TABLE_NAME='" + tableName + "'");
			Statement stmt = getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			TableData table = null;
			if (rs.next()) {
				table = new TableData(this);
				table.setName(tableName);
			}
			rs.close();
			return table;
		} catch (Exception e) {
			throw new QueryException("获取表错误：" + e.getMessage());
		}
	}
	
	/**
	 * 返回全部的表
	 * @param db
	 * @return
	 */
	public List<TableData> getTables() {
		try {
			List<TableData> result = new ArrayList<TableData>();
			ResultSet rs = getTablesResultSet();
			while (rs.next()) {
				TableData td = new TableData(this);
				td.setName(rs.getString(TABLE_NAME));
				result.add(td);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("查找表错误：" + this.getDatabaseName());
		}
	}
	
	/**
	 * 创建数据库
	 */
	public void create() {
		try {
			Statement stmt = this.serverConnection.getStatement();
			stmt.execute("create database " + this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("创建数据库错误：" + this.databaseName);
		}
	}
	
	/**
	 * 删除一个数据库
	 */
	public void remove() {
		try {
			Statement stmt = this.serverConnection.getStatement();
			stmt.execute("drop database " + this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("删除数据库错误：" + this.databaseName);
		}
	}
	
	/**
	 * 返回数据库中所有的存储过程
	 * @return
	 * @throws Exception
	 */
	private ResultSet getProceduresResultSet() throws Exception {
		Statement stmt = getStatement();
		String sql = "SELECT * FROM mysql.proc pc WHERE pc.db='" + this.databaseName + "' " +
				"AND pc.type='" + MySQLUtil.PROCEDURE_TYPE + "'";
		return stmt.executeQuery(sql);
	}
	
	/**
	 * 返回数据库中所有的函数的数据集
	 * @return
	 * @throws Exception
	 */
	private ResultSet getFunctionsResultSet() throws Exception {
		Statement stmt = getStatement();
		String sql = "SELECT * FROM mysql.proc pc WHERE pc.db='" + this.databaseName + "' " +
				"AND pc.type='" + MySQLUtil.FUNCTION_TYPE + "'";
		return stmt.executeQuery(sql);
	}
	
	/**
	 * 返回这个数据库的所有存储过程和函数
	 * @return
	 */
	public List<ProcedureData> getProcedures() {
		List<ProcedureData> result = new ArrayList<ProcedureData>();
		try {
			//得到函数的ResultSet
			ResultSet functionRs = getFunctionsResultSet();
			List<ProcedureData> functionList = getProcedureData(this, functionRs, 
					MySQLUtil.FUNCTION_TYPE);
			//得到存储过程的ResultSet
			ResultSet procedureRs = getProceduresResultSet();
			List<ProcedureData> procedureList = getProcedureData(this, procedureRs, 
					MySQLUtil.PROCEDURE_TYPE);
			result.addAll(functionList);
			result.addAll(procedureList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("查找(存储过程/函数)错误：" + getDatabaseName());
		}
	}
	
	/**
	 * 将ResultSet对象根据参数type类型封装成相应的ProcedureData
	 * @param rs
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private List<ProcedureData> getProcedureData(Database db, 
			ResultSet rs, String type) throws Exception {
		List<ProcedureData> result = new ArrayList<ProcedureData>();
		//遍历ResultSet
		while (rs.next()) {
			String content = rs.getString("body");
			String params = rs.getString("param_list");
			String returnString = rs.getString("returns");
			//根据类型封装ProcedureData
			ProcedureData data = new ProcedureData(db, type, content);
			//设置参数和返回值
			data.setArg(params);
			data.setReturnString(returnString);
			//获得（存储过程/函数）名字
			data.setName(rs.getString("name"));
			result.add(data);
		}
		rs.close();
		return result;
	}
	
	/**
	 * 返回这个数据库里的所有视图
	 * @return
	 */
	public List<ViewData> getViews() {
		try {
			List<ViewData> result = new ArrayList<ViewData>();
			ResultSet rs = getViewsResultSet();
			while (rs.next()) {
				String content = rs.getString("VIEW_DEFINITION");
				ViewData td = new ViewData(this, content);
				td.setName(rs.getString(TABLE_NAME));
				result.add(td);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("查找视图错误：" + getDatabaseName());
		}
	}
	
	/**
	 * 返回数据为中所有的视图
	 * @return
	 * @throws Exception
	 */
	private ResultSet getViewsResultSet() throws Exception {
		Statement stmt = getStatement();
		//到information_schema数据库中的VIEWS表查询
		String sql = "SELECT * FROM information_schema.VIEWS sc WHERE " +
				"sc.TABLE_SCHEMA='" + this.databaseName + "'";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public ServerConnection getServerConnection() {
		return serverConnection;
	}

	public void setServerConnection(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}

	
	public Icon getIcon() {
		if (this.connection == null) return ImageUtil.DATABASE_CLOSE;
		return ImageUtil.DATABASE_OPEN;
	}
	
	
	public String toString() {
		return this.databaseName;
	}
	
}
