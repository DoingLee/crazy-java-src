package org.crazyit.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import org.crazyit.editor.commons.AddInfo;
import org.crazyit.editor.commons.EditFile;
import org.crazyit.editor.commons.WorkSpace;
import org.crazyit.editor.handler.add.AddFileHandler;
import org.crazyit.editor.handler.add.AddFolderHandler;
import org.crazyit.editor.handler.add.AddProjectHandler;
import org.crazyit.editor.handler.run.JavaRunHandler;
import org.crazyit.editor.handler.save.SaveMediator;
import org.crazyit.editor.handler.save.SaveMediatorConcrete;
import org.crazyit.editor.tree.ProjectTreeModel;
import org.crazyit.editor.tree.ProjectTreeNode;
import org.crazyit.editor.tree.TreeCreator;

/**
 * 编辑界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class EditorFrame extends JFrame {
	
	//多文件的tab标题
	private JTabbedPane tabPane;
	
	//存放tabPane与desk
	private Box box;
	
	//创建一个多文档的桌面容器
	private JDesktopPane desk;
	
	//用于分隔主编辑区和信息显示区的容器
	private JSplitPane editorSplitPane;
	
	//可以滚动的JScrollPane对象，用于放infoArea
	private JScrollPane infoPane;
	
	//用于显示信息的文本域
	private JTextArea infoArea;
	
	//存放树的可滚动容器
	private JScrollPane treePane;
	
	//整个界面的分隔组件的容器
	private JSplitPane mainSplitPane;
	
	//项目树对象
	private JTree tree;
	
	//菜单栏对象
	private JMenuBar menuBar;
	
	//编辑菜单对象
	private JMenu editMenu;
	
	//文件菜单
	private JMenu fileMenu;
	
	//工具条
	private JToolBar toolBar;
	
	private WorkSpace workSpace;
	
	private TreeCreator treeCreator;
	
	//添加的界面
	private AddFrame addFrame;
	
	//文件选择器
	private FileChooser fileChooser;
	
	//当前正在编辑的文件对象
	private EditFile currentFile;
	
	//窗口监听器
	private IFrameListener iframeListener;
	
	//打开文件的集合
	private List<EditFile> openFiles = new ArrayList<EditFile>();

	//中介者对象
	private SaveMediator saveMediator;
	
	//运行class文件的处理类
	private JavaRunHandler runHandler;
	
	//新建文件的Action对象
	private Action fileNew = new AbstractAction("新建文件", new ImageIcon("images/newFile.gif")) {
		public void actionPerformed(ActionEvent e) {
			newFile();
		}
	};
	//新建目录的Action对象
	private Action folerNew = new AbstractAction("新建目录", new ImageIcon("images/newFile.gif")) {
		public void actionPerformed(ActionEvent e) {
			newFolder();
		}
	};
	//新建项目的Action对象
	private Action projectNew = new AbstractAction("新建项目", new ImageIcon("images/newFile.gif")) {
		public void actionPerformed(ActionEvent e) {
			newProject();
		}
	};
	//打开文件的Action对象
	private Action open = new AbstractAction("打     开", new ImageIcon("images/open.gif")) {
		public void actionPerformed(ActionEvent e) {
			selectFile();
		}
	};
	//保存文件的Action对象
	private Action save = new AbstractAction("保     存", new ImageIcon("images/save.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveFile(getCurrentFile());
		}
	};
	//刷新树的Action对象
	private Action refresh = new AbstractAction("刷     新", new ImageIcon("images/refresh.gif")) {
		public void actionPerformed(ActionEvent e) {
			reloadNode(getSelectNode());
		}
	};
	//运行文件的Action对象
	private Action run = new AbstractAction("运     行", new ImageIcon("images/run.gif")) {
		public void actionPerformed(ActionEvent e) {
			run();
		}
	};
	//退出的Action对象
	private Action exit = new AbstractAction("退     出") {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);//直接退出
		}
	};
	//复制文本的Action对象
	private Action copy = new AbstractAction("复     制", new ImageIcon("images/copy.gif")) {
		public void actionPerformed(ActionEvent e) {
			if (getCurrentFile() != null) {
				getCurrentFile().getEditPane().copy();
			}
		}
	};
	//剪切文本的Action对象
	private Action cut = new AbstractAction("剪     切", new ImageIcon("images/cut.gif")) {
		public void actionPerformed(ActionEvent e) {
			if (getCurrentFile() != null) {
				getCurrentFile().getEditPane().cut();
			}
		}
	};
	//粘贴文本的Action对象
	private Action paste = new AbstractAction("粘     贴", new ImageIcon("images/paste.gif")) {
		public void actionPerformed(ActionEvent e) {
			if (getCurrentFile() != null) {
				getCurrentFile().getEditPane().paste();
			}
		}
	};
	
	public EditorFrame(String title, TreeCreator treeCreator) {
		super(title); //设置标题
		this.treeCreator = treeCreator;
		this.iframeListener = new IFrameListener(this);
		this.saveMediator = new SaveMediatorConcrete();
		this.runHandler = new JavaRunHandler();
	}
	
	public void initFrame(WorkSpace space) {
		this.workSpace = space;
		//设置窗口关闭，退出程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//创建主编辑区的tabPane
		tabPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
		desk = new JDesktopPane();//创建JDesktopPane对象
		desk.setBackground(Color.GRAY);//设置desk的背景颜色为灰色
		box = new Box(BoxLayout.Y_AXIS);//设置box的布局
		box.add(tabPane);
		box.add(desk);
		//创建信息显示区的文本域
		infoArea = new JTextArea("", 5, 50);
		//将infoArea文本域作为组件放到infoPane中
		infoPane = new JScrollPane(infoArea);
		//设置信息区不可编辑
		infoArea.setEditable(false);
		//创建这个分隔组件的容器，并将box对象和infoPane放置其中
		editorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, box, infoPane);
		editorSplitPane.setDividerSize(3);
		editorSplitPane.setDividerLocation(500);
		//创建树
		tree = treeCreator.createTree(this);
		//创建可滚动的容器对象
		treePane = new JScrollPane(tree);
		//创建主界面的JSplitPane，横向，左边为treePane，右边为editorSplitPane
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, 
				editorSplitPane);
		//设置分隔条的位置
		mainSplitPane.setDividerLocation(200);
		//设置分隔条的粗细
		mainSplitPane.setDividerSize(3);
		add(mainSplitPane);
		
		//创建菜单栏对象
		menuBar = new JMenuBar();
		//创建编辑菜单对象
		editMenu = new JMenu("编辑");
		//创建文件菜单
		fileMenu = new JMenu("文件");
		//将文件菜单添加到菜单栏中
		menuBar.add(fileMenu);
		//将编辑菜单添加到菜单栏中
		menuBar.add(editMenu);
		//设置JFrame的菜单栏
		setJMenuBar(menuBar);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);//设置工具栏不可移动
		toolBar.setMargin(new Insets(0, 10, 5, 5));//设置工具栏的边距
		add(toolBar, BorderLayout.NORTH);//将工具栏添加到EditorFrame中
		
		pack();//使JFrame调整最佳大小
		addListeners();
	}
	
	public TreeCreator getTreeCreator() {
		return treeCreator;
	}
	
	//为EditorFrame中的组件添加监听器
	public void addListeners() {
		//新建文件的监听器
		fileMenu.add(fileNew).setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		//新建目录的监听器
		fileMenu.add(folerNew).setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		//新建项目的监听器
		fileMenu.add(projectNew).setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK));
		//打开菜单添加监听器
		fileMenu.add(open).setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		//为保存菜单添加监听器
		fileMenu.add(save).setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		//为刷新菜单添加监听器
		fileMenu.add(refresh).setAccelerator(KeyStroke.getKeyStroke("F5"));
		//为运行菜单添加监听器
		fileMenu.add(run).setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
		fileMenu.add(exit);
		//添加复制监听器
		editMenu.add(copy).setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		//添加剪切监听器
		editMenu.add(cut).setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		//添加粘贴监听器
		editMenu.add(paste).setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
	
		//为工具条添加各个操作
		toolBar.add(fileNew).setToolTipText("新建文件");
		toolBar.add(open).setToolTipText("打开");
		toolBar.add(save).setToolTipText("保存");
		toolBar.add(refresh).setToolTipText("刷新");
		toolBar.add(run).setToolTipText("运行");
		toolBar.add(copy).setToolTipText("复制");
		toolBar.add(cut).setToolTipText("剪切");
		toolBar.add(paste).setToolTipText("粘贴");
		
		//为tabPane添加监听器
		tabPane.addChangeListener(new TabListener(this));
	}
	
	//获取编辑器主界面项目树中所选中的节点
	public ProjectTreeNode getSelectNode() {
		//获得当前树选择的节点在树中的路径
		TreePath path = tree.getSelectionPath();
		//如果当前选择了节点
		if (path != null) {
			//创建一个ProjectTreeNode对象并用于返回
			ProjectTreeNode selectNode = (ProjectTreeNode)path.getLastPathComponent();
			return selectNode;
		}
		//当前没有选择节就返回null
		return null;
	}
	
	//显示iframe对象
	public void showIFrame(JInternalFrame iframe) {
		try {
			iframe.setSelected(true);
			iframe.toFront();
		} catch (Exception ex) {
		}
	}
	
	//打开文件的方法
	public void openFile(File file) {
		if (currentFile != null) {
			//如果参数file是当前正在编辑的文件
			if (file.equals(currentFile.getFile())) return;
		}
		//在打开文件的集合中查找该文件, 再判断该文件是否已经打开
		EditFile openedFile = getOpenFile(file);
		//如果文件已经打开了，调用打开方法（打开已经在编辑tab页面中显示的文件）
		if (openedFile != null) {
			openExistFile(openedFile, file);
			return;
		}
		//打开新的文件
		openNewFile(file);
	}
	
	//从本类中记录已经打开的文件集合中得到与参数file一样的EditFile对象
	private EditFile getOpenFile(File file) {
		for (EditFile openFile : openFiles) {
			if (openFile.getFile().equals(file)) return openFile;
		}
		return null;
	}
	
	//根据参数file获取该file对应的tab页中的索引
	private int getFileIndex(File file) {
		//如果从打开文件的集合中找不到与参数file对应的EditFile对象，返回-1
		EditFile openFile = getEditFile(file);
		if (openFile == null) return -1;
		return getTabIndex(openFile.getIframe().getToolTipText());
	}
	
	//在打开的文件中获得文件是file的EditFile对象
	private EditFile getEditFile(File file) {
		for (EditFile openFile : openFiles) {
			if (openFile.getFile().equals(file)) return openFile;
		}
		return null;
	}
	
	//根据tab页中的tips找到所对应的在tabPane的索引
	public int getTabIndex(String tips) {
		for (int i = 0; i < this.tabPane.getTabCount(); i++) {
			if (this.tabPane.getToolTipTextAt(i).equals(tips)) return i;
		}
		return -1;
	}
	
	//打开已经存在的文件（已经在编辑文件集合中的文件），openFiles集合中的文件
	public void openExistFile(EditFile openedFile, File willOpenFile) {
		//将tab页变成当前所选择文件的索引
		tabPane.setSelectedIndex(getFileIndex(willOpenFile));
		//显示iframe
		showIFrame(openedFile.getIframe());
		//设置当前打开文件
		this.currentFile = openedFile;
		//添加到当前打开的文件集合中
		this.openFiles.add(openedFile);
	}
	
	//打开一个新文件（该文件并不在编辑列表中）
	public void openNewFile(File file) {
		//设置EditorFrame的标题为该文件的全路径
		setTitle(file.getAbsolutePath());
		//创建一个JInternalFrame对象，title为文件的绝对路径
		JInternalFrame iframe = new JInternalFrame(file.getAbsolutePath(), true, true, true, true);
		
		//新建一个EditPane对象
		EditPane editPane = new EditPane(file);
		
		//为EditPane添加键盘监听器
		editPane.getDocument().addDocumentListener(new EditDocumentListener(this));
		iframe.add(new JScrollPane(editPane));
		//为JInternalFrame添加窗口监听器
		iframe.addInternalFrameListener(this.iframeListener);
		desk.add(iframe);
		iframe.show();
		iframe.reshape(0, 0, 400, 300);
		tabPane.addTab(file.getName(), null, null, file.getAbsolutePath());
		tabPane.setSelectedIndex(tabPane.getTabCount() - 1);
		//设置当前的文件对象
		this.currentFile = new EditFile(file, true, iframe, editPane);
		//将当前的文件加到打开的文件集合中
		this.openFiles.add(this.currentFile);
	}
	
	public void selectFile() {
		fileChooser = new FileChooser(this);
	}
	
	//新建文件的方法
	public void newFile() {
		//当没有选择一个节点而新建目录的时候，需要提醒
		if (getSelectNode() == null) {
			JOptionPane.showMessageDialog(this, "请选择目录"); 
			return;
		}
		AddInfo info = new AddInfo("文件名称：", this, new AddFileHandler());
		showAddFrame(info);
	}
	//显示新增的界面
	private void showAddFrame(AddInfo info) {
		//使EditorFrame变为不可用
		setEnabled(false);
		addFrame = new AddFrame(info);
		addFrame.pack();
		addFrame.setVisible(true);
	}
	
	//刷新树节点
	public void reloadNode(ProjectTreeNode selectNode) {
		if (selectNode == null) return; 
		//刷新树的节点
		ProjectTreeModel model = (ProjectTreeModel)getTree().getModel();
		//重新加载所选择的节点
		model.reload(selectNode, treeCreator);
	}
	
	public WorkSpace getWorkSpace() {
		return workSpace;
	}
	
	public JTree getTree() {
		return this.tree;
	}
	
	//新建目录的方法
	public void newFolder() {
		//当没有选择一个节点而新建目录的时候，需要提醒
		if (getSelectNode() == null) {
			JOptionPane.showMessageDialog(this, "请选择目录"); 
			return;
		}
		AddInfo info = new AddInfo("目录名称：", this, new AddFolderHandler());
		showAddFrame(info);
	}
	
	//新建项目的方法
	public void newProject() {
		AddInfo info = new AddInfo("项目名称：", this, new AddProjectHandler());
		showAddFrame(info);
	}
	
	//重新在treePane中构造一次树
	public void refreshTree(JTree newTree) {
		//将tree对象变为参数中的newTree
		this.tree = newTree;
		//让treePane（放树的容器）设置newTree为新的视图
		treePane.setViewportView(newTree);
		//更新界面
		treePane.updateUI();
	}

	public EditFile getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(EditFile currentFile) {
		this.currentFile = currentFile;
	}

	public List<EditFile> getOpenFiles() {
		return openFiles;
	}

	public JDesktopPane getDesk() {
		return desk;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}
	
	//根据JInternalFrame标题找到JInternalFrame对象
	public JInternalFrame getIFrame(String title) {
		JInternalFrame[] iframes = desk.getAllFrames();
		for (JInternalFrame iframe : iframes) {
			if (iframe.getTitle().equals(title)) return iframe;
		}
		return null;
	}
	
	//根据JInternalFrame在打开的文件集合中获取相应的文件对象
	public EditFile getEditFile(JInternalFrame iframe) {
		for (EditFile openFile : openFiles) {
			if (openFile.getIframe().equals(iframe)) return openFile;
		}
		return null;
	}
	
	//询问是否要保存, 参数为将被打开的文件
	public void askSave(EditFile file) {
		//该份文件修改过没有保存
		if (!file.isSaved()) {
			//弹出询问
			int val = JOptionPane.showConfirmDialog(this, "是否要保存？", "询问", 
					JOptionPane.YES_NO_OPTION);
			//点击了需要保存
			if (JOptionPane.YES_OPTION == val) {
				//调用EditorFrame的保存方法对文件进行保存
				saveFile(file);
			}
		}
	}
	
	//用于保存当前所打开的文件
	public void saveFile(EditFile file) {
		if (file == null) return;
		//调用中介者对象的方法去保存文件
		String result = saveMediator.doSave(this);
		//将结果放到信息显示区的文本域中
		infoArea.setText(result);
		//写完文件后，设置当前文件的保存状态为true，表示已经保存
		file.setSaved(true);
	}
	
	//关闭一个窗口
	public void closeIFrame(JInternalFrame iframe) {
		//获得当前的文件，即要关闭的文件对象
		EditFile closeFile = getEditFile(iframe);
		//设置本类中的currentFile属性
		afterClose(closeFile);
		//获得该iframe在tab页中对应的索引
		int index = getTabIndex(iframe.getTitle());
		//从tab页中删除
		getTabPane().remove(index);
		//从打开的文件集合中删除这个关闭的文件
		openFiles.remove(closeFile);
	}
	
	//当关闭一份文件后，设置本对象的currentFile属性
	private void afterClose(EditFile closeFile) {
		//获取关闭文件在打开文件集合中的索引
		int openFilesIndex = getEditFileIndex(closeFile);
		//如果该文件已经是所有打开的文件的最后一份
		if (this.openFiles.size() == 1) {
			this.currentFile = null;
		} else {//如果还有其他文件，判断关闭的文件位置
			if (openFilesIndex == 0) {
				//如果关闭的文件是第一份，拿集合中的第二份
				this.currentFile = openFiles.get(openFilesIndex + 1);
			} else if (openFilesIndex == (openFiles.size() - 1)) {
				//如果关闭的是最后一份，取倒数第二份
				this.currentFile = openFiles.get(openFiles.size() - 2);
			} else {
				//不是第一份，也不是最后一份
				this.currentFile = openFiles.get(openFilesIndex - 1);
			}
		}
	}
	
	//获取editFile在打开的文件集合中的索引
	private int getEditFileIndex(EditFile editFile) {
		for (int i = 0; i < this.openFiles.size(); i++) {
			if (openFiles.get(i).equals(editFile)) return i;
		}
		return -1;
	}
	
	//返回项目树当前所选中的节点所属的项目节点对应的目录
	public File getCurrentProject() {
		//获取根节点(工作空间)
		ProjectTreeNode root = (ProjectTreeNode)getSelectNode().getRoot();
		//获取根节点下的所有子节点（即项目节点集合）
		List<ProjectTreeNode> projects = root.getChildren();
		ProjectTreeNode selectNode = getSelectNode();
		if (selectNode != null) {
			for (ProjectTreeNode project : projects) {
				//当前先中节点是该project下的子节点
				if (selectNode.isNodeAncestor(project)) {
					return project.getFile();
				}
			}
		}
		return null;
	}
	
	//运行文件的方法
	public void run() {
		//运行前先保存
		saveFile(getCurrentFile());
		//将结果显示
		String result = runHandler.run(this);
		infoArea.setText(result);
	}
	
}
class FileChooser extends JFileChooser {
	
	private EditorFrame editorFrame;
	
	
	public FileChooser(EditorFrame editorFrame){
		//调用父类的构造器
		//利用editorFrame的工作空间作为文件选择器打开时的默认目录
		super(editorFrame.getWorkSpace().getFolder());
		this.editorFrame = editorFrame;
		showOpenDialog(editorFrame);
	}
	
	public void approveSelection() {
		File file = getSelectedFile();
		//设置树当前选择的节点为null, 即树没有被选中
		this.editorFrame.getTree().setSelectionPath(null);
		this.editorFrame.openFile(file);
		super.approveSelection();
	}
}