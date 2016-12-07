package org.crazyit.mysql.database.impl;

import java.io.File;
import java.util.List;

import org.crazyit.mysql.database.BackupHandler;
import org.crazyit.mysql.object.GlobalContext;
import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.object.tree.TableNode;
import org.crazyit.mysql.util.CommandUtil;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 备份处理实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BackupHandlerImpl implements BackupHandler {

	//获得mysql bin的目录
	private String getMySQLBin(GlobalContext ctx) {
		return ctx.getMySQLHome() + MySQLUtil.MYSQL_HOME_BIN + File.separator;
	}
	
	public void dumpDatabase(GlobalContext ctx, Database db, File targetFile) {
		StringBuffer dumpCommand = new StringBuffer();
		//注意, 需要为命令加入双引号, 如果没有双引号, mysql安装目录有空格将不能正常导出
		dumpCommand.append('"' + getMySQLBin(ctx));
		//加入mysqldump命令
		dumpCommand.append(MySQLUtil.MYSQLDUMP_COMMAND + '"');
		//加入各个信息
		//为命令加入-u -p -h参数
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" --force --databases " + db.getDatabaseName() + " > ");
		//如果导出的路径有空格将不能正常导出, 因此注意需要为路径加入引号
		dumpCommand.append('"' + targetFile.getAbsolutePath() + '"');
		System.out.println(dumpCommand.toString());
		//启动执行导出的线程
		CommandThread thread = new CommandThread(dumpCommand.toString());
		thread.start();
	}
	

	public void dumpTable(GlobalContext ctx, List<TableData> tables, Database db, 
			File targetFile) {
		StringBuffer dumpCommand = new StringBuffer();
		StringBuffer tableNames = new StringBuffer();
		//得到所有表名字符串
		for (TableData table : tables) tableNames.append(table.getName() + " ");
		dumpCommand.append('"' + getMySQLBin(ctx));
		//加入mysqldump命令
		dumpCommand.append(MySQLUtil.MYSQLDUMP_COMMAND + '"');
		//为命令加入-u -p -h参数
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" --databases " + db.getDatabaseName());
		dumpCommand.append(" --tables " + tableNames.toString() + " > ");
		dumpCommand.append('"' + targetFile.getAbsolutePath() + '"');
		System.out.println(dumpCommand.toString());
		new CommandThread(dumpCommand.toString()).start();
	}

	public void executeSQLFile(GlobalContext ctx, Database db, File sqlFile) {
		StringBuffer dumpCommand = new StringBuffer();
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQL_COMMAND + '"');
		//为命令加入-u -p -h参数
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" -D" + db.getDatabaseName());
		dumpCommand.append(" < \"" + sqlFile.getAbsolutePath() + "\"");
		System.out.println(dumpCommand.toString());
		new CommandThread(dumpCommand.toString()).start();
	}

	public void executeSQLFile(GlobalContext ctx, ServerConnection conn,
			File sqlFile) {
		StringBuffer dumpCommand = new StringBuffer();
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQL_COMMAND + '"');
		//为命令加入-u -p -h参数
		getExecuteCommand(dumpCommand, conn);
		dumpCommand.append(" < \"" + sqlFile.getAbsolutePath() + "\"");
		new CommandThread(dumpCommand.toString()).start();
	}
	
	//为命令加入-u -p -h参数
	private StringBuffer getExecuteCommand(StringBuffer command, ServerConnection conn) {
		command.append(" -u" + conn.getUsername());
		command.append(" -p" + conn.getPassword());
		command.append(" -h" + conn.getHost());
		return command;
	}

	/**
	 * 读取输入流
	 * @param is
	 * @return
	 */
//	private String readInputStream(InputStream is) {
//		try {
//			StringBuffer result = new StringBuffer();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			while(br.read() != -1) {
//				result.append(br.readLine());
//			}
//			return result.toString();
//		} catch (Exception e) {
//			throw new CommandException("读取命令输入错误：" + e.getMessage());
//		}
//
//	}
}

/**
 * 创建命令执行线程
 * @author yangenxiong
 *
 */
class CommandThread extends Thread {

	private String command;
	
	public CommandThread(String command) {
		this.command = command;
	}
	
	public void run() {
		CommandUtil.executeCommand(this.command);
	}
	
}
