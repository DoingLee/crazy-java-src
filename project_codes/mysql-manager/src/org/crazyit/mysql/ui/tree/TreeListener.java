package org.crazyit.mysql.ui.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.ui.MainFrame;

/**
 * 树的鼠标事件监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TreeListener extends MouseAdapter {

	private MainFrame mainFrame;
	
	public TreeListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	
	public void mousePressed(MouseEvent e) {
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
			//左键点击，查看树的节点
			this.mainFrame.viewTreeDatas();
		}
		if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
			//右键菜单
			this.mainFrame.showTreeMenu(e);
		}
		
	}

}
