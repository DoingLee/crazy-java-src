package org.crazyit.editor.handler.add;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.commons.WorkSpace;
import org.crazyit.editor.config.CompileConfig;
import org.crazyit.editor.exception.FileException;

/**
 * 添加项目处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddProjectHandler implements AddHandler {

	public void afterAdd(EditorFrame editorFrame, AddFrame addFrame, Object data) {
		try {
			//获取工作空间所在的目录
			File spaceFolder = editorFrame.getWorkSpace().getFolder();
			//创建.project文件
			File projectFile = new File(spaceFolder.getAbsoluteFile() + 
					File.separator + data + ".project");
			//创建项目目录
			File projectFolder = new File(spaceFolder.getAbsoluteFile() + File.separator + data);
			//项目已经存在，弹出警告并返回
			if (projectFile.exists() && projectFolder.exists()) {
				JOptionPane.showMessageDialog(addFrame, "项目已经存在");
				return;
			}
			//项目文件不存在， 创建项目文件
			if (!projectFile.exists()) projectFile.createNewFile();
			//项目目录不存在， 创建项目文件目录
			if (!projectFolder.exists()) projectFolder.mkdir();
			//创建项目的src目录和编译目录
			File src = new File(projectFolder.getAbsoluteFile() + 
					File.separator + CompileConfig.SRC_DIR);
			//Java文件编译的输入目录
			File output = new File(projectFolder.getAbsoluteFile() + 
					File.separator + CompileConfig.OUTPUT_DIR);
			//创建src和output两个目录
			src.mkdir();
			output.mkdir();
			//刷新整棵树
			JTree newTree = editorFrame.getTreeCreator().createTree(editorFrame);
			editorFrame.refreshTree(newTree); 
			//让EditorFrame变得可用
			editorFrame.setEnabled(true);
			//让添加的frame不可见
			addFrame.setVisible(false);
		} catch (Exception e) {
			throw new FileException("create project error: " + e.getMessage());
		}
	}

}
