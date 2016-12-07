package org.crazyit.editor.handler.add;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.EditorFrame;

/**
 * 添加事件的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface AddHandler {

	//新增完后需要做的事情，需要做的事情由实现类去实现
	//参数为EditorFrame，AddFrame，输入的信息data
	void afterAdd(EditorFrame editorFrame, AddFrame addFrame, Object data);
}
