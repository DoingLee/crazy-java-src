package org.crazyit.flashget.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.crazyit.flashget.ContextHolder;
import org.crazyit.flashget.DownloadContext;
import org.crazyit.flashget.info.Info;
import org.crazyit.flashget.navigation.DownloadNode;
import org.crazyit.flashget.navigation.DownloadingNode;
import org.crazyit.flashget.navigation.FailNode;
import org.crazyit.flashget.navigation.FinishNode;
import org.crazyit.flashget.navigation.TaskNode;
import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.state.Downloading;
import org.crazyit.flashget.state.Failed;
import org.crazyit.flashget.state.Finished;
import org.crazyit.flashget.state.Pause;
import org.crazyit.flashget.util.DateUtil;
import org.crazyit.flashget.util.FileUtil;
import org.crazyit.flashget.util.ImageUtil;

/**
 * 主界面
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MainFrame extends JFrame {

	//导航树
	private NavigationTree navTree;
	//下载列表
	private DownloadTable downloadTable;
	//信息列表
	private JList infoJList;
	//工具栏
	private JToolBar toolBar = new JToolBar();
	//新任务界面
	private NewTaskFrame taskFrame;
	//所有任务节点
	private TaskNode taskNode = new TaskNode();
	//正在下载节点
	private DownloadingNode downloadingNode = new DownloadingNode();
	//下载失败节点
	private FailNode failNode = new FailNode();
	//下载完成节点
	private FinishNode finishNode = new FinishNode();
	//当前用户浏览的节点
	private DownloadNode currentNode = taskNode;
	
	//信息列表的对象
	private final static String FILE_SIZE_TEXT = "文件大小: ";
	private final static String FILE_PATH_TEXT = "文件路径: ";
	private final static String DOWNLOAD_DATE_TEXT = "下载时间: ";
	private final static String RESOURCE_INFO_TEXT = "资源信息: ";
	private List<Info> infoList = new ArrayList<Info>();
	private Info fileSize = new Info(FILE_SIZE_TEXT);
	private Info filePath = new Info(FILE_PATH_TEXT);
	private Info downloadDate = new Info(DOWNLOAD_DATE_TEXT);
	private Info info = new Info(RESOURCE_INFO_TEXT);
	
	private Action newTask = new AbstractAction("新任务", new ImageIcon("images/tool/new-download.gif")) {
		public void actionPerformed(ActionEvent e) {
			newTask();
		}
	};
	
	private Action start = new AbstractAction("开始", new ImageIcon("images/tool/do-download.gif")) {
		public void actionPerformed(ActionEvent e) {
			start();
		}
	};
	
	private Action pause = new AbstractAction("暂停", new ImageIcon("images/tool/pause.gif")) {
		public void actionPerformed(ActionEvent e) {
			pause();
		}
	};
	
	private Action delete = new AbstractAction("删除任务", new ImageIcon("images/tool/delete.gif")) {
		public void actionPerformed(ActionEvent e) {
			delete();
		}
	};
	
	private Action deleteFinished = new AbstractAction("删除任务", new ImageIcon("images/tool/remove-finished.gif")) {
		public void actionPerformed(ActionEvent e) {
			deleteFinished();
		}
	};
	
	ActionListener refreshTable = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//刷新列表
			downloadTable.updateUI();
		}
	};

	//悬浮窗口
	private SuspendWindow suspendWindow;
	//任务栏图标
	private TrayIcon trayIcon;
	//任务栏图标菜单
	private PopupMenu popupMenu = new PopupMenu();
	private MenuItem openItem = new MenuItem("打开/关闭");
	private MenuItem newItem = new MenuItem("新建下载任务");
	private MenuItem startItem = new MenuItem("开始全部任务");
	private MenuItem pauseItem = new MenuItem("暂停全部任务");
	private MenuItem removeItem = new MenuItem("删除完成任务");
	private MenuItem quitItem = new MenuItem("退出");
	
	private BufferedImage trayIconImage = ImageUtil.getImage(ImageUtil.TRAY_ICON_PATH);
	
	public MainFrame() {
		//创建导航树
		createTree();
		createDownloadTable();
		//创建信息列表
		createList();
		this.taskFrame = new NewTaskFrame();
		//创建悬浮窗口
		this.suspendWindow = new SuspendWindow(this);
		//创建任务栏图标
		createTrayIcon();
		//创建工具栏
		createToolBar();
		//得到屏幕大小
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//导航滚动
		JScrollPane navPane = new JScrollPane(this.navTree);
		JScrollPane downloadPane = new JScrollPane(this.downloadTable);
		int downloadPaneHeight = (int)(screen.height/1.5);
		int downloadPaneWidth = (int)(screen.width/0.8);
		downloadPane.setPreferredSize(new Dimension(downloadPaneWidth, downloadPaneHeight));
		JScrollPane infoPane = new JScrollPane(this.infoJList);
		//主界面右边的分隔Pane
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				downloadPane, infoPane);
		rightPane.setDividerLocation(500);
		rightPane.setDividerSize(3);
		//主界面的分隔Pane
		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				navPane, rightPane);
		mainPane.setDividerSize(3);
		mainPane.setDividerLocation((int)(screen.width/5.5));
		
		this.add(mainPane);
		this.setPreferredSize(new Dimension(screen.width, screen.height - 30));
		this.setVisible(true);
		this.setTitle("下载工具");
		this.pack();
		initlisteners();
		//创建定时器
		Timer timer = new Timer(1000, refreshTable);
		timer.start();
		//读取序列化文件
		reverseSer();
	}
	
	public NewTaskFrame getNewTaskFrame() {
		return this.taskFrame;
	}
	
	/**
	 * 创建任务栏图标
	 */
	private void createTrayIcon() {
		this.popupMenu.add(openItem);
		this.popupMenu.add(newItem);
		this.popupMenu.add(startItem);
		this.popupMenu.add(pauseItem);
		this.popupMenu.add(removeItem);
		this.popupMenu.add(quitItem);
		try {
			SystemTray tray = SystemTray.getSystemTray();
			this.trayIcon = new TrayIcon(trayIconImage, "多线程下载工具", this.popupMenu);
			this.trayIcon.setToolTip("多线程下载工具");
			tray.add(this.trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initlisteners() {
		//点击列表鼠标监听器
		this.downloadTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//得到点击的资源
				Resource r = getResource();
				if (r == null) return;
				//设置信息显示区域的值
				fileSize.setValue(FILE_SIZE_TEXT + r.getSize());
				filePath.setValue(FILE_PATH_TEXT + 
						r.getSaveFile().getAbsolutePath());
				downloadDate.setValue(DOWNLOAD_DATE_TEXT + 
						DateUtil.formatDate(r.getDownloadDate()));
				info.setValue(RESOURCE_INFO_TEXT + r.getState().getState());
				//重新设置JList数据
				infoJList.setListData(infoList.toArray());
			}
		});
		//点击导航树鼠标监听器
		this.navTree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectTree();
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		//任务栏图标监听器
		this.trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setVisible(true);
				}
			}
		});
		this.openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isVisible()) setVisible(false);
				else setVisible(true);
			}
		});
		this.newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskFrame.setVisible(true);
			}
		});
		this.startItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startAllTask();
			}
		});
		this.pauseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseAllTask();
			}
		});
		this.removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFinished();
			}
		});
		this.quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serializable();
				System.exit(0);
			}
		});
	}
	
	/**
	 * 点击导航树触发的方法
	 */
	private void selectTree() {
		DownloadNode selectNode = getSelectNode();
		this.currentNode = selectNode;
		refreshTable();
	}
	
	/**
	 * 刷新列表
	 */
	private void refreshTable() {
		DownloadTableModel model = (DownloadTableModel)this.downloadTable.getModel();
		model.setResources(ContextHolder.ctx.getResources(currentNode));
	}
	
	private DownloadNode getSelectNode() {
		TreePath treePath = this.navTree.getSelectionPath();
		if (treePath == null) return null;
		//获得选中的TreeNode
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		return (DownloadNode)node.getUserObject();
	}
	
	private void addTableData() {
		DownloadTableModel model = (DownloadTableModel)this.downloadTable.getModel();
		//将保存的资源设置到列表中
		model.setResources(ContextHolder.ctx.resources);
		//刷新列表
		this.downloadTable.refresh();
	}
	
	/**
	 * 反序列化
	 */
	public void reverseSer() {
		File serFile = FileUtil.SERIALIZABLE_FILE;
		if (!serFile.exists()) return;
		try {
			//得到文件输入流
			FileInputStream fis = new FileInputStream(serFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//设置ContextHolder的DownloadContext
			ContextHolder.ctx = (DownloadContext)ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置列表
		addTableData();
	}
	
	/**
	 * 序列化(DownloadContext对象)
	 */
	public void serializable() {
		try {
			//序列化前先将所有正在下载的任务停止
			for (Resource r : ContextHolder.ctx.resources) {
				if (r.getState() instanceof Downloading) {
					r.setState(ContextHolder.ctx.PAUSE);
				}
			}
			File serFile = FileUtil.SERIALIZABLE_FILE;
			//判断序列化文件是否存在, 不存在则创建
			if (!serFile.exists()) serFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(serFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//将上下文对象写到序列化文件中
			oos.writeObject(ContextHolder.ctx);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JTable getDownloadTable() {
		return this.downloadTable;
	}
	
	private void createDownloadTable() {
		DownloadTableModel tableModel = new DownloadTableModel();
		this.downloadTable = new DownloadTable();
		this.downloadTable.setModel(tableModel);
		this.downloadTable.setTableFace();
	}

	private void createToolBar() {
		this.toolBar.setFloatable(false);
		this.toolBar.add(this.newTask).setToolTipText("新下载任务");
		this.toolBar.add(this.start).setToolTipText("开始任务");
		this.toolBar.add(this.pause).setToolTipText("暂停");
		this.toolBar.add(this.delete).setToolTipText("删除");
		this.toolBar.add(this.deleteFinished).setToolTipText("移除已经完成的任务");
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));
		this.add(this.toolBar, BorderLayout.NORTH);
	}
	
	private void start() {
		Resource r = getResource();
		if (r == null) return;
		if (r.getState() instanceof Pause || r.getState() instanceof Failed) {
			ContextHolder.dh.resumeDownload(r);
		}
	}
	
	/**
	 * 开始全部任务
	 */
	public void startAllTask() {
		for (Resource r : ContextHolder.ctx.resources) {
			if (r.getState() instanceof Pause || r.getState() instanceof Failed) {
				ContextHolder.dh.resumeDownload(r);
			}
		}
	}
	
	/**
	 * 暂停全部任务
	 */
	public void pauseAllTask() {
		for (Resource r : ContextHolder.ctx.resources) {
			if (r.getState() instanceof Downloading) {
				r.setState(ContextHolder.ctx.PAUSE);
			}
		}
	}
	
	private void newTask() {
		this.taskFrame.setVisible(true);
	}
	
	private void pause() {
		Resource r = getResource();
		if (r == null) return;
		//判断状态
		if (!(r.getState() instanceof Downloading)) return;
		r.setState(ContextHolder.ctx.PAUSE);
	}
	
	/**
	 * 删除资源
	 */
	private void delete() {
		Resource r = getResource();
		if (r == null) return;
		//先将任务停止
		r.setState(ContextHolder.ctx.PAUSE);
		//删除所有的.part文件
		FileUtil.deletePartFiles(r);
		//从上下文集合中删除资源
		ContextHolder.ctx.resources.remove(r);
	}
	
	/**
	 * 删除已下载完成的资源
	 */
	public void deleteFinished() {
		for (Iterator it = ContextHolder.ctx.resources.iterator(); it.hasNext();) {
			Resource r = (Resource)it.next();
			if (r.getState() instanceof Finished) {
				it.remove();
			}
		}
	}
	
	/**
	 * 得到用户在列表中所选择的资源
	 * @return
	 */
	private Resource getResource() {
		int row = this.downloadTable.getSelectedRow();
		int column = this.downloadTable.getColumn(DownloadTableModel.ID_COLUMN).getModelIndex();
		if (row == -1) return null;
		String id = (String)this.downloadTable.getValueAt(row, column);
		return ContextHolder.ctx.getResource(id);
	}

	
	/**
	 * 创建树
	 */
	private void createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode(taskNode);
		root.add(tn);
		//创建各个节点
		tn.add(new DefaultMutableTreeNode(downloadingNode));
		tn.add(new DefaultMutableTreeNode(failNode));
		tn.add(new DefaultMutableTreeNode(finishNode));
		this.navTree = new NavigationTree(root);
	}
		
	private void createList() {
		this.infoJList = new JList();
		this.infoList.add(this.fileSize);
		this.infoList.add(this.filePath);
		this.infoList.add(this.downloadDate);
		this.infoList.add(this.info);
		this.infoJList.setListData(infoList.toArray());
	}
	
	
}
