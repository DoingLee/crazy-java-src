package org.crazyit.mysql.object;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.crazyit.mysql.database.BackupHandler;
import org.crazyit.mysql.database.impl.BackupHandlerImpl;
import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.system.PropertiesHandler;
import org.crazyit.mysql.system.impl.PropertiesHandlerImpl;
import org.crazyit.mysql.util.FileUtil;

/**
 * 全局上下文
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GlobalContext {

	//mysql安装目录绝对路径
	private String mySQLHome;
	
	//属性文件处理接口
	private PropertiesHandler propertiesHandler = new PropertiesHandlerImpl();
	//备份接口
	private BackupHandler backHandler = new BackupHandlerImpl();
	
	public GlobalContext(String mySQLHome) {
		this.mySQLHome = mySQLHome;
		//将mySQLHome加到环境变量中
		Properties props = System.getProperties();
		props.setProperty("mysql.home", mySQLHome);
		System.setProperties(props);
	}
	
	public BackupHandler getBackupHandler() {
		return this.backHandler;
	}
	
	//存放所有服务器连接的集合
	private Map<String, ServerConnection> connections = new HashMap<String, ServerConnection>();
	
	//添加一个连接到Map中
	public void addConnection(ServerConnection connection) {
		this.connections.put(connection.getConnectionName(), connection);
	}
	
	//从Map中删除一个连接
	public void removeConnection(ServerConnection connection) {
		//删除该连接的配置文件
		File configFile = new File(FileUtil.CONNECTIONS_FOLDER + 
				connection.getConnectionName() + ".properties");
		configFile.delete();
		this.connections.remove(connection.getConnectionName());
	}
	
	//从Map中获得一个DatabaseConnection对象
	public ServerConnection getConnection(String connectionName) {
		return this.connections.get(connectionName);
	}
	
	//返回mySQLHome
	public String getMySQLHome() {
		return this.mySQLHome;
	}
	
	public PropertiesHandler getPropertiesHandler() {
		return this.propertiesHandler;
	}
	
	public Map<String, ServerConnection> getConnections() {
		return this.connections;
	}
}
