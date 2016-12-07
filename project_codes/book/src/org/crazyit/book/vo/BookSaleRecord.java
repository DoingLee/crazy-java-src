package org.crazyit.book.vo;


/**
 * 书的销售记录
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BookSaleRecord extends ValueObject {

	//该记录对应的书的外键
	private String BOOK_ID_FK;
	
	//该记录对应的销售记录的外键
	private String T_SALE_RECORD_ID_FK;
	
	//该记录所对应的书的销售数量
	private String TRADE_SUM;
	
	//该记录对应的书对象, 当从数据库查找到BookSaleRecord时, 该属性为null
	private Book book;
	
	//该记录对应的销售记录对象, 当从数据库查找到BookSaleRecord时, 该属性为null
	private SaleRecord saleRecord;

	public String getBOOK_ID_FK() {
		return BOOK_ID_FK;
	}

	public void setBOOK_ID_FK(String book_id_fk) {
		BOOK_ID_FK = book_id_fk;
	}

	public String getT_SALE_RECORD_ID_FK() {
		return T_SALE_RECORD_ID_FK;
	}

	public void setT_SALE_RECORD_ID_FK(String t_sale_record_id_fk) {
		T_SALE_RECORD_ID_FK = t_sale_record_id_fk;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public SaleRecord getSaleRecord() {
		return saleRecord;
	}

	public void setSaleRecord(SaleRecord saleRecord) {
		this.saleRecord = saleRecord;
	}

	public String getTRADE_SUM() {
		return TRADE_SUM;
	}

	public void setTRADE_SUM(String trade_sum) {
		TRADE_SUM = trade_sum;
	}
	
	
}
