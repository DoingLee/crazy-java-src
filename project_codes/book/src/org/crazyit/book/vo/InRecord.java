package org.crazyit.book.vo;

import java.util.Vector;

/**
 * 入库记录
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class InRecord extends ValueObject {

	//入库日期
	private String RECORD_DATE;
	
	//入库的总数量
	private int amount;
	
	//该入库记录所对应的书的入库记录
	private Vector<BookInRecord> bookInRecords;
	
	//入库书的名称, 以逗号隔开
	private String bookNames;

	public String getRECORD_DATE() {
		return RECORD_DATE;
	}

	public void setRECORD_DATE(String record_date) {
		RECORD_DATE = record_date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Vector<BookInRecord> getBookInRecords() {
		return bookInRecords;
	}

	public void setBookInRecords(Vector<BookInRecord> bookInRecords) {
		this.bookInRecords = bookInRecords;
	}

	public String getBookNames() {
		return bookNames;
	}

	public void setBookNames(String bookNames) {
		this.bookNames = bookNames;
	}
	
	
}
