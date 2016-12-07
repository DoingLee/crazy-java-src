package org.crazyit.editor.handler.save;

import org.crazyit.editor.EditorFrame;

/**
 * 保存的中介者
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class SaveMediator {

	//需要子类去实现的保存方法
	public abstract String doSave(EditorFrame editorFramet);
}
