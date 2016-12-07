package org.crazyit.transaction.ui.table;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class UserTableCellRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel c = (JLabel)super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, column);
		c.setToolTipText(value.toString());
		//…Ë÷√æ”÷–
		c.setHorizontalAlignment(JLabel.CENTER);
		return c;
	}
}
