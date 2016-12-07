package org.crazyit.gamehall.chatroom.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.crazyit.gamehall.commons.User;

public class UserListCellRenderer extends DefaultListCellRenderer {

	private Map<String, Icon> heads = new HashMap<String, Icon>();
	
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent(list, 
				value, index, isSelected, cellHasFocus);
		User user = (User)value;
		label.setIcon(getHead(user));
		label.setText(user.getName());
		if (isSelected) {
			this.setBackground(Color.YELLOW);
			list.setSelectedIndex(index);
		} else {
			this.setBackground(Color.WHITE);
			list.setSelectedIndex(-1);
		}
		return this;
	}
	
	private Icon getHead(User user) {
		if (user.getHeadImage() == null) return null;
		Icon head = this.heads.get(user.getHeadImage());
		if (head == null) {
			head = new ImageIcon(user.getHeadImage());
			this.heads.put(user.getHeadImage(), head);
		}
		return head;
	}
}
