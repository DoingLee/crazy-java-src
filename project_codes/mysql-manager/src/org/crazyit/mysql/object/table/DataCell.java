package org.crazyit.mysql.object.table;

/**
 * 数据表格中的单元格对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataCell {

	//该单元格所在的行
	private int row;
	
	//该单元格所在的列
	private DataColumn column;
	
	//该单元格的值
	private String value;
		
	public DataCell(int row, DataColumn column, String value) {
		this.row = row;
		this.column = column;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public DataColumn getColumn() {
		return column;
	}
	
	public void setColumn(DataColumn column) {
		this.column = column;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
	
	
}
