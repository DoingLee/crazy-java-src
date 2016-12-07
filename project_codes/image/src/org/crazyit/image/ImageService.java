package org.crazyit.image;

import org.crazyit.image.tool.AbstractTool;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;
import javax.swing.JViewport;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * 画图工具处理逻辑类(非工具)
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageService {
	private ImageFileChooser fileChooser = new ImageFileChooser();

	/**
	 * 获取屏幕的分辨率
	 * 
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}

	/**
	 * 初始化新drawSpace
	 * 
	 * @param frame
	 *            ImageFrame
	 * @return void
	 */
	public void initDrawSpace(ImageFrame frame) {
		// 获取画图对象
		Graphics g = frame.getBufferedImage().getGraphics();
		// 获取画布的大小
		Dimension d = frame.getDrawSpace().getPreferredSize();
		// 获取宽
		int drawWidth = (int) d.getWidth();
		// 获取高
		int drawHeight = (int) d.getHeight();
		// 绘画区
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, drawWidth, drawHeight);
	}

	/**
	 * repaint
	 * 
	 */
	public void repaint(Graphics g, BufferedImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		// 设置非绘画区的颜色
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int) screenSize.getWidth() * 10, (int) screenSize
				.getHeight() * 10);
		// 设置拖动点的颜色
		g.setColor(Color.BLUE);
		g.fillRect(drawWidth, drawHeight, 4, 4);
		g.fillRect(drawWidth, (int) drawHeight / 2 - 2, 4, 4);
		g.fillRect((int) drawWidth / 2 - 2, drawHeight, 4, 4);
		// 把缓冲的图片绘画出来
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}

	/**
	 * 创建鼠标图形
	 * 
	 * @param path
	 *            String 图形路径
	 * @return Cursor;
	 */
	public static Cursor createCursor(String path) {
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
				new Point(10, 10), "mycursor");
	}

	/**
	 * 设置JViewport
	 * 
	 * @param scroll
	 *            JScrollPane
	 * @param panel
	 *            JPanel
	 * @param width
	 *            int
	 * @parem width int
	 * @return void
	 */
	public static void setViewport(JScrollPane scroll, JPanel panel, int width,
			int height) {
		// 新建一个JViewport
		JViewport viewport = new JViewport();
		// 设置viewport的大小
		panel.setPreferredSize(new Dimension(width + 50, height + 50));
		// 设置viewport
		viewport.setView(panel);
		scroll.setViewport(viewport);
	}

	/**
	 * 保存图片
	 * 
	 * @param b
	 *            boolean 是否直接保存
	 * @param frame
	 *            ImageFrame
	 * @return void
	 */
	public void save(boolean b, ImageFrame frame) {
		if (b) {
			// 如果选择保存
			if (fileChooser.showSaveDialog(frame) == ImageFileChooser.APPROVE_OPTION) {
				// 获取当前路径
				File currentDirectory = fileChooser.getCurrentDirectory();
				// 获取文件名
				String fileName = fileChooser.getSelectedFile().getName();
				// 获取后缀名
				String suf = fileChooser.getSuf();
				// 组合保存路径
				String savePath = currentDirectory + "\\" + fileName + "."
						+ suf;
				try {
					// 将图片写到保存路径
					ImageIO.write(frame.getBufferedImage(), suf, new File(
							savePath));
				} catch (java.io.IOException ie) {
					ie.printStackTrace();
				}
				// 设置保存后的窗口标题
				frame.setTitle(fileName + "." + suf + " - 画图");
				// 已保存
				frame.getBufferedImage().setIsSaved(true);
			}
		} else if (!frame.getBufferedImage().isSaved()) {
			// 新建一个对话框
			JOptionPane option = new JOptionPane();
			// 显示确认保存对话框YES_NO_OPTION
			int checked = option.showConfirmDialog(frame, "保存改动?", "画图",
					option.YES_NO_OPTION, option.WARNING_MESSAGE);
			// 如果选择是
			if (checked == option.YES_OPTION) {
				// 保存图片
				save(true, frame);
			}
		}
	}

	/**
	 * 打开图片
	 * 
	 * @param frame
	 *            ImageFrame
	 * @return void
	 */
	public void open(ImageFrame frame) {
		save(false, frame);
		// 如果打开一个文件
		if (fileChooser.showOpenDialog(frame) == ImageFileChooser.APPROVE_OPTION) {
			// 获取选择的文件
			File file = fileChooser.getSelectedFile();
			// 设置当前文件夹
			fileChooser.setCurrentDirectory(file);
			BufferedImage image = null;
			try {
				// 从文件读取图片
				image = ImageIO.read(file);
			} catch (java.io.IOException e) {
				e.printStackTrace();
				return;
			}
			// 宽，高
			int width = image.getWidth();
			int height = image.getHeight();
			AbstractTool.drawWidth = width;
			AbstractTool.drawHeight = height;
			// 创建一个MyImage
			MyImage myImage = new MyImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			// 把读取到的图片画到myImage上面
			myImage.getGraphics().drawImage(image, 0, 0, width, height, null);
			frame.setBufferedImage(myImage);
			// repaint
			frame.getDrawSpace().repaint();
			// 重新设置viewport
			ImageService.setViewport(frame.getScroll(), frame.getDrawSpace(),
					width, height);
			// 设置保存后的窗口标题
			frame.setTitle(fileChooser.getSelectedFile().getName() + " - 画图");
		}
	}

	/**
	 * 新图片
	 * 
	 * @param frame
	 *            ImageFrame
	 * @return void
	 */
	public void createGraphics(ImageFrame frame) {
		save(false, frame);
		// 宽，高
		int width = (int) getScreenSize().getWidth() / 2;
		int height = (int) getScreenSize().getHeight() / 2;
		AbstractTool.drawWidth = width;
		AbstractTool.drawHeight = height;
		// 创建一个MyImage
		MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = myImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		frame.setBufferedImage(myImage);
		// repaint
		frame.getDrawSpace().repaint();
		// 重新设置viewport
		ImageService.setViewport(frame.getScroll(), frame.getDrawSpace(),
				width, height);
		// 设置保存后的窗口标题
		frame.setTitle("未命名 - 画图");
	}

	/**
	 * 编辑颜色
	 * 
	 * @param frame
	 *            ImageFrame
	 * @rerun void
	 */
	public void editColor(ImageFrame frame) {
		// 获取颜色
		Color color = JColorChooser.showDialog(frame.getColorChooser(), "编辑颜色",
				Color.BLACK);
		color = color == null ? AbstractTool.color : color;
		// 设置工具的颜色
		AbstractTool.color = color;
		// 设置目前显示的颜色
		frame.getCurrentColorPanel().setBackground(color);
	}

	/**
	 * 退出
	 * 
	 * @parame frame ImageFrame
	 * @return void
	 */
	public void exit(ImageFrame frame) {
		save(false, frame);
		System.exit(0);
	}

	/**
	 * 设置是否可见
	 * 
	 * @param panel
	 *            JPanel
	 * @return void
	 */
	public void setVisible(JPanel panel) {
		boolean b = panel.isVisible() ? false : true;
		panel.setVisible(b);
	}

	/**
	 * 处理菜单事件
	 * 
	 * @param frame
	 *            ImageFrame
	 * @parem cmd String
	 * @return void
	 */
	public void menuDo(ImageFrame frame, String cmd) {
		if (cmd.equals("编辑颜色")) {
			editColor(frame);
		}

		if (cmd.equals("工具箱(T)")) {
			setVisible(frame.getToolPanel());
		}

		if (cmd.equals("颜料盒(C)")) {
			setVisible(frame.getColorPanel());
		}

		if (cmd.equals("新建(N)")) {
			createGraphics(frame);
		}

		if (cmd.equals("打开(O)")) {
			open(frame);
		}

		if (cmd.equals("保存(S)")) {
			save(true, frame);
		}

		if (cmd.equals("退出(X)")) {
			exit(frame);
		}

	}

}