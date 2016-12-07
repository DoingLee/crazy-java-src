package org.crazyit.book.vo;

import java.util.List;
import java.util.Vector;

/**
 * 销售记录
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SaleRecord extends ValueObject {
	//交易日期
	private String RECORD_DATE;
	//销售的总数量
	private int amount;
	//总价钱
	private double totalPrice;
	//书的销售记录
	private Vector<BookSaleRecord> bookSaleRecords;
	
	//该记录中对应所有书的名称, 显示用
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Vector<BookSaleRecord> getBookSaleRecords() {
		return bookSaleRecords;
	}

	public void setBookSaleRecords(Vector<BookSaleRecord> bookSaleRecords) {
		this.bookSaleRecords = bookSaleRecords;
	}

	public String getBookNames() {
		return bookNames;
	}

	public void setBookNames(String bookNames) {
		this.bookNames = bookNames;
	}
	
	
}
