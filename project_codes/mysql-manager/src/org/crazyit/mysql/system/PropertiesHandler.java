package org.crazyit.mysql.system;

import java.util.List;

import org.crazyit.mysql.object.tree.ServerConnection;

/**
 * properties文件处理接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface PropertiesHandler {

	/**
	 * 将一个ServerConnection对象保存至系统的properties中, 
	 * 该properties文件的文件名是ServerConnection的connectioName
	 * @param conn
	 */
	void saveServerConnection(ServerConnection conn);
	
	/**
	 * 去本地的connections目录中读取全部的配置文件, 并封装成ServerConnection集合
	 * @return
	 */
	List<ServerConnection> getServerConnections();
}
