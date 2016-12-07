package org.crazyit.editor.tree;

import java.io.File;

import javax.swing.JTree;

import org.crazyit.editor.EditorFrame;

/**
 * 树创建接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface TreeCreator {

	/**
	 * 根据编辑的EditorFrame对象创建项目树
	 * @param editorFrame
	 * @return
	 */
	JTree createTree(EditorFrame editorFrame);
	
	/**
	 * 根据一个目录创建它的节点
	 * @param folder
	 * @return
	 */
	ProjectTreeNode createNode(File folder);
}
