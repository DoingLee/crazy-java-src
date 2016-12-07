package org.crazyit.ball;

import java.io.IOException;

/**
 * 道具对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class Magic extends BallComponent {
	/**
	 * 提供给子类调用的构造器
	 * 
	 * @param path
	 *            String 文件路径
	 * @param x
	 *            int x坐标
	 * @param y
	 *            int y坐标
	 */
	public Magic(String path, int x, int y) throws IOException {
		super(path, x, y);
	}

	/**
	 * 道具的功能
	 * 
	 * @param stitck
	 *            Stick
	 * @return void
	 */
	public abstract void magicDo(Stick stick);
}