package org.crazyit.mysql.util;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import org.crazyit.mysql.exception.CommandException;

/**
 * 命令工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class CommandUtil {

	public final static String WINDOWS_COMMAND = "cmd /c ";
	
	/**
	 * 执行命令并返回进程输入流
	 * @param command
	 * @return
	 */
	public static InputStream executeCommand(String command) {
		try {
			//在windows下将命令生成一份bat文件, 再执行该bat文件
			File batFile = new File("temp/dump.bat");
			if (!batFile.exists()) batFile.createNewFile();
			//将命令写入文件中
			FileWriter writer = new FileWriter(batFile);
			writer.write(command);
			writer.close();
			//执行该bat文件
			Process process =Runtime.getRuntime().exec(WINDOWS_COMMAND + batFile.getAbsolutePath());
			process.waitFor();
			//将bat文件删除
			batFile.delete();
			return process.getInputStream();
		} catch (Exception e) {
			throw new CommandException("执行命令错误：" + command);
		}
	}
}
