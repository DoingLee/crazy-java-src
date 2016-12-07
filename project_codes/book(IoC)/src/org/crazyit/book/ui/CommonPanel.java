package org.crazyit.book.ui;

import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class CommonPanel extends JPanel {

	//存放数据的table
	private JTable table;
	
	//列表数据
	protected Vector<Vector> datas;
	
	public void setJTable(JTable table) {
		this.table = table;
	}
	
	public JTable getJTable() {
		return this.table;
	}
	
	public Vector<Vector> getDatas() {
		return datas;
	}

	public void setDatas(Vector<Vector> datas) {
		this.datas = datas;
	}

	/*
	 * 将数据设置进JTable中
	 */
	public void initData() {
		if (this.table == null) return; 
		DefaultTableModel tableModel = (DefaultTableModel)this.table.getModel();
		//将数据设入表格Model中
		tableModel.setDataVector(getDatas(), getColumns());
		//设置表格样式
		setTableFace();
	}
	
	/*
	 * 刷新列表的方法
	 */
	public void refreshTable() {
		initData();
		getJTable().repaint();
	}
	
	/*
	 * 获取表列集合, 由子类去实现
	 */
	public abstract Vector<String> getColumns();
	
	/*
	 * 设置列表的样式, 由子类去实现
	 */
	public abstract void setTableFace();
	
	/*
	 * 设置数据列表的方法，由子类实现
	 */
	public abstract void setViewDatas();
	
	/*
	 * 清空表单
	 */
	public abstract void clear();
	
	/*
	 * 分隔用的box
	 */
	public Box getSplitBox() {
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel("             "));
		return box;
	}
	
	//给子类使用的方法, 用于获取一个列表的id列值
	public String getSelectId(JTable table) {
		int row = table.getSelectedRow();
		int column = table.getColumn("id").getModelIndex();
		String id = (String)table.getValueAt(row, column);
		return id;
	}
	
	
	//显示警告
	protected int showWarn(String message) {
		return JOptionPane.showConfirmDialog(this, message, "警告", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	
}
