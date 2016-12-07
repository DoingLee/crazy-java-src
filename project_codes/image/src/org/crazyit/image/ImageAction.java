package org.crazyit.image;

import org.crazyit.image.ImageFrame;
import org.crazyit.image.tool.*;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * 按键处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageAction extends AbstractAction {
	private String name = "";
	private ImageFrame frame = null;
	private Color color = null;
	private Tool tool = null;
	private JPanel colorPanel = null;

	/**
	 * 构造器
	 * 
	 * @param icon
	 *            ImageIcon 图标
	 * @param name
	 *            名字
	 * @param frame
	 *            ImageFrame
	 */
	public ImageAction(Color color, JPanel colorPanel) {
		// 调用父构造器
		super();
		this.color = color;
		this.colorPanel = colorPanel;
	}

	/**
	 * 构造器
	 * 
	 * @param icon
	 *            ImageIcon 图标
	 * @param name
	 *            名字
	 * @param frame
	 *            ImageFrame
	 */
	public ImageAction(ImageIcon icon, String name, ImageFrame frame) {
		// 调用父构造器
		super("", icon);
		this.name = name;
		this.frame = frame;
	}

	/**
	 * 重写void actionPerformed( ActionEvent e )方法
	 * 
	 * @param e
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		// 设置tool
		tool = name != "" ? ToolFactory.getToolInstance(frame, name) : tool;
		if (tool != null) {
			// 设置正在使用的tool
			frame.setTool(tool);
		}
		if (color != null) {
			// 设置正在使用的颜色
			AbstractTool.color = color;
			colorPanel.setBackground(color);
		}
	}
}