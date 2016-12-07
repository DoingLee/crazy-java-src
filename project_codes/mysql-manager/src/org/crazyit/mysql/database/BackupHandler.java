package org.crazyit.mysql.database;

import java.io.File;
import java.util.List;

import org.crazyit.mysql.object.GlobalContext;
import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.object.tree.ServerConnection;

/**
 * 备份处理接口，用于导出脚本和执行脚本
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface BackupHandler {

	/**
	 * 导出一个数据库到文件targetFile中
	 * @param ctx
	 * @param db
	 * @param targetFile
	 */
	void dumpDatabase(GlobalContext ctx, Database db, File targetFile);
	
	/**
	 * 导出多个表
	 * @param ctx
	 * @param table
	 * @param db
	 * @param targetFile
	 */
	void dumpTable(GlobalContext ctx, List<TableData> table, Database db, 
			File targetFile);
	
	/**
	 * 为某个数据库导入一份SQL文件
	 * @param ctx
	 * @param db
	 * @param sqlFile
	 */
	void executeSQLFile(GlobalContext ctx, Database db, File sqlFile);
	
	/**
	 * 为某个服务器连接导入一份SQL文件
	 * @param ctx
	 * @param conn
	 * @param sqlFile
	 */
	void executeSQLFile(GlobalContext ctx, ServerConnection conn, File sqlFile);
}
