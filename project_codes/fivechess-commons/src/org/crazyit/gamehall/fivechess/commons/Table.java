package org.crazyit.gamehall.fivechess.commons;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 游戏中的一个桌子对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Table {

	//在界面中的开始X座标
	private int beginX;
	
	//在界面中的开始Y座标
	private int beginY;
	
	//桌子的图片
	private String tableImage;	
	
	//桌子号
	private int tableNumber;
	
	//默认的图片宽
	public final static int DEFAULT_IMAGE_WIDTH = 140;
	
	//默认的图片高
	public final static int DEFAULT_IMAGE_HEIGHT = 140;
	
	//该Table对应的范围
	private Rectangle range;
	
	//左边的座位
	private Seat leftSeat;
	
	//右边的座位
	private Seat rightSeat;
	
	public Table(int beginX, int beginY, int tableNumber) {
		this.beginX = beginX;
		this.beginY = beginY;
		this.tableNumber = tableNumber;
		this.range = new Rectangle(beginX, beginY, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
		//创建桌子对象的时候就创建左右的Seat对象
		this.leftSeat = new Seat(null, new Rectangle(getLeftSeatBeginX(), getLeftSeatBeginY(), 
				Seat.SEAT_WIDTH, Seat.SEAT_HEIGHT), Seat.LEFT);
		this.rightSeat = new Seat(null, new Rectangle(getRightSeatBeginX(), getRightSeatBeginY(), 
				Seat.SEAT_WIDTH, Seat.SEAT_HEIGHT), Seat.RIGHT);
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

	public String getTableImage() {
		return tableImage;
	}

	public void setTableImage(String tableImage) {
		this.tableImage = tableImage;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	//得到右边座标的开始X座标
	public int getRightSeatBeginX() {
		return this.beginX + 101;
	}
	
	//得到右边座位的开始Y座标
	public int getRightSeatBeginY() {
		return this.beginY + 52;
	}

	//得到左边座位的开始X座标
	public int getLeftSeatBeginX() {
		return this.beginX + 12;
	}
	
	//得到左边座位的开始Y座标
	public int getLeftSeatBeginY() {
		return this.beginY + 52;
	}
	
	public Rectangle getRange() {
		return range;
	}

	public void setRange(Rectangle range) {
		this.range = range;
	}

	public Seat getLeftSeat() {
		return leftSeat;
	}

	public Seat getRightSeat() {
		return rightSeat;
	}
	
	/**
	 * 得到桌子的左边或者右边的位置
	 * @param side
	 * @return
	 */
	public Seat getSeat(String side) {
		if (Seat.LEFT.equals(side)) return this.leftSeat;
		else return this.rightSeat;
	}
		
	//根据桌子编号取得桌子对象
	public static Table getTable(Integer tableNumber, Table[][] tables) {
		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < tables[i].length; j++) {
				Table table = tables[i][j];
				if (tableNumber == table.getTableNumber()) return table;
			}
		}
		return null;
	}
	
	/**
	 * 得到玩家在桌子的位置
	 * @param user
	 * @return
	 */
	public Seat getUserSeat(ChessUser user) {
		if (this.leftSeat.getUser() != null) {
			if (this.leftSeat.getUser() != null) {
				if (user.getId().equals(this.leftSeat.getUser().getId())) {
					return this.leftSeat;
				}
			}
		}
		if (this.rightSeat.getUser() != null) {
			if (this.rightSeat.getUser() != null) {
				if (user.getId().equals(this.rightSeat.getUser().getId())) {
					return this.rightSeat;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获得桌子另外一边的座位
	 * @param seat
	 * @return
	 */
	public Seat getAnotherSeat(Seat seat) {
		if (seat.getSide().equals(Seat.LEFT)) return this.rightSeat;
		else return this.leftSeat;
	}
	
	/**
	 * 得到对手
	 * @param user
	 * @return
	 */
	public ChessUser getAnotherUser(ChessUser user) {
		Seat seat = getUserSeat(user);
		if (seat == null) return null;
		Seat otherSeat = getAnotherSeat(seat);
		return otherSeat.getUser();
	}
}
