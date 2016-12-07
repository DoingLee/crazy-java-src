package org.crazyit.foxmail.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.util.FileUtil;

/**
 * 主界面中邮件附件列表的监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MainListMouseListener extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JList list = (JList)e.getSource();
			FileObject file = (FileObject)list.getSelectedValue();
			if (file == null) return;
			handle(file);
		}
	}
	
	public void handle(FileObject file) {
		int result = JOptionPane.showOptionDialog(null, "请选择操作", "选择",
				0, JOptionPane.QUESTION_MESSAGE, null, 
				new Object[]{"打开", "另存为", "取消"}, null);
		if (result == 0) {
			openFile(file);//打开操作
		} else if (result == 1) {
			saveAs(file);//另存为操作
		}
	}
	
	//打开操作
	public void openFile(FileObject file) {
		try {
			Runtime.getRuntime().exec("cmd /c \"" + 
					file.getFile().getAbsolutePath() + "\"");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "打开文件错误, " + 
					file.getSourceName() + "文件不存在", "错误", 
					JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	//另存为操作, 打开文件选择器
	public void saveAs(FileObject file) {
		FolderChooser chooser = new FolderChooser(file);
		chooser.showSaveDialog(null);
	}
}

class FolderChooser extends JFileChooser {
	//需要另存为的文件
	private FileObject sourceFile;
	
	public FolderChooser(FileObject sourceFile) {
		this.sourceFile = sourceFile;
		//只能选目录
		this.setFileSelectionMode(DIRECTORIES_ONLY); 
	}
	//在文件选择器中选择了文件或者目录后
	public void approveSelection() {
		File targetFile = getSelectedFile();
		if (targetFile.isDirectory()) {
			//如果用户选的是目录, 即没有输入新的文件名, 则用sourceName作为文件名
			File newFile = new File(targetFile.getAbsolutePath() + File.separator 
					+ this.sourceFile.getSourceName());
			FileUtil.copy(this.sourceFile.getFile(), newFile);
		} else {
			//用户输入了新的文件名, 直接复制
			FileUtil.copy(this.sourceFile.getFile(), targetFile);
		}
		super.approveSelection();
	}	
}
