package org.crazyit.book.vo;

public class BookInRecord extends ValueObject {
	//对应书的外键, 从数据库查出来时有值
	private String BOOK_ID_FK;
	//对应销售记录外键
	private String T_IN_RECORD_ID_FK;
	//入库数量
	private String IN_SUM;
	
	//该记录所对应的书, 从数据库查出来时为null
	private Book book;
	
	//该记录所对应的和库记录, 从数据库查出来时为null
	private InRecord inRecord;

	public String getBOOK_ID_FK() {
		return BOOK_ID_FK;
	}

	public void setBOOK_ID_FK(String book_id_fk) {
		BOOK_ID_FK = book_id_fk;
	}

	public String getT_IN_RECORD_ID_FK() {
		return T_IN_RECORD_ID_FK;
	}

	public void setT_IN_RECORD_ID_FK(String t_in_record_id_fk) {
		T_IN_RECORD_ID_FK = t_in_record_id_fk;
	}

	public String getIN_SUM() {
		return IN_SUM;
	}

	public void setIN_SUM(String in_sum) {
		IN_SUM = in_sum;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public InRecord getInRecord() {
		return inRecord;
	}

	public void setInRecord(InRecord inRecord) {
		this.inRecord = inRecord;
	}
	
}
