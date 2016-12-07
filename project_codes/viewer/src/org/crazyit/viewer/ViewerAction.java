package org.crazyit.viewer;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.crazyit.viewer.action.Action;

/**
 * 工具栏的Action类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ViewerAction extends AbstractAction {
	private String actionName = "";
	private ViewerFrame frame = null;
	
	//这个工具栏的AbstractAction所对应的org.crazyit.viewer.action包的某个Action实全
	private Action action = null;

	/**
	 * 构造器
	 * 
	 */
	public ViewerAction() {
		// 调用父构造器
		super();
	}

	/**
	 * 构造器
	 * 
	 * @param icon
	 *            ImageIcon 图标
	 * @param name
	 *            String
	 */
	public ViewerAction(ImageIcon icon, String actionName, ViewerFrame frame) {
		// 调用父构造器
		super("", icon);
		this.actionName = actionName;
		this.frame = frame;
	}

	/**
	 * 重写void actionPerformed( ActionEvent e )方法
	 * 
	 * @param e
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		ViewerService service = ViewerService.getInstance();
		Action action = getAction(this.actionName);
		//调用Action的execute方法
		action.execute(service, frame);
	}
	
	/**
	 * 通过actionName得到该类的实例
	 * @param actionName
	 * @return
	 */
	private Action getAction(String actionName) {
		try {
			if (this.action == null) {
				//创建Action实例
				Action action = (Action)Class.forName(actionName).newInstance();
				this.action = action;
			}
			return this.action;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}