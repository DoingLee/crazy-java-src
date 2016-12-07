package org.crazyit.flashget.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.crazyit.flashget.navigation.DownloadNode;

public class NavigationTreeCellRender extends DefaultTreeCellRenderer {
	
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, 
				expanded, leaf, row, hasFocus);
		//得到树节点
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		//得到节点对象
		DownloadNode obj = (DownloadNode)node.getUserObject();
		//设置文本与图片
		if (obj != null) {
			this.setIcon(obj.getImageIcon());
			this.setText(obj.getText());
		}
		return this;
	}
}
