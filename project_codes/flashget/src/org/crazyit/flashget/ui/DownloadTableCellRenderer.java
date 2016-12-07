package org.crazyit.flashget.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DownloadTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//判断是否需要显示图片
		if (value instanceof Icon) this.setIcon((Icon)value);
		else this.setText(value.toString());
		//判断是否选中
		if (isSelected) super.setBackground(table.getSelectionBackground());
		else setBackground(table.getBackground());
		//设置居中
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setToolTipText(value.toString());
		return this;
	}

	
}
