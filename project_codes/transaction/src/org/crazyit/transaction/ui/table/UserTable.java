package org.crazyit.transaction.ui.table;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * 用户列表
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserTable extends JTable {

	public UserTable(UserTableModel model) {
		super(model);
		setTableFace();
	}
	
	public void setTableFace() {
		this.getColumn(UserTableModel.USER_ID).setMinWidth(-1);
		this.getColumn(UserTableModel.USER_ID).setMaxWidth(-1);
		this.getColumn(UserTableModel.USER_NAME).setMaxWidth(200);
		this.getColumn(UserTableModel.REAL_NAME).setMaxWidth(200);
		this.getColumn(UserTableModel.ROLE).setMaxWidth(200);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(25);
		//设置渲染器
		this.getColumn(UserTableModel.USER_NAME).setCellRenderer(new UserTableCellRenderer());
		this.getColumn(UserTableModel.REAL_NAME).setCellRenderer(new UserTableCellRenderer());
		this.getColumn(UserTableModel.ROLE).setCellRenderer(new UserTableCellRenderer());
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
