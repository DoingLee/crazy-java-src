package org.crazyit.flashget.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * 下载列表 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DownloadTable extends JTable {
		
	public DownloadTable() {
		super();
	}

	public void refresh() {
		DownloadTableModel tableModel = (DownloadTableModel)this.getModel();
		//通知监听器, 在范围内加入数据
		tableModel.fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
		setTableFace();
	}

	public void setTableFace() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(25);
		//设置列宽
		this.getColumn(DownloadTableModel.ID_COLUMN).setMinWidth(0);
		this.getColumn(DownloadTableModel.ID_COLUMN).setMaxWidth(0);
		this.getColumn(DownloadTableModel.STATE_COLUMN).setMaxWidth(40);
		this.getColumn(DownloadTableModel.FILE_NAME_COLUMN).setMinWidth(200);
		this.getColumn(DownloadTableModel.FILE_SIZE_COLUMN).setMinWidth(60);
		this.getColumn(DownloadTableModel.PLAN_COLUMN).setMinWidth(180);
		this.getColumn(DownloadTableModel.SPEED_COLUMN).setMinWidth(50);
		this.getColumn(DownloadTableModel.HAS_DOWN_COLUMN).setMinWidth(50);
		this.getColumn(DownloadTableModel.COST_TIME_COLUMN).setMinWidth(50);
		this.getColumn(DownloadTableModel.SPARE_TIME_COLUMN).setMinWidth(50);
		//设置单元格渲染
		this.getColumn(DownloadTableModel.STATE_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.FILE_NAME_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.FILE_SIZE_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.PLAN_COLUMN).setCellRenderer(
				new DownloadProgressBar());
		this.getColumn(DownloadTableModel.SPEED_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.HAS_DOWN_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.COST_TIME_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		this.getColumn(DownloadTableModel.SPARE_TIME_COLUMN).setCellRenderer(
				new DownloadTableCellRenderer());
		//去掉表格的线
		setShowVerticalLines(false);
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
