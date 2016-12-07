package org.crazyit.gamehall.commons;

/**
 * 游戏接口, 各个加入到框架中的游戏必须提供一个该接口的实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface Game {

	/**
	 * 开始游戏的方法
	 * @param user
	 */
	void start(User user);
}
