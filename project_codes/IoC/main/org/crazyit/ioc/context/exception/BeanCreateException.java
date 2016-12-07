package org.crazyit.ioc.context.exception;

/**
 * 创建Bean异常
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BeanCreateException extends RuntimeException {

	public BeanCreateException(String e) {
		super(e);
	}
}
