package org.crazyit.linkgame.commons;

/**
 * 游戏异常类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameException extends RuntimeException {
	// 自定义的异常类
	public GameException(String message) {
		super(message);
	}
}
