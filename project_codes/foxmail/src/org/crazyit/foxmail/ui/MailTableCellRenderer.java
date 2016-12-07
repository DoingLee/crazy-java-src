package org.crazyit.foxmail.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 邮件列表的单元格渲染对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailTableCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable arg0, Object value,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		//判断该单元格的值是否图片
		if (value instanceof Icon) {
			this.setIcon((Icon)value);
		} else {
			this.setText(value.toString());
		}
		return this;
	}
}
