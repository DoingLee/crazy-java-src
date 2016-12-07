package org.crazyit.editor;

import org.crazyit.editor.tree.TreeCreator;
import org.crazyit.editor.tree.TreeCreatorImpl;

/**
 * 程序入口类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Main {

	public static void main(String[] args) {
		TreeCreator treeCreator = new TreeCreatorImpl();
		//创建EditorFrame，暂时不用设置可见
		EditorFrame editorFrame = new EditorFrame("ide", treeCreator);
		//将editorFrame对象作为SpaceFrame的构造参数
		SpaceFrame spaceFrame = new SpaceFrame(editorFrame);
		//让SpaceFrame可见
		spaceFrame.setVisible(true);
	}
}
