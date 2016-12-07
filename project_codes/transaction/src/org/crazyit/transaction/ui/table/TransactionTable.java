package org.crazyit.transaction.ui.table;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * 事务列表
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TransactionTable extends JTable {

	public TransactionTable(TransactionTableModel model) {
		super(model);
		setTableFace();
	}
	
	
	public void setTableFace() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(25);
		//去掉表格的线
		this.setShowVerticalLines(false);
		//设置列宽
		this.getColumn(TransactionTableModel.TS_ID).setMinWidth(-1);
		this.getColumn(TransactionTableModel.TS_ID).setMaxWidth(-1);
		this.getColumn(TransactionTableModel.TS_STATE).setMaxWidth(30);
		this.getColumn(TransactionTableModel.TS_TITLE).setMinWidth(150);
		this.getColumn(TransactionTableModel.TS_CONTENT).setMinWidth(250);
		this.getColumn(TransactionTableModel.TS_TARGETDATE).setMinWidth(80);
		this.getColumn(TransactionTableModel.TS_CREATEDATE).setMinWidth(80);
		this.getColumn(TransactionTableModel.CURRENT_HANDLER).setMinWidth(80);
		this.getColumn(TransactionTableModel.INITIATOR).setMinWidth(80);
		this.getColumn(TransactionTableModel.TS_FACTDATE).setMinWidth(80);
		//设置单元格渲染器
		this.getColumn(TransactionTableModel.TS_STATE).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.TS_TITLE).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.TS_CONTENT).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.TS_TARGETDATE).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.TS_CREATEDATE).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.CURRENT_HANDLER).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.INITIATOR).setCellRenderer(new TransactionTableCellRenderer());
		this.getColumn(TransactionTableModel.TS_FACTDATE).setCellRenderer(new TransactionTableCellRenderer());
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
