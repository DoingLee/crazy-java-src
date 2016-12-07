package org.crazyit.mysql.util;

import java.io.File;

/**
 * 该类用于存放MySQL命令
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MySQLUtil {


	//mysql安装目录下的bin目录
	public final static String MYSQL_HOME_BIN = File.separator + "bin";
	
	//mysqldump命令
	public final static String MYSQLDUMP_COMMAND = "mysqldump";
	
	//mysql命令
	public final static String MYSQL_COMMAND = "mysql";
	
	//表类型
	public final static String TABLE_TYPE = "BASE TABLE";
	
	//系统视图
	public final static String SYSTEM_VIEW_TYPE = "SYSTEM VIEW";

	//存储过程
	public final static String PROCEDURE_TYPE = "PROCEDURE";
	
	//函数
	public final static String FUNCTION_TYPE = "FUNCTION";
	
	//降序字符串
	public final static String DESC = "DESC";
	
	//升序字符串
	public final static String ASC = "ASC";
	
	//创建视图命令
	public final static String CREATE_VIEW = "create view ";
	
	//AS字符串
	public final static String AS = "AS";
	
	//修改视图命令
	public final static String ALTER_VIEW = "alter view ";
	
	//删除视图命令
	public final static String DROP_VIEW = "drop view if exists ";
	
	//创建存储过程命令
	public final static String CREATE_PROCEDURE = "create procedure ";
	
	//修改存储过程
	public final static String ALTER_PROCEDURE = "alter procedure ";
	
	//删除存储过程
	public final static String DROP_PROCEDURE = "drop procedure ";
	
	//创建函数命令
	public final static String CREATE_FUNCTION = "create function ";
	
	//修改函数
	public final static String ALTER_FUNCTION = "alter function ";
	
	//删除函数命令
	public final static String DROP_FUNCTION = "drop function ";
	
}
