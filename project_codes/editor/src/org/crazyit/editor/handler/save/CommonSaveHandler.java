package org.crazyit.editor.handler.save;

import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.commons.EditFile;
import org.crazyit.editor.util.FileUtil;

/**
 * 执行普通保存的处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class CommonSaveHandler implements SaveHandler {

	//提供一个保存方法，作为普通的保存
	public String save(EditorFrame editorFrame) {
		EditFile editFile = editorFrame.getCurrentFile();
		FileUtil.writeFile(editFile.getFile(), editFile.getEditPane().getText());
		return null; 
	}

}
