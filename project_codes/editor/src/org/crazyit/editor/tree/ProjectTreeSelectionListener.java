package org.crazyit.editor.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.TreeSelectionEvent;

import org.crazyit.editor.EditorFrame;

/**
 * 项目树选择监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ProjectTreeSelectionListener extends MouseAdapter {

	//主界面的frame
	private EditorFrame editorFrame;
	
	//将主界面的frame(EditorFrame)作为构造参数传入监听器
	public ProjectTreeSelectionListener(EditorFrame editorFrame) {
		this.editorFrame = editorFrame;
	}

	public void mousePressed(MouseEvent e) {
		//得到当前所选择的节点
		ProjectTreeNode selectNode = this.editorFrame.getSelectNode();
		//如果没有选择节点，就返回
		if (selectNode == null) return;
		//如果选择的是一个目录，返回
		if (selectNode.getFile().isDirectory()) return;
		//使用EditorFrame的方法来打开文件
		this.editorFrame.openFile(selectNode.getFile());
	}

}
