package org.crazyit.book.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

/**
 * 列表的基类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class CommonJTable extends JTable {
	public CommonJTable(TableModel dm) {
		super(dm);
		//设置表格只能选择一行
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
	}
	//重写父类的方法, 使所有的单元格不可编辑
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
