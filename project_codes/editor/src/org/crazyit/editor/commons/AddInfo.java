package org.crazyit.editor.commons;

import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.handler.add.AddHandler;

/**
 * 添加的信息对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddInfo {

	//字符串, 在新增界面的text前显示, 例如: 文件名称
	private String info;
	
	//受新增操作影响的主frame
	private EditorFrame editorFrame;
	
	//新增完后的处理类
	private AddHandler handler;
	
	public AddInfo(String info, EditorFrame editorFrame, AddHandler handler) {
		this.info = info;
		this.editorFrame = editorFrame;
		this.handler = handler;
	}

	public AddHandler getHandler() {
		return handler;
	}

	public void setHandler(AddHandler handler) {
		this.handler = handler;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public EditorFrame getEditorFrame() {
		return editorFrame;
	}

	public void setEditorFrame(EditorFrame editorFrame) {
		this.editorFrame = editorFrame;
	}

}
