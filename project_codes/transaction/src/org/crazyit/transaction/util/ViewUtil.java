package org.crazyit.transaction.util;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ViewUtil {

	//显示警告
	public static int showWarn(String message, Component comp) {
		return JOptionPane.showConfirmDialog(comp, message, "警告", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//显示询问
	public static int showConfirm(String message, Component comp) {
		return JOptionPane.showConfirmDialog(comp, message, "询问", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	
	/**
	 * 获取一个列表的id列的值
	 * @param table
	 * @return
	 */
	public static String getSelectValue(JTable table, String columnName) {
		int row = table.getSelectedRow();
		if (row >= table.getModel().getRowCount()) return null;
		if (row == -1) return null;
		int column = table.getColumn(columnName).getModelIndex();
		String id = (String)table.getValueAt(row, column);
		return id;
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String formatDate(Date d) {
		return dateFormat.format(d);
	}
	
	public static String timeFormatDate(Date d) {
		return timeFormat.format(d);
	}
	
}
