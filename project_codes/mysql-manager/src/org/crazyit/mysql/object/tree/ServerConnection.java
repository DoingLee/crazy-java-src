package org.crazyit.mysql.object.tree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.crazyit.mysql.exception.ConnectionException;
import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.util.ImageUtil;

/**
 * 服务器连接对象
 * @author yangenxiong
 *
 */
public class ServerConnection extends ConnectionNode {

	
	//MySQL驱动
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	//连接名称
	private String connectionName;
	//用户名
	private String username;
	//密码
	private String password;
	//连接ip
	private String host;
	//连接端口
	private String port;
	
	public ServerConnection(String connectionName, String username,
			String password, String host, String port) {
		super();
		this.connectionName = connectionName;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}
	
	/**
	 * 获得一个服务器连接下面所有的数据库
	 * @param conn
	 * @return
	 */
	public List<Database> getDatabases() {
		List<Database> result = new ArrayList<Database>();
		try {
			//获得一个连接下面所有的数据库
			ResultSet rs = query("show databases");
			while (rs.next()) {
				String databaseName = rs.getString("Database");
				Database db = new Database(databaseName, this);
				result.add(db);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			return result;
		}
	}

	//查询并返回ResultSet对象
	public ResultSet query(String sql) throws Exception {
		Statement stmt = getStatement();
		return stmt.executeQuery(sql);
	}
	
	//如果服务器已连接，则使用现成的连接创建Statement，没连接则重新创建连接
	public Statement getStatement() throws Exception {
		if (super.connection != null) {
			return super.connection.createStatement();
		}
		Connection conn = createConnection("");
		return conn.createStatement();
	}
	
	//返回连接字符串
	public String getConnectUrl() {
		return "jdbc:mysql://" + this.host + ":" + this.port + "/";
	}
	
	//获取连接
	public Connection connect() {
		//serverConnection在本类的实例中只有一个实例
		if (super.connection != null) {
			return super.connection;
		}
		try {
			Class.forName(DRIVER);
			Connection conn = createConnection("");
			super.connection = conn;
			return super.connection;
		} catch (Exception e) {
			throw new ConnectionException("服务器不能连接, 请检查配置");
		}
	}
	
	//创建连接, 参数是数据库名称
	public Connection createConnection(String database) throws Exception {
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(getConnectUrl() + database, 
				this.username, this.password);
		return conn;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getConnectionName() {
		return connectionName;
	}
	
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	
	public Icon getIcon() {
		if (super.connection == null) return ImageUtil.CONNECTION_CLOSE;
		else return ImageUtil.CONNECTION_OPEN;
	}

	
	public String toString() {
		return this.connectionName;
	}
	
	
}
