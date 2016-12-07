package org.crazyit.gamehall.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.crazyit.gamehall.chatroom.client.ChatIndex;
import org.crazyit.gamehall.client.exception.ClientException;
import org.crazyit.gamehall.client.util.ImageUtil;
import org.crazyit.gamehall.commons.Connection;
import org.crazyit.gamehall.commons.Game;
import org.crazyit.gamehall.commons.User;

/**
 * 登录界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LoginFrame extends JFrame {

	//用户名
	private JLabel nameTextLabel = new JLabel("用户名：");
	private JTextField nameField = new JTextField(20);
	
	//性别选择
	private JLabel sexTextLabel = new JLabel("性别：");
	private JComboBox sexSelect = new JComboBox();
	
	//头像
	private JLabel headTextLabel = new JLabel("头像：");
	private JComboBox headSelect;
	
	//游戏选择
	private JLabel gameTextLabel = new JLabel("选择游戏：");
	private JComboBox gameSelect = new JComboBox();
	
	//连接地址
	private JLabel connectionLabel = new JLabel("连接地址：");
	private JTextField connectionField = new JTextField("127.0.0.1");
	
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	
	//头像图片
	private Map<String, ImageIcon> heads;
	
	private User user;
	
	public LoginFrame() {
		this.sexSelect.addItem("男");
		this.sexSelect.addItem("女");
		this.heads = ImageUtil.getHeads();
		//创建头像选择下拉框
		buildHeadSelect();
		//创建游戏选择下拉框
		buildGameSelect();
		
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(Box.createHorizontalStrut(30));
		nameBox.add(this.nameTextLabel);
		nameBox.add(Box.createHorizontalStrut(20));
		nameBox.add(this.nameField);
		nameBox.add(Box.createHorizontalStrut(30));
		
		Box sexBox = Box.createHorizontalBox();
		sexBox.add(Box.createHorizontalStrut(30));
		sexBox.add(this.sexTextLabel);
		sexBox.add(Box.createHorizontalStrut(33));
		sexBox.add(this.sexSelect);
		sexBox.add(Box.createHorizontalStrut(170));
		
		Box headBox = Box.createHorizontalBox();
		headBox.add(Box.createHorizontalStrut(30));
		headBox.add(this.headTextLabel);
		headBox.add(Box.createHorizontalStrut(33));
		headBox.add(this.headSelect);
		headBox.add(Box.createHorizontalStrut(170));
		
		
		Box gameBox = Box.createHorizontalBox();
		gameBox.add(Box.createHorizontalStrut(30));
		gameBox.add(this.gameTextLabel);
		gameBox.add(Box.createHorizontalStrut(7));
		gameBox.add(this.gameSelect);
		gameBox.add(Box.createHorizontalStrut(30));
		
		Box connectionBox = Box.createHorizontalBox();
		connectionBox.add(Box.createHorizontalStrut(30));
		connectionBox.add(this.connectionLabel);
		connectionBox.add(Box.createHorizontalStrut(7));
		connectionBox.add(this.connectionField);
		connectionBox.add(Box.createHorizontalStrut(30));
		
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		buttonBox.add(Box.createHorizontalStrut(30));
		buttonBox.add(this.cancelButton);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(nameBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(sexBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(headBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(connectionBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(gameBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		
		
		this.add(mainBox);
		this.setLocation(300, 200);
		this.setTitle("游戏登录");
		this.pack();
		this.setVisible(true);
		initListeners();
		this.user = new User();
	}
	
	//初始化按钮监听器
	private void initListeners() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void login() {
		if (this.nameField.getText().equals("")) {
			JOptionPane.showConfirmDialog(null, "请输入名称", "提示", 
					JOptionPane.OK_CANCEL_OPTION);
			return;
		}
		//设置User的各个属性
		setUser();
		this.user.setSocket(createSocket(this.connectionField.getText(), 12000));
		//得到用户所选择的游戏
		Game game = (Game)this.gameSelect.getSelectedItem();
		game.start(this.user);
		//启动线程
		ClientThread thread = new ClientThread(this.user);
		thread.start();
		this.setVisible(false);
	}
	
	private Socket createSocket(String address, int port) {
		try {
			//创建Socket
			return new Socket(address, port);
		} catch (Exception e) {
			throw new ClientException(e.getMessage());
		}
	}
	
	private void setUser() {
		//创建用户
		int sex = getSex();
		String name = this.nameField.getText();
		String id = UUID.randomUUID().toString();
		this.user.setId(id);
		this.user.setHeadImage((String)this.headSelect.getSelectedItem());
		this.user.setName(name);
		this.user.setSex(sex);
	}
	
	private int getSex() {
		String sex = (String)this.sexSelect.getSelectedItem();
		if (sex.equals("男")) return 0;
		else return 1;
	}
	
	private void buildGameSelect() {
		readDeployGame();
	}
	
	//读取game目录下的包的MANIFEST.MF文件, 再读取该文件中的Game-Class属性(有的话就加入下拉)
	private void readDeployGame() {
		try {
			File folder = new File("game");
			for (File file : folder.listFiles()) {
				if (isJar(file.getName())) {
					//得到jar文件
					JarFile jar = new JarFile(file);
					//得到元数据文件
					Manifest mf = jar.getManifest();
					//获得各个属性
					Attributes gameClassAttrs = mf.getMainAttributes();
					//查找Game-Class属性
					String gameClass = gameClassAttrs.getValue("Game-Class");
					if (gameClass != null) {
						Game game = (Game)Class.forName(gameClass).newInstance();
						this.gameSelect.addItem(game);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断文件名是为一个jar包的名称
	 * @param fileName
	 * @return
	 */
	private boolean isJar(String fileName) {
		int pointIndex = fileName.lastIndexOf(".");
		if (pointIndex != -1) {
			String subfix = fileName.substring(pointIndex + 1, fileName.length());
			if ("jar".equals(subfix)) {
				return true;
			}
		}
		return false;
	}
	
	
	//创建头像选择下拉
	private void buildHeadSelect() {
		this.headSelect = new JComboBox(this.heads.keySet().toArray());
		this.headSelect.setMaximumRowCount(5);
		this.headSelect.setRenderer(new HeadComboBoxRenderer(this.heads));
	}

}
