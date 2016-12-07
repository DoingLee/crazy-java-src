package org.crazyit.foxmail.ui;

import javax.swing.JOptionPane;

import org.crazyit.foxmail.object.FileObject;

/**
 * 写邮件时的附件列表监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SendListMouseListener extends MainListMouseListener {

	@Override
	public void handle(FileObject file) {
		int result = JOptionPane.showOptionDialog(null, "请选择操作", "选择",
				0, JOptionPane.QUESTION_MESSAGE, null, 
				new Object[]{"打开", "取消"}, null);
		if (result == 0) {
			//打开操作
			openFile(file);
		}
	}

}
