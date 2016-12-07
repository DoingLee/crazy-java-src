package org.crazyit.image;

import org.crazyit.image.tool.*;
import org.crazyit.image.ImageAction;
import static org.crazyit.image.tool.Tool.*;
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
import javax.swing.JColorChooser;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 界面对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageFrame extends JFrame {
	// 定义业务逻辑类
	private ImageService service = new ImageService();
	// 初始化屏幕的尺寸
	private Dimension screenSize = service.getScreenSize();
	// 设置默认画板
	private JPanel drawSpace = createDrawSpace();
	// 设置缓冲图片
	private MyImage bufferedImage = new MyImage(
			(int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2,
			BufferedImage.TYPE_INT_RGB);
	// 设置当前使用的工具
	private Tool tool = null;
	// 设置画图对象
	Graphics g = bufferedImage.getGraphics();
	// 颜色显示面板
	private JPanel currentColorPanel = null;
	// 颜色选择器
	private JColorChooser colorChooser = getColorChooser();
	// 加给菜单的事件监听器
	ActionListener menuListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			service.menuDo(ImageFrame.this, e.getActionCommand());
		}
	};
	// 默认JScrollPane
	private JScrollPane scroll = null;
	// 工具栏
	JPanel toolPanel = createToolPanel();
	// 颜色面板
	JPanel colorPanel = createColorPanel();

	/**
	 * 默认构造器
	 * 
	 * @return void
	 */
	public ImageFrame() {
		super();
		// 初始化
		init();
	}

	public ImageService getService() {
		return this.service;
	}

	/**
	 * 初始化ImageFrame
	 * 
	 * @return void
	 */
	public void init() {
		// 设置标题
		this.setTitle("未命名 - 画图");
		// 初始化画图
		service.initDrawSpace(this);
		// 设置标题
		// 获取正在使用的工具
		tool = ToolFactory.getToolInstance(this, PENCIL_TOOL);

		// 创建鼠标运动监听器
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			// 拖动鼠标
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}

			// 移动鼠标
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}

		};
		// 创建鼠标监听器
		MouseListener mouseListener = new MouseAdapter() {
			// 松开鼠标
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}

			// 按下鼠标
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}

			// 点击鼠标
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};

		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);

		createMenuBar();
		// 使用drawSpace创建一个滚动窗口
		scroll = new JScrollPane(drawSpace);
		// 设置viewport
		ImageService.setViewport(scroll, drawSpace, bufferedImage.getWidth(),
				bufferedImage.getHeight());
		// 将panel加到本Frame上面
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.SOUTH);
	}

	/**
	 * 获取画布
	 * 
	 * @return JPanel
	 */
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}

	/**
	 * 获取JScroolPane
	 * 
	 * @return JScrollPane
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * 获取工具栏
	 * 
	 * @return JPanel
	 */
	public JPanel getToolPanel() {
		return this.toolPanel;
	}

	/**
	 * 获取颜色面板
	 * 
	 * @return JPanel
	 */
	public JPanel getColorPanel() {
		return this.colorPanel;
	}

	/**
	 * 获取图片
	 * 
	 * @return MyImage
	 */
	public MyImage getBufferedImage() {
		return this.bufferedImage;
	}

	/**
	 * 设置图片
	 * 
	 * @param bufferedImage
	 *            MyImage
	 * @return void
	 */
	public void setBufferedImage(MyImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * 设置工具
	 * 
	 * @param tool
	 *            Tool
	 * @return void
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}

	/**
	 * 获取工具
	 * 
	 * @return Tool
	 */
	public Tool getTool() {
		return this.tool;
	}

	/**
	 * 获取颜色选择器
	 * 
	 * @return JColorChooser
	 */
	public JColorChooser getColorChooser() {
		if (colorChooser == null) {
			colorChooser = new JColorChooser();
		}
		return colorChooser;
	}

	/**
	 * 创建简单颜色选择板
	 * 
	 * @return JPanel
	 */
	public JPanel createColorPanel() {
		// 新建一个JPanel
		JPanel panel = new JPanel();
		// 设置布局方式
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 新建一个JToolBar
		JToolBar toolBar = new JToolBar("颜色");
		// 设置为不可拖动
		toolBar.setFloatable(false);
		// 设置与边界的距离
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		// 设置布局方式
		toolBar.setLayout(new GridLayout(2, 6, 2, 2));
		// Color类中的已有颜色
		Color[] colorArr = { BLACK, BLUE, CYAN, GRAY, GREEN, LIGHT_GRAY,
				MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW };
		JButton[] panelArr = new JButton[colorArr.length];
		// 正在使用的颜色
		currentColorPanel = new JPanel();
		currentColorPanel.setBackground(Color.BLACK);
		currentColorPanel.setPreferredSize(new Dimension(20, 20));
		// 创建这些颜色的button
		for (int i = 0; i < panelArr.length; i++) {
			// 创建JButton
			panelArr[i] = new JButton(new ImageAction(colorArr[i],
					currentColorPanel));
			// 设置button的颜色
			panelArr[i].setBackground(colorArr[i]);
			// 把button加到toobar中
			toolBar.add(panelArr[i]);
		}
		panel.add(currentColorPanel);
		panel.add(toolBar);
		// 返回
		return panel;
	}

	/**
	 * 获取颜色显示面板
	 * 
	 * @return JPanel
	 */
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}

	/**
	 * 获取screenSize
	 * 
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		return this.screenSize;
	}

	/**
	 * 创建菜单栏
	 * 
	 * @return void
	 */
	public void createMenuBar() {
		// 创建一个JMenuBar放置菜单
		JMenuBar menuBar = new JMenuBar();
		// 菜单文字数组，与下面的menuItemArr一一对应
		String[] menuArr = { "文件(F)", "查看(V)", "颜色(C)", "帮助(H)" };
		// 菜单项文字数组
		String[][] menuItemArr = { { "新建(N)", "打开(O)", "保存(S)", "-", "退出(X)" },
				{ "工具箱(T)", "颜料盒(C)" }, { "编辑颜色" }, { "帮助主题", "关于" } };
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

	/**
	 * 创建画板
	 * 
	 * @return JPanel
	 */
	public JPanel createDrawSpace() {
		JPanel drawSpace = new DrawSpace();
		// 设置drawSpace的大小
		drawSpace.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight() - 150));
		return drawSpace;
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
		// 设置为垂直排列
		toolBar.setOrientation(toolBar.VERTICAL);
		// 设置为可以拖动
		toolBar.setFloatable(true);
		// 设置与边界的距离
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		// 设置布局方式
		toolBar.setLayout(new GridLayout(5, 2, 2, 2));
		// 工具数组
		String[] toolarr = { PENCIL_TOOL, BRUSH_TOOL, COLORPICKED_TOOL,
				ATOMIZER_TOOL, ERASER_TOOL, LINE_TOOL, POLYGON_TOOL, RECT_TOOL,
				ROUND_TOOL, ROUNDRECT_TOOL };
		for (int i = 0; i < toolarr.length; i++) {
			ImageAction action = new ImageAction(new ImageIcon("img/"
					+ toolarr[i] + ".jpg"), toolarr[i], this);
			// 以图标创建一个新的button
			JButton button = new JButton(action);
			// 把button加到工具栏中
			toolBar.add(button);
		}
		panel.add(toolBar);
		// 返回
		return panel;
	}

	// 画图区域
	public class DrawSpace extends JPanel {
		/**
		 * 重写void paint( Graphics g )方法
		 * 
		 * @param g
		 *            Graphics
		 * @return void
		 */
		public void paint(Graphics g) {
			// draw
			service.repaint(g, bufferedImage);
		}
	}

}