package org.crazyit.gamehall.fivechess.commons;

import java.awt.Rectangle;

/**
 * 桌子的座位对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Seat {
	
	//该座位的玩家
	private ChessUser user;
	
	//座位的座标范围
	private Rectangle range;
	
	//座位边
	private String side;

	//座位宽度
	public final static int SEAT_WIDTH = 30;
	
	//座位高度
	public final static int SEAT_HEIGHT = 30;

	public final static String LEFT = "left";
	
	public final static String RIGHT = "right";
	
	public Seat(ChessUser user, Rectangle range, String side) {
		this.user = user;
		this.range = range;
		this.side = side;
	}

	public ChessUser getUser() {
		return user;
	}

	public void setUser(ChessUser user) {
		this.user = user;
	}

	public Rectangle getRange() {
		return range;
	}

	public void setRange(Rectangle range) {
		this.range = range;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
