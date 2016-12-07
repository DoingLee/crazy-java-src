package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.crazyit.mysql.object.list.ProcedureData;
import org.crazyit.mysql.object.table.ProcedureType;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.util.MySQLUtil;

/**
 *  新增(修改存储过程)界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ProcedureFrame extends CommonFrame {

	private Box editBox = Box.createVerticalBox();
	private JScrollPane editPane;
	private JTextArea editArea = new JTextArea(25, 60);
	//界面下边的Box
	private Box downBox = Box.createVerticalBox();
	//参数
	private Box paramBox = Box.createHorizontalBox();
	private JLabel paramLabel = new JLabel("参数：");
	private JTextField paramField = new JTextField(20);
	//返回值
	private Box returnBox = Box.createHorizontalBox();
	private JLabel returnLabel = new JLabel("返回值：");
	private JTextField returnField = new JTextField(20);
	//类型
	private Box typeBox = Box.createHorizontalBox();
	private JLabel typeLabel = new JLabel("类型：");
	private JComboBox typeCombo = new JComboBox();
	
	private Box mainBox = Box.createVerticalBox();
	
	//类型对象
	private ProcedureType function = new ProcedureType(MySQLUtil.FUNCTION_TYPE, "函数");
	private ProcedureType producure = new ProcedureType(MySQLUtil.PROCEDURE_TYPE, "存储过程");
	//工具栏
	private JToolBar toolBar = new JToolBar();
	//保存
	private Action save = new AbstractAction("保存", new ImageIcon("images/save-procedure.gif")){
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	private ProcedureData procedureData;
	
	private String currentType;
	
	private NameFrame nameFrame;
	
	private MainFrame mainFrame;
	
	public ProcedureFrame(ProcedureData procedureData, 
			MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.procedureData = procedureData;

		this.nameFrame = new NameFrame(this);
		this.typeCombo.addItem(this.producure);
		this.typeCombo.addItem(this.function);
		this.toolBar.add(this.save).setToolTipText("保存");
		this.editArea.setLineWrap(true);
		this.editPane = new JScrollPane(this.editArea);
		this.editBox.add(this.editPane);
		
		this.paramBox.add(Box.createHorizontalStrut(20));
		this.paramBox.add(this.paramLabel);
		this.paramBox.add(Box.createHorizontalStrut(23));
		this.paramBox.add(this.paramField);
		this.paramBox.add(Box.createHorizontalStrut(20));
		this.returnBox.add(Box.createHorizontalStrut(20));
		this.returnBox.add(this.returnLabel);
		this.returnBox.add(Box.createHorizontalStrut(10));
		this.returnBox.add(this.returnField);
		this.returnBox.add(Box.createHorizontalStrut(20));
		this.typeBox.add(Box.createHorizontalStrut(20));
		this.typeBox.add(this.typeLabel);
		this.typeBox.add(Box.createHorizontalStrut(23));
		this.typeBox.add(this.typeCombo);
		this.typeBox.add(Box.createHorizontalStrut(143));
		this.downBox.add(Box.createVerticalStrut(10));
		this.downBox.add(this.paramBox);
		this.downBox.add(Box.createVerticalStrut(10));
		this.downBox.add(this.returnBox);
		this.downBox.add(Box.createVerticalStrut(10));
		this.downBox.add(this.typeBox);
		this.downBox.add(Box.createVerticalStrut(20));
		
		this.mainBox.add(editPane);
		this.mainBox.add(downBox);
		this.add(this.toolBar, BorderLayout.NORTH);
		this.add(this.mainBox);
		this.setLocation(200, 50);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//释放本窗口所有内存
				dispose();
			}
		});
		this.typeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeSelect();
			}
		});
		//初始化界面各值
		initField();
	}
	
	private void initField() {
		//初始化界面数据, 判断是新增还修改
		if (this.procedureData.getContent() != null) {
			//修改
			this.editArea.setText(procedureData.getContent());
			this.paramField.setText(procedureData.getArg());
			this.returnField.setText(procedureData.getReturnString());
			if (procedureData.getType().equals(MySQLUtil.PROCEDURE_TYPE)) {
				//存储过程
				this.returnField.setEditable(false);
				this.typeCombo.setSelectedItem(this.producure);
				this.currentType = MySQLUtil.PROCEDURE_TYPE;
				this.setTitle("存储过程");
			} else {
				//函数
				this.returnField.setEditable(true);
				this.typeCombo.setSelectedItem(this.function);
				this.currentType = MySQLUtil.FUNCTION_TYPE;
				this.setTitle("函数");
			}
		} else {
			//新增
			this.returnField.setEditable(false);
			this.currentType = MySQLUtil.PROCEDURE_TYPE;
			this.setTitle("存储过程");
		}
	}
	
	//保存方法
	private void save() {
		String content = this.editArea.getText();
		if (content.trim().equals("")) {
			showMessage("请输入完整数据", "错误");
			return;
		}
		//没内容就添加, 有内容就修改
		if (this.procedureData.getContent() == null) {
			add();
		} else {
			update();
		}
	}

	//新增方法，当输入名称后调用confirm方法
	private void add() {
		this.nameFrame.setVisible(true);
	}
	
	//修改方法
	private void update() {
		try {
			if (this.currentType.equals(MySQLUtil.PROCEDURE_TYPE)) {
				updateProcedure();
			} else {
				updateFunction();
			}
			this.setVisible(false);
			this.dispose();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//修改存储过程
	private void updateProcedure() {
		String arg = this.paramField.getText();
		String content = this.editArea.getText();
		this.procedureData.setArg(arg);
		this.procedureData.setContent(content);
		this.procedureData.updateProcedure();
	}
	
	//修改函数
	private void updateFunction() {
		String arg = this.paramField.getText();
		String content = this.editArea.getText();
		String returnString = this.returnField.getText();
		this.procedureData.setArg(arg);
		this.procedureData.setReturnString(returnString);
		this.procedureData.setContent(content);
		this.procedureData.updateFunction();
	}
		
	//实现CommonFrame的方法, 让NameFrame去调用
	public void confirm(String name) {
		try {
			if (this.currentType.equals(MySQLUtil.PROCEDURE_TYPE)) {
				//添加存储过程
				saveProcedure(name);
			} else {
				//添加函数
				saveFunction(name);
			}
			//刷新主界面数据
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			this.procedureData.setContent(null);
			showMessage(e.getMessage(), "错误");
		}
		this.nameFrame.setVisible(false);
	}
	
	//保存函数
	private void saveFunction(String name) {
		String arg = this.paramField.getText();
		String content = this.editArea.getText();
		String returnString = this.returnField.getText();
		this.procedureData.setArg(arg);
		this.procedureData.setName(name);
		this.procedureData.setReturnString(returnString);
		this.procedureData.setContent(content);
		this.procedureData.createFunction();
	}
	
	//保存存储过程
	private void saveProcedure(String name) {
		String arg = this.paramField.getText();
		String content = this.editArea.getText();
		this.procedureData.setArg(arg);
		this.procedureData.setName(name);
		this.procedureData.setContent(content);
		this.procedureData.createProcedure();
	}

	//当下拉框发生改变时触发该方法
	private void changeSelect() {
		ProcedureType selectType = (ProcedureType)this.typeCombo.getSelectedItem();
		if (selectType.getValue().equals(MySQLUtil.FUNCTION_TYPE)) {
			//函数可以有返回值
			this.returnField.setEditable(true);
			this.setTitle("函数");
		} else {
			this.returnField.setEditable(false);
			this.setTitle("存储过程");
		}
		this.currentType = selectType.getValue();
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
}
