package org.crazyit.book.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

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
