package org.crazyit.flashget.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DownloadProgressBar extends JProgressBar implements
		TableCellRenderer {
	public DownloadProgressBar() {
		super(0, 100);
		this.setStringPainted(true);
		this.setForeground(Color.green);
	}
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Float floatValue = Float.parseFloat(value.toString());
		int intValue = (int)floatValue.floatValue();
		this.setValue(intValue);
		this.setString(value.toString() + " %");
		return this;
	}
}
