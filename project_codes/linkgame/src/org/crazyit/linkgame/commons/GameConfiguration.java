package org.crazyit.linkgame.commons;

/**
 * 保存游戏配置的对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameConfiguration {
	// 棋盘数组一维的长度
	private int xSize;

	// 棋盘数组二维的长度
	private int ySize;

	// 棋盘中第一张图片出现的x坐标
	private int beginImageX;

	// 棋盘中第一张图片出现的y坐标
	private int beginImageY;

	// 每点成功连接的分值
	private long perGrade;

	// 设置每局的时间, 单位是秒
	private long gameTime;

	// 提供一个构造器
	public GameConfiguration(int xSize, int ySize, int beginImageX,
			int beginImageY, long perGrade, long gameTime) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.beginImageX = beginImageX;
		this.beginImageY = beginImageY;
		this.perGrade = perGrade;
		this.gameTime = gameTime;
	}

	public long getGameTime() {
		return gameTime;
	}

	public long getPerGrade() {
		return perGrade;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public int getBeginImageX() {
		return beginImageX;
	}

	public int getBeginImageY() {
		return beginImageY;
	}

}
