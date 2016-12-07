package org.crazyit.mysql.ui.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.crazyit.mysql.object.ViewObject;

/**
 * 列表渲染类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ListCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent(list, 
				value, index, isSelected, cellHasFocus);
		ViewObject vd = (ViewObject)value;
		label.setIcon(vd.getIcon());
		label.setToolTipText(vd.toString());
		if (isSelected) {
			setBackground(Color.blue);
			setForeground(Color.white);
		}
		return this;
	}
}
