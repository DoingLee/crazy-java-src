package org.crazyit.mysql.ui.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.crazyit.mysql.ui.DataFrame;

/**
 * 数据列表对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataTable extends JTable {

	//当点击表头时, 表示当前所选择的列
	private int selectColumn = -1;
	
	public DataTable(DefaultTableModel model) {
		super(model);
		final JTableHeader header = this.getTableHeader();
		//为表头添加鼠标事件监听器
		header.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				header.getTable().clearSelection();
				int tableColumn = header.columnAtPoint(e.getPoint());
				selectColumn = tableColumn;
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectColumn = -1;
				updateUI();
			}
		});
		//设置样式
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setRowHeight(25);
	}
	
	//返回selectColumn属性值
	public int getSelectColumn() {
		return this.selectColumn;
	}

	//判断一个单元格是否被选中, 重写JTable的方法
	public boolean isCellSelected(int row, int column) {
		//如果列数与当前选择的列相同,返回true
		if (this.selectColumn == column) return true;
		return super.isCellSelected(row, column);
	}


}
