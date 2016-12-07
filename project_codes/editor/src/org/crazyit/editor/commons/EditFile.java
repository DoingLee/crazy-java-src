package org.crazyit.editor.commons;

import java.io.File;

import javax.swing.JInternalFrame;

import org.crazyit.editor.EditPane;

/**
 * 编辑的文件对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class EditFile {

	//当前编辑的文件
	private File file;
	
	//该文件是否已经被保存
	private boolean saved;
	
	//该文件对应的窗口
	private JInternalFrame iframe;
	
	//该文件所对应的编辑器
	private EditPane editPane;
	
	public EditFile(File file, boolean saved, JInternalFrame iframe, 
			EditPane editPane) {
		this.file = file;
		this.saved = saved;
		this.iframe = iframe;
		this.editPane = editPane;
	}

	public EditPane getEditPane() {
		return editPane;
	}

	public void setEditPane(EditPane editPane) {
		this.editPane = editPane;
	}

	public JInternalFrame getIframe() {
		return iframe;
	}


	public void setIframe(JInternalFrame iframe) {
		this.iframe = iframe;
	}


	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
	
}
