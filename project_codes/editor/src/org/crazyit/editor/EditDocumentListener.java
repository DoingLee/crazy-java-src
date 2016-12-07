package org.crazyit.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 文档监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class EditDocumentListener implements DocumentListener {
	//主界面对象
	private EditorFrame editorFrame;
	public EditDocumentListener(EditorFrame editorFrame) {
		this.editorFrame = editorFrame;
	}
	public void changedUpdate(DocumentEvent e) {
	}
	public void insertUpdate(DocumentEvent e) {
		//设置当前编辑的文件的saved属性为false
		this.editorFrame.getCurrentFile().setSaved(false);
	}
	public void removeUpdate(DocumentEvent e) {
	}
}
