package org.crazyit.ball;

import java.io.IOException;

/**
 * 使挡板变短的道具
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ShortMagic extends Magic {
	/**
	 * 构造器
	 * 
	 * @param path
	 *            String 文件路径
	 * @param x
	 *            int x坐标
	 * @param y
	 *            int y坐标
	 */
	public ShortMagic(String path, int x, int y) throws IOException {
		super(path, x, y);
	}

	/**
	 * 道具的功能 : 档板变短
	 * 
	 * @param stitck
	 *            Stick
	 * @return void
	 */
	public void magicDo(Stick stick) {
		double imageWidth = stick.getImage().getWidth(null);
		// 如果档板没有变短过
		if (stick.getPreWidth() >= imageWidth) {
			// 将档板的宽度改为一半
			stick.setPreWidth((int) (stick.getPreWidth() * 0.5));
		}
	}
}