package org.crazyit.foxmail.ui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

/**
 * 邮件列表对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailListTable extends JTable {
	public MailListTable(TableModel dm) {
		super(dm);
		//只能选择一行
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//去掉表格的线
		setShowHorizontalLines(false);
		setShowVerticalLines(false);
	}
	//让列表不可编辑
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
