package org.crazyit.foxmail.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import org.crazyit.foxmail.mail.MailSender;
import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.system.SystemHandler;
import org.crazyit.foxmail.util.FileUtil;

/**
 * 邮件编写界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailFrame extends JFrame {

	//收信人
	private JLabel receiverLabel = new JLabel("收信人：");
	private JTextField receiver = new JTextField(60);
	//抄送
	private JLabel ccLabel = new JLabel("抄  送：");
	private JTextField cc = new JTextField(60);
	//主题
	private JLabel subjectLabel = new JLabel("主  题：");
	private JTextField subject = new JTextField(60);
	//存放正文JTextArea的JScrollPane
	private JScrollPane textScrollPane;
	//正文
	private JTextArea textArea = new JTextArea(20, 50);
	//上下分隔的JSplitPane
	private JSplitPane mainPane;
	//正文的左右分隔JSplitPane
	private JSplitPane textSplitPane;
	//存放附件列表JList对象的JScrollPane
	private JScrollPane fileScrollPane;
	//附件列表
	private JList fileList;
	//收件人Box
	private Box receiverBox = Box.createHorizontalBox();
	//抄送Box
	private Box ccBox = Box.createHorizontalBox();
	//主题Box
	private Box subjectBox = Box.createHorizontalBox();
	//存放收件人Box，抄送Box和主题Box的Box
	private Box upBox = Box.createVerticalBox();
	//工具栏
	private JToolBar toolBar = new JToolBar();
	
//	private MailContext ctx;
	
	private SystemHandler systemHander;
	
	private MailSender mailSender;
	
	private MainFrame mainFrame;
	
	//发送
	private Action send = new AbstractAction("发送", new ImageIcon("images/send.gif")) {
		public void actionPerformed(ActionEvent e) {
			send();
		}
	};
	
	//保存至发件箱
	private Action saveOut = new AbstractAction("保存至发件箱", new ImageIcon("images/out-save.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveOut();
		}
	};
	
	//保存至草稿箱
	private Action saveDraft = new AbstractAction("保存至草稿箱", new ImageIcon("images/draft-save.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveDraft();
		}
	};
	
	//上传附件
	private Action file = new AbstractAction("上传附件", new ImageIcon("images/file.gif")) {
		public void actionPerformed(ActionEvent e) {
			upload();
		}
	};
	
	//删除附件
	private Action deleteFile = new AbstractAction("删除附件", new ImageIcon("images/delete.gif")) {
		public void actionPerformed(ActionEvent e) {
			deleteFile();
		}
	};
	
	public MailFrame(MainFrame mainFrame) {
		this.fileList = new JList();
		this.mainFrame = mainFrame;
		this.systemHander = mainFrame.getSystemHandler();
		this.mailSender = mainFrame.getMailSender();
		//初始化工具栏
		this.toolBar.add(this.send).setToolTipText("发送");
		this.toolBar.addSeparator();
		this.toolBar.add(this.saveOut).setToolTipText("保存至发件箱");
		this.toolBar.addSeparator();
		this.toolBar.add(this.saveDraft).setToolTipText("保存至草稿箱");
		this.toolBar.addSeparator();
		this.toolBar.add(this.file).setToolTipText("上传附件");
		this.toolBar.add(this.deleteFile).setToolTipText("删除附件");
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));//设置工具栏的边距

		//加入鼠标事件监听器
		this.fileList.addMouseListener(new SendListMouseListener());
		//收件人的Box
		this.receiverBox.add(Box.createHorizontalStrut(10));
		this.receiverBox.add(receiverLabel);
		this.receiverBox.add(Box.createHorizontalStrut(5));
		this.receiverBox.add(receiver);
		this.receiverBox.add(Box.createHorizontalStrut(10));
		//抄送的Box
		this.ccBox.add(Box.createHorizontalStrut(10));
		this.ccBox.add(ccLabel);
		this.ccBox.add(Box.createHorizontalStrut(12));
		this.ccBox.add(cc);
		this.ccBox.add(Box.createHorizontalStrut(10));
		//主题的Box
		this.subjectBox.add(Box.createHorizontalStrut(10));
		this.subjectBox.add(subjectLabel);
		this.subjectBox.add(Box.createHorizontalStrut(12));
		this.subjectBox.add(subject);
		this.subjectBox.add(Box.createHorizontalStrut(10));
		//正文以上的Box
		this.upBox.add(Box.createVerticalStrut(10));
		this.upBox.add(this.receiverBox);
		this.upBox.add(Box.createVerticalStrut(5));
		this.upBox.add(this.ccBox);
		this.upBox.add(Box.createVerticalStrut(5));
		this.upBox.add(this.subjectBox);
		this.upBox.add(Box.createVerticalStrut(10));
		
		this.textArea.setLineWrap(true);
		this.textScrollPane = new JScrollPane(this.textArea);
		//附件的JScrollPane
		this.fileScrollPane = new JScrollPane(this.fileList);
		//正文与附件的JSplitPane
		this.textSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.fileScrollPane, this.textScrollPane);
		this.textSplitPane.setDividerSize(5);
		this.textSplitPane.setDividerLocation(100);
		//正文和上面信息的JSplitPane
		this.mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				this.upBox, this.textSplitPane);
		this.mainPane.setDividerSize(5);
		//设置JFrame的信息
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setTitle("写邮件");
		this.add(this.mainPane);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				clean();
			}
		});
		this.setLocation(140, 80);
	}
	
	//保存到草稿箱
	private void saveDraft() {
		if (!validateInput()) return;
		//得到界面中的Mail, 该对象的位置在草稿箱
		Mail mail = getMail(FileUtil.DRAFT);
		this.systemHander.saveDraftBox(mail, this.mainFrame.getMailContext());
		//添加到MainFrame的发件箱集合中
		this.mainFrame.addDraftMail(mail);
		showMessage("保存到草稿箱成功");
	}
	
	//保存到发件箱
	private void saveOut() {
		if (!validateInput()) return;
		//得到界面中的Mail, 该对象原来的位置的发件箱
		Mail mail = getMail(FileUtil.OUTBOX);
		saveOut(mail);
		showMessage("保存到发件箱成功");
	}
	
	private void saveOut(Mail mail) {
		this.systemHander.saveOutBox(mail, this.mainFrame.getMailContext());
		//添加到MainFrame的发件箱集合中
		this.mainFrame.addOutMail(mail);
	}
	
	//删除附件
	private void deleteFile() {
		FileObject file = (FileObject)this.fileList.getSelectedValue();
		if (file == null) {
			showMessage("请选择需要删除的附件");
			return;
		}
		List<FileObject> files = getFileListObjects();
		files.remove(file);
		this.fileList.setListData(files.toArray());
	}
	
	//回复邮件初始化界面组件
	public void replyInit(Mail mail) {
		this.setVisible(true);
		this.fileList.setListData(mail.getFiles().toArray());
		this.receiver.setText(mail.getSender());
		this.subject.setText("RE: " + mail.getSubject());
		this.textArea.setText(mail.getContent());
		this.cc.setText(mail.getCCString());
	}
	
	//转发邮件初始化界面
	public void transmitInit(Mail mail) {
		this.setVisible(true);
		this.fileList.setListData(mail.getFiles().toArray());
		this.subject.setText(mail.getSubject());
		this.textArea.setText(mail.getContent());
		this.cc.setText(mail.getCCString());
	}
	
	//主界面点击发送邮件时初始化界面
	public void sendInit(Mail mail) {
		this.setVisible(true);
		this.fileList.setListData(mail.getFiles().toArray());
		this.receiver.setText(mail.getReceiverString());
		this.subject.setText(mail.getSubject());
		this.cc.setText(mail.getCCString());
		this.textArea.setText(mail.getContent());
	}
	
	public JList getFileList() {
		return this.fileList;
	}
	
	private void upload() {
		FileChooser chooser = new FileChooser(this);
		chooser.showOpenDialog(this);
	}
	
	//将界面的组件封装成一个Mail对象
	private Mail getMail(String fromBox) {
		String xmlName = UUID.randomUUID().toString() + ".xml";
		Mail mail = new Mail(xmlName, getAddressList(this.receiver), 
				this.mainFrame.getMailContext().getAccount(), 
				this.subject.getText(), new Date(), "10",
				true, this.textArea.getText(), fromBox);
		mail.setCcs(getAddressList(this.cc));
		mail.setFiles(getFileListObjects());
		return mail;
	}
	
	//发送方法
	private void send() {
		if (!validateInput()) return;
		//得到Mail对象, 该对象表示原来的位置在已发送
		Mail mail = getMail(FileUtil.SENT);
		try {
			this.mailSender.send(mail, this.mainFrame.getMailContext());
			//将Mail对象保存到发送成功的目录(sent)
			this.systemHander.saveSent(mail, this.mainFrame.getMailContext());
			//添加了已发送的集合中
			this.mainFrame.addSentMail(mail);
			showMessage("你的邮件已成功发送");
			//清空界面组件的值
			clean();
			this.setVisible(false);
		} catch (Exception e) {
			//发送失败保存到发件箱
			saveOut(mail);
			showMessage(e.getMessage());
		}
	}

	
	//空的附件列表数据
	private Object[] emptyListData = new Object[]{};
	//清空界面各个元素
	private void clean() {
		this.receiver.setText("");
		this.cc.setText("");
		this.subject.setText("");
		this.textArea.setText("");
		this.fileList.setListData(this.emptyListData);
	}
	
	//返回地址
	private List<String> getAddressList(JTextField field) {
		String all = field.getText();
		List<String> result = new ArrayList<String>();
		if (all.equals("")) return result; 
		for (String re : all.split(",")) {
			result.add(re);
		}
		return result;
	}
	
	private boolean validateInput() {
		if (this.receiver.getText().equals("")) {
			showMessage("请输入收件人");
			return false;
		}
		if (this.subject.getText().equals("")) {
			showMessage("请输入主题");
			return false;
		}
		if (this.mainFrame.getMailContext().getAccount() == null || 
				"".equals(this.mainFrame.getMailContext())) {
			showMessage("请配置你的帐号信息");
			return false;
		}
		return true;
	}
	
	private int showMessage(String s) {
		return JOptionPane.showConfirmDialog(this, s, "警告", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//从JList中得到附件的对象集合
	public List<FileObject> getFileListObjects() {
		ListModel model = this.fileList.getModel();
		List<FileObject> files = new ArrayList<FileObject>();
		for (int i = 0; i < model.getSize(); i++) {
			files.add((FileObject)model.getElementAt(i));
		}
		return files;
	}
}

class FileChooser extends JFileChooser {
	
	MailFrame mailFrame;
	
	public FileChooser(MailFrame mailFrame) {
		this.mailFrame = mailFrame;
		this.setFileSelectionMode(FILES_ONLY); 
	}

	@Override
	public void approveSelection() {
		File file = this.getSelectedFile();
		FileObject fo = new FileObject(file.getName(), file);
		List<FileObject> files = this.mailFrame.getFileListObjects();
		files.add(fo);
		mailFrame.getFileList().setListData(files.toArray());
		super.approveSelection();
	}
	
	
}
