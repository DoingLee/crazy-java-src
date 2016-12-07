package org.crazyit.linkgame.commons;

import java.awt.image.BufferedImage;

/**
 * 连连看游戏中的方块对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Piece {
	// 保存方块对象的所对应的图片
	private BufferedImage image;

	// 开始点的x坐标
	private int beginX;

	// 开始点的y坐标
	private int beginY;

	// 结束点的x坐标
	private int endX;

	// 结束点的y坐标
	private int endY;

	// 该对象在数组中一维的位置
	private int indexX;

	// 该对象在数组中二维的位置
	private int indexY;

	// 只设置该Piece对象在棋盘数组中的位置
	public Piece(int indexX, int indexY) {
		this.indexX = indexX;
		this.indexY = indexY;
	}

	// 通过构造器创建这个对象
	public Piece(int beginX, int beginY, int indexX, int indexY,
			BufferedImage image) {
		this.beginX = beginX;
		this.beginY = beginY;
		this.indexX = indexX;
		this.indexY = indexY;
		this.image = image;
		this.endX = beginX + image.getWidth();
		this.endY = beginY + image.getHeight();
	}

	public int getIndexX() {
		return indexX;
	}

	public int getIndexY() {
		return indexY;
	}

	public int getBeginX() {
		return beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public BufferedImage getImage() {
		return image;
	}

	/**
	 * 是否为图片相同的两个Piece, 判断标准为两个Piece的图片是否相同
	 * 
	 * @param piece
	 * @return true 两个Piece图片相同 false 两个Piece图片不相同
	 */
	public boolean isSameImage(Piece piece) {
		// 如果图片相同, 返回true
		if (this.image.equals(piece.getImage())) {
			return true;
		}
		return false;
	}
}
