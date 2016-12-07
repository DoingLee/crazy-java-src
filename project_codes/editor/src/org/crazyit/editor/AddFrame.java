package org.crazyit.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.crazyit.editor.commons.AddInfo;

/**
 * 添加界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddFrame extends JFrame {

	//该Frame的JPanel
	private JPanel mainPanel;
	
	//项目名称
	private JPanel namePanel;
	
	//显示文件的JLabel
	private JLabel nameLabel;
	
	//输入名称的JTextField
	private JTextField nameText;
	
	//放按钮的Panel
	private JPanel buttonPanel;
	
	//确定按钮
	private JButton confirmButton;
	
	//取消按钮
	private JButton cancelButton;
	
	
	public AddFrame(final AddInfo info) {
		mainPanel = new JPanel();
		namePanel = new JPanel();
		//设置nameLabel的文字
		nameLabel = new JLabel(info.getInfo());
		nameText = new JTextField("", 20);
		buttonPanel = new JPanel();
		confirmButton = new JButton("确定");
		cancelButton = new JButton("取消");
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				cancel(info);
			}
		});
		setLocation(200, 200);
		setResizable(false);
		//文本框前面的字
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));		
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		nameText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//判断项目路径与名称是否有值, 如果两个text field都有值, 则确定按钮可用
				if (nameText.getText().equals("")) {
					confirmButton.setEnabled(false);
				} else {
					confirmButton.setEnabled(true);
				}
			}
		});
		
		//确定和取消的按钮
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		confirmButton.setEnabled(false);
		buttonPanel.add(confirmButton);
		buttonPanel.add(new JLabel("    "));
		buttonPanel.add(cancelButton);
		
		//为取消按钮添加监听器
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cancel(info);
			}
		});
		//为确定按钮添加监听器
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//如果输入框没有值，返回
				if (nameText.getText() == "") return;
				handerConfirm(info);
			}
		});
		mainPanel.add(namePanel);
		mainPanel.add(buttonPanel);
		add(mainPanel);
		pack();
	}
	
	//处理确定按钮的点击
	private void handerConfirm(AddInfo info) {
		//获取得用户输入
		String data = nameText.getText();
		//新增后会涉及的一些与业务相关的操作留给Handler类处理
		info.getHandler().afterAdd(info.getEditorFrame(), this, data);
	}
	
	private void cancel(AddInfo info) {
		//设置EditorFrame可用
		info.getEditorFrame().setEnabled(true);
		//让本窗口不可见
		setVisible(false);
	}
	

	
}


