package org.crazyit.mysql.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.crazyit.mysql.object.GlobalContext;
import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.util.FileUtil;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 进入系统的配置界面, 主要用于配置mysql_home
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ConfigFrame extends JFrame {

	//文本区域的Box
	private Box textBox = Box.createHorizontalBox();
	//按钮的Box
	private Box buttonBox = Box.createHorizontalBox();
	//界面中最大的Box
	private Box mainBox = Box.createVerticalBox();
	//MySQL安装目录文字
	private JLabel mysqlHomeLabel = new JLabel("MySQL的安装目录：");
	//选择目录后显示的JTextField
	private JTextField mysqlHomeField = new JTextField(20);
	//确定按钮
	private JButton confirmButton = new JButton("确定");
	//取消按钮
	private JButton cancelButton = new JButton("取消");
	//浏览目录按钮
	private JButton chooseButton = new JButton("选择");
	//主界面对象
	private MainFrame mainFrame;
	
	public ConfigFrame() {
		this.mysqlHomeField.setEditable(false);
		//从配置文件中读取MySQL Home的值设入JTextField中
		this.mysqlHomeField.setText(FileUtil.getMySQLHome());
		this.textBox.add(Box.createHorizontalStrut(20));
		this.textBox.add(this.mysqlHomeLabel);
		this.textBox.add(this.mysqlHomeField);
		this.textBox.add(Box.createHorizontalStrut(10));
		this.textBox.add(this.chooseButton);
		this.textBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.cancelButton);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.mainBox.add(textBox);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.mainBox.add(buttonBox);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);
		this.pack();
		this.setLocation(250, 250);
		this.setResizable(false);
		this.setTitle("MySQL管理器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initListeners();
	}
	
	private void showMainFrame() {
		//取得用户输入值
		String mysqlHome = this.mysqlHomeField.getText();
		//寻找用户选择的目录，判断是否可以找到MySQL安装目录下的bin目录
		File file = new File(mysqlHome + MySQLUtil.MYSQL_HOME_BIN);
		//找不到MySQL的安装目录，提示
		if (!file.exists()) {
			showMessage("请选择正确MySQL安装目录", "错误");
			return;
		}
		//如果配置文件的值与用户输入的值不相等，则重新写入配置文件中
		if (!mysqlHome.equals(FileUtil.getMySQLHome())) {
			FileUtil.saveMysqlHome(this.mysqlHomeField.getText());
		}
		GlobalContext ctx = new GlobalContext(mysqlHome);
		//读取全部的服务器连接配置
		List<ServerConnection> conns = ctx.getPropertiesHandler().getServerConnections();
		addConnectionToContext(ctx, conns);
		this.mainFrame = new MainFrame(ctx);
		this.mainFrame.setVisible(true);
		this.setVisible(false);
	}
	
	/**
	 * 将参数中的所有ServerConnection对象加到GlobalContext的Map中
	 * @param ctx
	 * @param conns
	 */
	private void addConnectionToContext(GlobalContext ctx, 
			List<ServerConnection> conns) {
		for (ServerConnection conn : conns) ctx.addConnection(conn);
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//初始化监听器
	public void initListeners() {
		this.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showMainFrame();
			}
		});
		this.cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.chooseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FileChooser chooser = new FileChooser(mysqlHomeField);
				chooser.showOpenDialog(null);
			}
		});
	}
}
class FileChooser extends JFileChooser {
	
	private JTextField field;
	
	public FileChooser(JTextField field) {
		this.field = field;
		//设置只可以选择目录
		this.setFileSelectionMode(FileChooser.DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		//设置JTextField的值
		this.field.setText(this.getSelectedFile().getAbsolutePath());
		super.approveSelection();
	}
	
}
