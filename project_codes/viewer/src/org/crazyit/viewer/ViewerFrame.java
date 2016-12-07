package org.crazyit.viewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 主界面对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ViewerFrame extends JFrame {
	// 设置读图区的宽和高
	private int width = 800;
	private int height = 600;
	// 用一个JLabel放置图片
	JLabel label = new JLabel();
	ViewerService service = ViewerService.getInstance();

	// 加给菜单的事件监听器
	ActionListener menuListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			service.menuDo(ViewerFrame.this, e.getActionCommand());
		}
	};

	/**
	 * 构造器
	 */
	public ViewerFrame() {
		super();
		// 初始化这个JFrame
		init();
	}

	/**
	 * 初始化
	 * 
	 * @return void
	 */
	public void init() {
		// 设置标题
		this.setTitle("看图程序");
		// 设置大小
		this.setPreferredSize(new Dimension(width, height));
		// 创建菜单
		createMenuBar();
		// 创建工具栏
		JPanel toolBar = createToolPanel();
		// 把工具栏和读图区加到JFrame里面
		this.add(toolBar, BorderLayout.NORTH);
		this.add(new JScrollPane(label), BorderLayout.CENTER);
		// 设置为可见
		this.setVisible(true);
		this.pack();
	}

	/**
	 * 获取JLabel
	 * 
	 * @return JLabel
	 */
	public JLabel getLabel() {
		return this.label;
	}

	/**
	 * 创建工具栏
	 * 
	 * @return JPanel
	 */
	public JPanel createToolPanel() {
		// 创建一个JPanel
		JPanel panel = new JPanel();
		// 创建一个标题为"工具"的工具栏
		JToolBar toolBar = new JToolBar("工具");
		// 设置为不可拖动
		toolBar.setFloatable(false);
		// 设置布局方式
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 工具数组
		String[] toolarr = { "org.crazyit.viewer.action.OpenAction", 
				"org.crazyit.viewer.action.LastAction", 
				"org.crazyit.viewer.action.NextAction", 
				"org.crazyit.viewer.action.BigAction", 
				"org.crazyit.viewer.action.SmallAction" };
		for (int i = 0; i < toolarr.length; i++) {
			ViewerAction action = new ViewerAction(new ImageIcon("img/"
					+ toolarr[i] + ".gif"), toolarr[i], this);
			// 以图标创建一个新的button
			JButton button = new JButton(action);
			// 把button加到工具栏中
			toolBar.add(button);
		}
		panel.add(toolBar);
		// 返回
		return panel;
	}

	/**
	 * 创建菜单栏
	 * 
	 * @return void
	 */
	public void createMenuBar() {
		// 创建一个JMenuBar放置菜单
		JMenuBar menuBar = new JMenuBar();
		// 菜单文字数组，以下面的menuItemArr一一对应
		String[] menuArr = { "文件(F)", "工具(T)", "帮助(H)" };
		// 菜单项文字数组
		String[][] menuItemArr = { { "打开(O)", "-", "退出(X)" },
				{ "放大(M)", "缩小(O)", "-", "上一个(X)", "下一个(P)" }, { "帮助主题", "关于" } };
		// 遍历menuArr与menuItemArr去创建菜单
		for (int i = 0; i < menuArr.length; i++) {
			// 新建一个JMenu菜单
			JMenu menu = new JMenu(menuArr[i]);
			for (int j = 0; j < menuItemArr[i].length; j++) {
				// 如果menuItemArr[i][j]等于"-"
				if (menuItemArr[i][j].equals("-")) {
					// 设置菜单分隔
					menu.addSeparator();
				} else {
					// 新建一个JMenuItem菜单项
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					// 把菜单项加到JMenu菜单里面
					menu.add(menuItem);
				}
			}
			// 把菜单加到JMenuBar上
			menuBar.add(menu);
		}
		// 设置JMenubar
		this.setJMenuBar(menuBar);
	}
}