package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.crazyit.mysql.object.list.ViewData;

/**
 * 新增(修改视图界面)
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ViewFrame extends CommonFrame {

	private JScrollPane editPane;
	
	private JTextArea editArea = new JTextArea(20, 40);
	
	private JToolBar toolBar = new JToolBar();
	
	private NameFrame nameFrame;
	
	private Action save = new AbstractAction("保存", new ImageIcon("images/save-view.gif")) {
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	//当前的视图对象
	private ViewData viewData;
	//主界面对象
	private MainFrame mainFrame;
	
	public ViewFrame(ViewData viewData, MainFrame mainFrame) {
		this.viewData = viewData;
		this.mainFrame = mainFrame;
		this.nameFrame = new NameFrame(this);
		if (viewData.getContent() != null) {
			this.editArea.setText(viewData.getContent());
		}
		this.editArea.setLineWrap(true);
		this.editPane = new JScrollPane(this.editArea);
		this.add(editPane);
		this.setLocation(250, 150);
		this.toolBar.add(save).setToolTipText("保存");
		this.add(toolBar, BorderLayout.NORTH);
		this.setTitle("新增视图");
		this.pack();
	}
	
	public void setViewData(ViewData viewData) {
		this.viewData = viewData;
	}
	
	public ViewData getViewData() {
		return this.viewData;
	}
	
	public JTextArea getEditArea() {
		return this.editArea;
	}
	
	private void save() {
		if (this.viewData.getContent() == null) {
			//内容为空，添加
			add();
		} else {
			//修改
			update();
		}
	}
	
	//添加
	private void add() {
		this.nameFrame.setVisible(true);
	}
	
	//修改
	private void update() {
		try {
			String content = this.editArea.getText();
			this.viewData.setContent(content);
			this.viewData.alterView();
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	public int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//该方法由NameFrame调用
	public void confirm(String name) {
		//得到用户输入的视图定义
		String content = this.getEditArea().getText();
		if (content.trim().equals("") || name.trim().equals("")) {
			this.showMessage("请输入完整信息", "错误");
			return;
		}
		ViewData viewData = this.getViewData();
		try {
			viewData.createView();
			viewData.setName(name);
			viewData.setContent(content);
			this.setViewData(viewData);
			nameFrame.setVisible(false);
			//刷新MainFrame列表
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
}
