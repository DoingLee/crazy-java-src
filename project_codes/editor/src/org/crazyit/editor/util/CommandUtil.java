package org.crazyit.editor.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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
	
	//返回进程process的错误信息
	public static String getErrorMessage(Process process) {
		return getProcessString(process.getErrorStream());
	}
	
	private static String getProcessString(InputStream is) {
		try {
			StringBuffer buffer = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = is.read(b)) != -1;)  
				buffer.append(new String(b, 0, n));
			is.close();
			return buffer.toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	//获取一个进程的错误信息
	public static String getProcessString(Process process) {
		StringBuffer result = new StringBuffer();
		//调用CommandUtil的方法
		String errorString = getErrorMessage(process);
		if (errorString.length() != 0) result.append("错误: " + errorString);
		else result.append("执行完成");
		return result.toString();
	}
	
	//返回一个进程的信息
	public static String getRunString(Process process) {
		String error = getErrorMessage(process);
		String message = getProcessString(process.getInputStream());
		return error + message;
	}
	
	//执行一个命令并返回相应的信息
	public static Process executeCommand(String command) {
		try {
			//在windows下将命令生成一份bat文件, 再执行该bat文件
			File batFile = new File("dump.bat");
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
			return process;
		} catch (Exception e) {
			return null;
		}
	}
}
