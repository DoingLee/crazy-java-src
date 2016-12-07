package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.crazyit.mysql.object.list.QueryData;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.util.FileUtil;

/**
 * 查询界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class QueryFrame extends JFrame {

	private JScrollPane editPane;
	
	private JTextArea editArea = new JTextArea(20, 40);
	
	private JToolBar toolBar = new JToolBar();
	
	private Action query = new AbstractAction("查询", new ImageIcon("images/run-query.gif")) {
		public void actionPerformed(ActionEvent e) {
			execute();
		}
	};
	
	private Action save = new AbstractAction("保存至文件", new ImageIcon("images/save-query.gif")) {
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	//该查询需要在某个数据库下执行
	private Database database;
	
	public QueryFrame(Database database) {
		this.database = database;
		this.editPane = new JScrollPane(this.editArea);
		this.add(editPane);
		this.setLocation(250, 150);
		this.toolBar.add(query).setToolTipText("查询");
		this.toolBar.add(save).setToolTipText("保存至文件");
		this.add(toolBar, BorderLayout.NORTH);
		this.setTitle("执行查询");
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//释放本窗口所有内存
				dispose();
			}
		});
		this.pack();
	}
	
	//保存SQL至文件，打开文件选择器
	private void save() {
		FolderChooser chooser = new FolderChooser(this);
		chooser.showSaveDialog(this);
	}
	
	//写入文件
	public void writeToFile(File file) {
		String content = this.editArea.getText();
		//将内容写到文件中
		FileUtil.writeToFile(file, content);
	}
	
	//执行方法
	private void execute() {
		String sql = this.editArea.getText();
		if (sql.toLowerCase().indexOf("select") != -1) {
			query();
		} else if (sql.toLowerCase().indexOf("call") != -1) {
			query();
		} else {
			try {
				QueryData queryData = new QueryData(this.database, sql);
				queryData.execute();
				showMessage("执行成功", "");
			} catch (Exception e) {
				showMessage(e.getMessage(), "错误");
			}
		}
	}
	
	//查询
	private void query() {
		//得到SQL
		String sql = this.editArea.getText();
		try {
			//封装一个QueryData对象
			QueryData queryData = new QueryData(this.database, sql);
			//显示DataFrame
			DataFrame dataFrame =new DataFrame(queryData);
			dataFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			showMessage(e.getMessage(), "错误");
		}
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
}

class FolderChooser extends JFileChooser {
	
	private QueryFrame queryFrame;
	
	public FolderChooser(QueryFrame queryFrame) {
		this.queryFrame = queryFrame;
		this.setFileSelectionMode(this.DIRECTORIES_ONLY);
	}

	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//用户选择了目录，则使用UUID创建文件名
			String fileName = UUID.randomUUID().toString();
			File newFile = new File(file.getAbsolutePath() + 
					File.separator + fileName + ".sql");
			this.queryFrame.writeToFile(newFile);
		} else {
			//用户选择了文件
			this.queryFrame.writeToFile(file);
		}
		super.approveSelection();
	}
	
	
}


