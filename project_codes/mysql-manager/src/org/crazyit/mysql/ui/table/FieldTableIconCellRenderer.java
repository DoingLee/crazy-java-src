package org.crazyit.mysql.ui.table;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 字符列表的渲染对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FieldTableIconCellRenderer extends DefaultTableCellRenderer {

	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//判断单元格的值类型，分别调用setIcon与setText方法
		if (value instanceof Icon) this.setIcon((Icon)value);
		else this.setText((String)value);
		this.setHorizontalAlignment(CENTER);
		return this;
	}
}
