package org.crazyit.foxmail.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.crazyit.foxmail.box.MailBox;

/**
 * 继承DefaultTreeCellRenderer，实现每个节点都有不同的图标
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SailTreeCellRenderer extends DefaultTreeCellRenderer {

	//树节点被选中时的字体
	private Font selectFont;
	
	public SailTreeCellRenderer() {
		this.selectFont = new Font(null, Font.BOLD, 12);
	}
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		MailBox box = (MailBox)node.getUserObject();
		this.setText(box.getText());
		//判断是否选中, 再决定使用字体
		if (isSelected(node, tree)) {
			this.setFont(this.selectFont);
		} else {
			this.setFont(null);
		}
		this.setIcon(box.getImageIcon());
		return this;
	}
	
	//判断一个node是否被选中
	private boolean isSelected(DefaultMutableTreeNode node, JTree tree) {
		//得到选中的TreePath
		TreePath treePath = tree.getSelectionPath();
		if (treePath == null) return false;
		//得到被选中的节点
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		if (node.equals(selectNode)) {
			return true;
		}
		return false;
	}
	
}
