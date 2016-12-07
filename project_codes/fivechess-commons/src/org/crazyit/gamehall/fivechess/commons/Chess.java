package org.crazyit.gamehall.fivechess.commons;

import java.awt.Rectangle;

/**
 * 五子棋棋盘中的一个棋子对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Chess {

	//棋子的开始X坐标
	private int beginX;

	//棋子的开始Y坐标
	private int beginY;
	
	//在二维数组中的一维值
	private int i;
	
	//在二维数组中的二维值
	private int j;
	
	//棋子颜色
	private String color;
	
	//该棋子的区域
	private Rectangle range;
	
	public final static String BLACK = "black";
	
	public final static String WHITE = "white";
	
	//棋子图片的宽和高
	public final static int IMAGE_WIDTH = 33;
	
	public final static int IMAGE_HEIGHT = 33;
	
	//棋盘的横向大小
	public final static int CHESS_BOARD_X_SIZE = 15;
	
	//棋盘的纵向大小
	public final static int CHESS_BOARD_Y_SYZE = 15;

	//客户端使用的构造器, 需要一个棋子的开始坐标
	public Chess(int beginX, int beginY, int i, int j, String color) {
		this.beginX = beginX;
		this.beginY = beginY;
		this.i = i;
		this.j = j;
		this.color = color;
		this.range = new Rectangle(beginX, beginY, IMAGE_WIDTH, IMAGE_HEIGHT);
	}
	
	//服务器端使用的构造器, 不必需要棋子的坐标
	public Chess(int i, int j, String color) {
		this.i = i;
		this.j = j;
		this.color = color;
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public void setI(int i) {
		this.i = i;
	}
	
	public void setJ(int j) {
		this.j = j;
	}

	public int getBeginX() {
		return beginX;
	}

	public void setBeginX(int beginX) {
		this.beginX = beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public void setBeginY(int beginY) {
		this.beginY = beginY;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Rectangle getRange() {
		return this.range;
	}
}
