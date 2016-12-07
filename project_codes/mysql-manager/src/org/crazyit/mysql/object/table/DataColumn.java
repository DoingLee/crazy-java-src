package org.crazyit.mysql.object.table;

/**
 * 数据列表中的列对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataColumn {

	//该列在JTable中的索引
	private int index;
	
	//该列的文字
	private String text;

	public DataColumn(int index, String text) {
		this.index = index;
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return this.text;
	}
	
	
}
