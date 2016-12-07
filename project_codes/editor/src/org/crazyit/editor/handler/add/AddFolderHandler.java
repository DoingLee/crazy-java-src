package org.crazyit.editor.handler.add;

import java.io.File;

import javax.swing.JOptionPane;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.exception.FileException;
import org.crazyit.editor.tree.ProjectTreeNode;

/**
 * 添加目录处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddFolderHandler implements AddHandler {

	public void afterAdd(EditorFrame editorFrame, AddFrame addFrame, Object data) {
		try {
			//获得树中所选取的节点
			ProjectTreeNode selectNode = editorFrame.getSelectNode();
			//获取该节点所对应的文件对象
			File folder = selectNode.getFile();
			//如果folder不是一个目录，则用selectNode的父节点（是一个目录）作为新目录的父目录
			if (!folder.isDirectory()) {
				ProjectTreeNode parent = (ProjectTreeNode)selectNode.getParent();
				//让当前所选择的文件的父目录作为当前选择的目录
				selectNode = parent;
				folder = parent.getFile();
			}
			//创建一个文件目录对象
			File newFolder = new File(folder.getAbsoluteFile() + File.separator + data);
			//如果该目录已经存在，弹出提示并返回
			if (newFolder.exists()) {
				JOptionPane.showMessageDialog(addFrame, "目录已经存在");
				return;
			}
			//创建新的目录
			newFolder.mkdir();
			//刷新树的节点
			editorFrame.reloadNode(selectNode);
			//让EditorFrame可用
			editorFrame.setEnabled(true);
			//让添加的frame不可见
			addFrame.setVisible(false);
		} catch (Exception e) {
			throw new FileException("create folder error: " + e.getMessage());
		}
	}

}
