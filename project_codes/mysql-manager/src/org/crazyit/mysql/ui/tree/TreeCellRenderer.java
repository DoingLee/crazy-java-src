package org.crazyit.mysql.ui.tree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.crazyit.mysql.object.ViewObject;
import org.crazyit.mysql.util.ImageUtil;

/**
 * 树各个节点的处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class TreeCellRenderer extends DefaultTreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		//获得每个节点的ViewObject
		ViewObject obj = (ViewObject)node.getUserObject();
		if (obj == null) return this;
		//设置图片与文件
		this.setText(obj.toString());
		this.setIcon(obj.getIcon());
		//判断是否选来设置字体颜色
		if (sel) this.setForeground(Color.blue);
		else this.setForeground(getTextNonSelectionColor());
		return this;
	}
}
