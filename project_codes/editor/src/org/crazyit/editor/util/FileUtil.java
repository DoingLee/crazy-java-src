package org.crazyit.editor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JTextArea;

import org.crazyit.editor.exception.FileException;

/**
 * 文件处理工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FileUtil {
	
	//读取一个文件的内容
	public static String readFile(File file) {
		StringBuffer result = new StringBuffer();
		try	{
			//使用IO流读取文件
			FileInputStream fis= new FileInputStream(file);
			String content = null;
			byte[] arr = new byte[1024];
			int readLength ;
			while ((readLength = fis.read(arr)) > 0) {
				content = new String(arr, 0, readLength);
				result.append(content);
			}
			fis.close();
		}
		catch(IOException e) {
			throw new FileException("read '" + file.getAbsolutePath() + "' file error");
		}
		return result.toString();
	}
	
	//使用IO流将参数content写入到参数file中
	public static void writeFile(File file, String content) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			ps.print(content);
			ps.close();
			fos.close();
		} catch (IOException e) {
			throw new FileException("write file error");
		}
	}
}
