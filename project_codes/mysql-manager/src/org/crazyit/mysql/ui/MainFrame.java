package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.crazyit.mysql.object.GlobalContext;
import org.crazyit.mysql.object.ViewObject;
import org.crazyit.mysql.object.list.AbstractData;
import org.crazyit.mysql.object.list.ProcedureData;
import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.object.list.ViewData;
import org.crazyit.mysql.object.tree.ConnectionNode;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.object.tree.ProcedureNode;
import org.crazyit.mysql.object.tree.RootNode;
import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.object.tree.TableNode;
import org.crazyit.mysql.object.tree.ViewNode;
import org.crazyit.mysql.ui.list.ListCellRenderer;
import org.crazyit.mysql.ui.tree.TreeCellRenderer;
import org.crazyit.mysql.ui.tree.TreeListener;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 管理器的主界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	//主界面的分隔Pane
	private JSplitPane mainPane;
	//树的JScrollPane
	private JScrollPane treePane;
	//主界面的连接树, 树的第一层子节点是各个连接，第二层子节点是该连接下的数据库列表，
	//第三层子节点是表、视图等
	private JTree tree;
	//树的Model
	private DefaultTreeModel treeModel;
	//JList的JScrollPane
	private JScrollPane dataPane;
	//表、视图等存放的JList
	private JList dataList;
	//空的数据
	private Object[] emptyData = new Object[]{};
	//工具栏
	private JToolBar toolBar = new JToolBar();
	//新连接界面
	private ConnectionFrame connectionFrame;
	//新建数据库界面
	private DatabaseFrame databaseFrame;
	
	//新连接
	private Action newConnection = new AbstractAction("新连接", new ImageIcon("images/connection.gif")) {
		public void actionPerformed(ActionEvent e) {
			newConnection();
		}
	};

	//表
	private Action table = new AbstractAction("表", new ImageIcon("images/table.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewTables();
		}
	};
	
	//视图
	private Action view = new AbstractAction("视图", new ImageIcon("images/view.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewViews();
		}
	};
	
	//存储过程
	private Action procedure = new AbstractAction("存储过程", new ImageIcon("images/procedure.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewProcedures();
		}
	};
	
	//查询
	private Action query = new AbstractAction("查询", new ImageIcon("images/query.gif")) {
		public void actionPerformed(ActionEvent e) {
			query();
		}
	};
	
	//弹出菜单
	JPopupMenu menu = new JPopupMenu();

	//表的菜单
	private JMenuItem addTableItem = new JMenuItem("新建表", new ImageIcon("images/add-table.gif"));
	private JMenuItem editTableItem = new JMenuItem("编辑表", new ImageIcon("images/edit-table.gif"));
	private JMenuItem deleteTableItem = new JMenuItem("删除表", new ImageIcon("images/delete-table.gif"));
	private JMenuItem dumpTableItem = new JMenuItem("导出表", null);
	private JMenuItem refresh = new JMenuItem("刷    新", new ImageIcon("images/refresh.gif"));
	//视图菜单 
	private JMenuItem addViewItem = new JMenuItem("新建视图", new ImageIcon("images/add-view.gif"));
	private JMenuItem editViewItem = new JMenuItem("编辑视图", new ImageIcon("images/edit-view.gif"));
	private JMenuItem dropViewItem = new JMenuItem("删除视图", new ImageIcon("images/delete-view.gif"));
	//存储过程菜单 
	private JMenuItem addProcedureItem = new JMenuItem("新建存储过程", new ImageIcon("images/add-procedure.gif"));
	private JMenuItem editProcedureItem = new JMenuItem("编辑存储过程", new ImageIcon("images/edit-procedure.gif"));
	private JMenuItem dropProcedureItem = new JMenuItem("删除存储过程", new ImageIcon("images/delete-procedure.gif"));

	//树的弹出菜单
	JPopupMenu treeMenu = new JPopupMenu();
	private JMenuItem closeConnection = new JMenuItem("关闭连接", null);
	private JMenuItem removeConnection = new JMenuItem("删除连接", null);
	private JMenuItem closeDatabase = new JMenuItem("关闭数据库", null);
	private JMenuItem newDatabase = new JMenuItem("新建数据库", null);
	private JMenuItem removeDatabase = new JMenuItem("删除数据库", null);
	private JMenuItem executeSQLFile = new JMenuItem("执行SQL文件", null);
	private JMenuItem dumpSQLFile = new JMenuItem("导出SQL文件", null);
	//全局上下文对象
	private GlobalContext ctx;
	//当前列表前所显示数据种类
	private ViewObject currentView;
	
	public MainFrame(GlobalContext ctx) {
		this.ctx = ctx;
		//初始化树对象
		createTree();
		this.treePane = new JScrollPane(this.tree);
		//初始化表、视图的JList
		this.dataList = createList();
		this.dataPane = new JScrollPane(dataList);
		this.dataPane.setPreferredSize(new Dimension(600, 400));
		this.mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.treePane, 
				this.dataPane);
		this.mainPane.setDividerLocation(170);
		this.add(this.mainPane);
		//创建工具栏
		createToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setLocation(150, 100);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("MySQL管理器");
		//初始化各个界面
		this.connectionFrame = new ConnectionFrame(this.ctx, this);
		initMenuItemListeners();
	}
	
	//初始化右键菜单
	private void initMenuItemListeners() {
		this.addTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newTable();
			}
		});
		this.editTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editTable();
			}
		});
		this.deleteTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropTable();
			}
		});
		this.dumpTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dumpTable();
			}
		});
		this.addViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newView();
			}
		});
		this.editViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editView();
			}
		});
		this.dropViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropView();
			}
		});
		this.addProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newProcedure();
			}
		});
		this.editProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editProcedure();
			}
		});
		this.dropProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropProcedure();
			}
		});
		this.refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				refreshDataList();
			}
		});
		this.closeConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				closeConnection();
			}
		});
		this.closeDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				closeDatabase();
			}
		});
		this.dumpSQLFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dumpDatabase();
			}
		});
		this.executeSQLFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				executeSQLFile();
			}
		});
		this.removeConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeConnection();
			}
		});
		this.newDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newDatabase();
			}
		});
		this.removeDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeDatabase();
			}
		});
	}
	
	//初始化界dataList属性
	@SuppressWarnings("static-access")
	private JList createList() {
		JList dataList = new JList();
		//设置先纵向后横向滚动 
		dataList.setLayoutOrientation(JList.VERTICAL_WRAP);
		dataList.setFixedCellHeight(30);
		dataList.setVisibleRowCount(this.HEIGHT / 30);
		dataList.setCellRenderer(new ListCellRenderer());
		dataList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)  clickList();
			}
		});
		return dataList;
	}
	
	//双击列表方法
	private void clickList() {
		AbstractData selectData = getSelectData();
		if (selectData instanceof TableData) {
			//选择表，打开表数据
			openTable((TableData)selectData);
		} else if (selectData instanceof ViewData) {
			//选择视图，打开视图
			openView((ViewData)selectData);
		} else if (selectData instanceof ProcedureData) {
			//选择存储过程，编辑存储过程
			editProcedure();
		}
	}
	
	//打开视图
	private void openView(ViewData view) {
		DataFrame dataFrame =new DataFrame(view);
		dataFrame.setVisible(true);
	}
	
	//打开表
	private void openTable(TableData table) {
		DataFrame dataFrame =new DataFrame(table);
		dataFrame.setVisible(true);
	}
	
	//获得列表中选择的表、视图或者存储过程
	private AbstractData getSelectData() {
		return (AbstractData)this.dataList.getSelectedValue();
	}
	
	//显示新建连接界面
	private void newConnection() {
		this.connectionFrame.setVisible(true);
	}
	
	
	//根据选中的节点获取该节点所属的Database节点，如果选择了服务器节点，则返回null
	private DefaultMutableTreeNode getDatabaseNode(DefaultMutableTreeNode selectNode) {
		if (selectNode == null) return null;
		if (selectNode.getUserObject() instanceof Database) {
			return selectNode;
		}
		if (selectNode.getParent() == null) return null;
		return getDatabaseNode((DefaultMutableTreeNode)selectNode.getParent());
	}
	
	//在工具栏中点击了表
	private void viewTables() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//调用点击表节点的方法
		clickTableNode(db);
	}
	

	//导出一个数据库(菜单点击执行)
	private void dumpDatabase() {
		Database db = this.getSelectDatabase();
		DumpFolderChooser fc = new DumpFolderChooser(this, db);
		fc.showOpenDialog(this);
	}
	
	//导出表（菜单点击执行）
	private void dumpTable() {
		Database db = this.getSelectDatabase();
		DumpTableChooser fc = new DumpTableChooser(this, db);
		fc.showOpenDialog(this);
	}
	
	//由文件选择器执行导出表
	public void executeDumpTable(File file, Database db) {
		List<TableData> tables = new ArrayList<TableData>();
		Object[] selects = this.dataList.getSelectedValues();
		for (Object obj : selects) {
			TableData table = (TableData)obj;
			tables.add(table);
		}
		this.ctx.getBackupHandler().dumpTable(this.ctx, tables, db, file);
	}
	
	//让文件选择器执行的方法, 执行导出
	public void executeDumpDatabase(File file, Database db) {
		this.ctx.getBackupHandler().dumpDatabase(this.ctx, db, file);
	}
	
	//执行一份sql文件(菜单点击执行)
	private void executeSQLFile() {
		SQLFileChooser fc = new SQLFileChooser(this);
		fc.showOpenDialog(this);
	}
	
	//文件选择器执行
	public void executeSQLFile(File file) {
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode.getUserObject() instanceof ServerConnection) {
			ServerConnection conn = (ServerConnection)selectNode.getUserObject();
			//让整个服务器连接执行一份SQL
			this.ctx.getBackupHandler().executeSQLFile(ctx, conn, file);
		} else if (selectNode.getUserObject() instanceof Database) {
			//让一个数据库执行一份SQL
			Database db = (Database)selectNode.getUserObject();
			this.ctx.getBackupHandler().executeSQLFile(this.ctx, db, file);
		}
	}
	
	//在工具栏中点击了存储过程
	private void viewProcedures() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//调用点击表节点的方法
		clickProcedureNode(db);
	}
	
	//在工具栏中点击了视图
	private void viewViews() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//调用点击表节点的方法
		clickViewNode(db);
	}
	
	//在工具栏中点击了查询
	private void query() {
		Database db = getSelectDatabase();
		if (db == null) return;
		QueryFrame queryFrame = new QueryFrame(db);
		queryFrame.setVisible(true);
	}
	
	//显示新建表界面
	private void newTable() {
		Database db = getSelectDatabase();
		TableData table = new TableData(db);
		TableFrame tableFrame = new TableFrame(table, this);
		tableFrame.setVisible(true);
	}
	
	//修改表
	private void editTable() {
		TableData table = (TableData)this.getSelectData();
		TableFrame tableFrame = new TableFrame(table, this);
		tableFrame.setVisible(true);
	}
	
	//返回当前所选中的Database节点
	private DefaultMutableTreeNode getDatabaseNode() {
		//得到选中的节点
		DefaultMutableTreeNode selectNode = getSelectNode();
		//得到Database节点
		return getDatabaseNode(selectNode);
	}
	
	//新增视图
	private void newView() {
		Database db = this.getSelectDatabase();
		if (db == null) return;
		//构造一个新的ViewData
		ViewData viewData = new ViewData(db, null);
		ViewFrame viewFrame = new ViewFrame(viewData, this);
		viewFrame.setVisible(true);
	}
	
	//修改视图
	private void editView() {
		ViewData selectData = (ViewData)getSelectData();
		if (selectData == null) return;
		ViewFrame viewFrame = new ViewFrame(selectData, this);
		viewFrame.setVisible(true);
	}
	
	//删除视图
	private void dropView() {
		//得到全部选中的视图
		Object[] views = this.dataList.getSelectedValues();
		try {
			//循环删除
			for (Object view : views) {
				ViewData vd = (ViewData)view;
				vd.dropView();
			}
			//刷新列表
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//删除表
	private void dropTable() {
		Object[] tables = this.dataList.getSelectedValues();
		try {
			for (Object table : tables) {
				TableData td = (TableData)table;
				td.dropTable();
			}
			//刷新列表
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//刷新列表
	public void refreshDataList() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//使用当前显示的数据类型判断应该如何刷新
		if (this.currentView instanceof TableData) {
			this.dataList.setListData(db.getTables().toArray());//刷新表
		} else if (this.currentView instanceof ViewData) {
			this.dataList.setListData(db.getViews().toArray());//刷新视图
		} else if (this.currentView instanceof ProcedureData) {
			this.dataList.setListData(db.getProcedures().toArray());//刷新存储过程
		}
	}
				
	//新增存储过程
	private void newProcedure() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//构造一个新的ProcedureData对象, 表示是新增操作
		ProcedureData procedureData = new ProcedureData(db, MySQLUtil.PROCEDURE_TYPE, null);
		ProcedureFrame procedureFrame = new ProcedureFrame(procedureData, this);
		procedureFrame.setVisible(true);
	}
		
	//修改存储过程
	private void editProcedure() {
		ProcedureData procedureData = (ProcedureData)getSelectData();
		if (procedureData == null) return;
		ProcedureFrame procedureFrame = new ProcedureFrame(procedureData, this);
		procedureFrame.setVisible(true);
	}
	
	//删除存储过程
	private void dropProcedure() {
		//得到全部选中的存储过程
		Object[] procedures = this.dataList.getSelectedValues();
		try {
			//循环删除
			for (Object procedure : procedures) {
				ProcedureData data = (ProcedureData)procedure;
				data.drop();
			}
			//刷新列表
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//获得选中的Database节点
	private Database getSelectDatabase() {
		DefaultMutableTreeNode databaseNode = getDatabaseNode();
		if (databaseNode == null) return null;
		return (Database)databaseNode.getUserObject();
	}

	//为JList添加右键菜单
	private void addMouseMenu() {
		this.dataList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					menu.show(dataList, e.getX(), e.getY());
				}
			}
		});
	}
	
	//
	private void createToolBar() {
		this.toolBar.add(this.newConnection).setToolTipText("新连接");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.table).setToolTipText("表");
		this.toolBar.add(this.view).setToolTipText("视图");
		this.toolBar.add(this.procedure).setToolTipText("存储过程");
		this.toolBar.add(this.query).setToolTipText("查询");
	}
	
	//创建树
	private void createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new RootNode());
		//创建连接节点
		createNodes(root);
		this.treeModel = new DefaultTreeModel(root);
		//构造树
		JTree tree = new JTree(this.treeModel);
		TreeCellRenderer cr = new TreeCellRenderer();
		tree.setCellRenderer(cr);
		tree.addMouseListener(new TreeListener(this));
		tree.setRootVisible(false);
		//添加右键菜单
		tree.add(this.treeMenu);
		this.tree = tree;
	}
	
	//显示树的右键菜单
	public void showTreeMenu(MouseEvent e) {
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode == null) return;
		if (selectNode.getUserObject() instanceof ServerConnection) {
			createServerConnectionMenu();
			this.treeMenu.show(this.tree, e.getX(), e.getY());
		} else if (selectNode.getUserObject() instanceof Database) {
			createDatabaseMenu();
			this.treeMenu.show(this.tree, e.getX(), e.getY());
		}
	}
	
	//如果当前列表中显示的是表, 则创建右键菜单
	private void createTableMenu() {
		this.menu.removeAll();
		this.menu.add(this.addTableItem);
		this.menu.add(this.editTableItem);
		this.menu.add(this.deleteTableItem);
		this.menu.add(this.dumpTableItem);
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//如果当前列表中显示的是视图, 则创建右键菜单
	private void createViewMenu() {
		this.menu.removeAll();
		this.menu.add(this.addViewItem);
		this.menu.add(this.editViewItem);
		this.menu.add(this.dropViewItem);	
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//如果当前列表中显示的是存储过程, 则创建右键菜单
	private void createProcedureMenu() {
		this.menu.removeAll();
		this.menu.add(this.addProcedureItem);
		this.menu.add(this.editProcedureItem);
		this.menu.add(this.dropProcedureItem);	
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//显示服务器连接的菜单 
	private void createServerConnectionMenu() {
		this.treeMenu.removeAll();
		this.treeMenu.add(this.closeConnection);
		this.treeMenu.add(this.removeConnection);
		this.treeMenu.add(this.newDatabase);
		this.treeMenu.add(this.executeSQLFile);
	}
	
	//显示数据库连接的菜单 
	private void createDatabaseMenu() {
		this.treeMenu.removeAll();
		this.treeMenu.add(this.closeDatabase);
		this.treeMenu.add(this.removeDatabase);
		this.treeMenu.add(this.executeSQLFile);
		this.treeMenu.add(this.dumpSQLFile);
	}
	
	//新建数据库菜单执行的方法
	private void newDatabase() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection conn = (ServerConnection)selectNode.getUserObject();
		if (this.databaseFrame == null) {
			this.databaseFrame = new DatabaseFrame(this, conn);
		}
		this.databaseFrame.setVisible(true);
	}
	
	//删除一个数据库
	private void removeDatabase() {
		//得到选择中的节点
		DefaultMutableTreeNode selectNode = getSelectNode();
		Database db = (Database)selectNode.getUserObject();
		db.remove();
		this.treeModel.removeNodeFromParent(selectNode);
	}
	
	//DatabaseFrame执行的方法，添加一个数据库
	public void addDatabase(Database db) {
		DefaultMutableTreeNode parent = getSelectNode();
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(db);
		this.treeModel.insertNodeInto(newChild, parent, parent.getChildCount());
	}
	
	//关闭服务器连接
	private void closeConnection() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection sc = (ServerConnection)selectNode.getUserObject();
		//将ServerConnection的连接对象设为null
		sc.setConnection(null);
		//删除所有的子节点
		removeNodeChildren(selectNode);
		//设置树不选中
		this.tree.setSelectionPath(null);
	}
	
	//关闭数据库连接
	private void closeDatabase() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		Database db = (Database)selectNode.getUserObject();
		db.setConnection(null);
		//删除所有的子节点
		removeNodeChildren(selectNode);
		//设置树不选中
		this.tree.setSelectionPath(null);
	}
	
	//删除一个节点的所有子节点
	private void removeNodeChildren(DefaultMutableTreeNode node) {
		//获取节点数量
		int childCount = this.treeModel.getChildCount(node);
		for (int i = 0; i < childCount; i++) {
			//从最后一个开始删除
			this.treeModel.removeNodeFromParent((DefaultMutableTreeNode)node.getLastChild());
		}
	}
	
	//删除一个连接
	private void removeConnection() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection conn = (ServerConnection)selectNode.getUserObject();
		//从上下文件中删除
		this.ctx.removeConnection(conn);
		//从树节点中删除
		this.treeModel.removeNodeFromParent(selectNode);
	}
	
	//创建树中服务器连接的节点
	private void createNodes(DefaultMutableTreeNode root) {
		Map<String, ServerConnection> conns = this.ctx.getConnections();
		for (String key : conns.keySet()) {
			ServerConnection conn = conns.get(key);
			//创建连接节点
			DefaultMutableTreeNode conntionNode = new DefaultMutableTreeNode(conn);
			root.add(conntionNode);
		}
	}
	
	//获得树中选中的节点
	private DefaultMutableTreeNode getSelectNode() {
		TreePath treePath = this.tree.getSelectionPath();
		if (treePath == null) return null;
		//获得选中的节点
		return (DefaultMutableTreeNode)treePath.getLastPathComponent();
	}

	
	//点击树节点的操作
	public void viewTreeDatas() {
		//获得选中的节点
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode == null) return;
		//清空数据
		this.dataList.setListData(this.emptyData);
		//判断点击节点的类型
		if (selectNode.getUserObject() instanceof ServerConnection) {
			clickServerNode(selectNode);//服务器连接节点
		} else if (selectNode.getUserObject() instanceof Database) {
			clickDatabaseNode(selectNode);//数据库连接节点
		} else if (selectNode.getUserObject() instanceof TableNode) {
			Database db = getDatabase(selectNode);
			clickTableNode(db);//表节点
		} else if (selectNode.getUserObject() instanceof ViewNode) {
			Database db = getDatabase(selectNode);
			clickViewNode(db);//视图节点
		} else if (selectNode.getUserObject() instanceof ProcedureNode) {
			Database db = getDatabase(selectNode);
			clickProcedureNode(db);//存储过程和函数节点
		}
	}
	
	//在添加连接界面添加了一个连接后执行的方法, 向树中添加一个连接
	public void addConnection(ServerConnection sc) {
		//得到要节点
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)this.treeModel.getRoot();
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(sc);
		//向要节点添加连接节点
		this.treeModel.insertNodeInto(newChild, root, root.getChildCount());
		if (root.getChildCount() == 1) this.tree.updateUI();		
	}
	
	//根据一个节点(该节点为Database节点的子节点)获得Database节点
	private Database getDatabase(DefaultMutableTreeNode selectNode) {
		//获取父节点
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectNode.getParent();
		return (Database)parentNode.getUserObject();
	}
	
	//点击存储过程节点
	private void clickProcedureNode(Database db) {
		List<ProcedureData> datas = db.getProcedures();
		this.dataList.setListData(datas.toArray());
		//显示存储过程后，创建右键菜单
		createProcedureMenu();
		//设置当前显示的数据类型为存储过程
		this.currentView = new ProcedureData(db, null, null);
	}
	
	//点击视图节点，查找全部的视图
	private void clickViewNode(Database db) {
		List<ViewData> datas = db.getViews();
		this.dataList.setListData(datas.toArray());
		//显示视图后，创建右键菜单
		createViewMenu();
		//设置当前显示的数据类型为视图
		this.currentView = new ViewData(db, null);
	}
	
	//点击表节点，查找全部的表
	private void clickTableNode(Database db) {
		List<TableData> datas = db.getTables();
		this.dataList.setListData(datas.toArray());
		//显示表后，创建右键菜单
		createTableMenu();
		//设置当前显示的数据类型为表
		this.currentView = new TableData(db);
	}
	
	/*
	 * 判断连接是否出错，适用于服务器节点和数据库节点
	 */
	private void validateConnect(DefaultMutableTreeNode selectNode, ConnectionNode node) {
		try {
			//进行连接
			node.connect();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//点击数据库节点
	public void clickDatabaseNode(DefaultMutableTreeNode selectNode) {
		//获取点击树节点的对象
		Database database = (Database)selectNode.getUserObject();
		validateConnect(selectNode, database);
		//创建节点
		buildDatabaseChild(database, selectNode);
	}
	
	//创建数据库节点子节点
	private void buildDatabaseChild(Database database, 
			DefaultMutableTreeNode databaseNode) {
		//判断如果已经连接，则不创建节点
		if (databaseNode.getChildCount() != 0) return;
		//创建三个子节点（表、视图、存储过程）
		DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(new TableNode(database));
		DefaultMutableTreeNode viewNode = new DefaultMutableTreeNode(new ViewNode(database));
		ProcedureNode pNode = new ProcedureNode(database);
		DefaultMutableTreeNode procedureNode = new DefaultMutableTreeNode(pNode);
		//插入树中
		this.treeModel.insertNodeInto(tableNode, databaseNode, databaseNode.getChildCount());
		this.treeModel.insertNodeInto(viewNode, databaseNode, databaseNode.getChildCount());
		this.treeModel.insertNodeInto(procedureNode, databaseNode, databaseNode.getChildCount());
	}
	
	//点击服务器节点
	public void clickServerNode(DefaultMutableTreeNode selectNode) {
		ServerConnection server = (ServerConnection)selectNode.getUserObject();
		validateConnect(selectNode, server);
		//创建服务器子节点
		buildServerChild(server, selectNode);
	}
	
	
	//创建数据库一层的节点（树的第二层）
	public void buildServerChild(ServerConnection server, 
			DefaultMutableTreeNode conntionNode) {
		//如果有子节点，则不再创建
		if (conntionNode.getChildCount() != 0) return;
		List<Database> databases = server.getDatabases();
		//再创建连接节点下面的数据节点
		for (Database database : databases) {
			DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database);
			//将数据库节点加入到连接节点中
			this.treeModel.insertNodeInto(databaseNode, conntionNode, conntionNode.getChildCount());
		}
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
}
//导入SQL文件的文件选择类
class SQLFileChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	public SQLFileChooser(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	
	public void approveSelection() {
		File file = this.getSelectedFile();
		this.mainFrame.executeSQLFile(file);
		super.approveSelection();
	}

}
//导出数据库SQL文件的目录选择类
class DumpFolderChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	private Database db;
	
	public DumpFolderChooser(MainFrame mainFrame, Database db) {
		this.mainFrame = mainFrame;
		this.db = db;
		this.setFileSelectionMode(DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//用户选择了目录
			File targetFile = new File(file.getAbsolutePath() + File.separator + 
					this.db.getDatabaseName() + ".sql");
			this.mainFrame.executeDumpDatabase(targetFile, this.db);
		} else {
			this.mainFrame.executeDumpDatabase(file, this.db);
		}
		super.approveSelection();
	}
}
/**
 * 导出表时的选择器
 * @author yangenxiong
 *
 */
class DumpTableChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	private Database db;
	
	public DumpTableChooser(MainFrame mainFrame, Database db) {
		this.mainFrame = mainFrame;
		this.db = db;
		this.setFileSelectionMode(DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//用户选择了目录
			File targetFile = new File(file.getAbsolutePath() + File.separator + 
					this.db.getDatabaseName() + ".sql");
			this.mainFrame.executeDumpTable(targetFile, this.db);
		} else {
			this.mainFrame.executeDumpTable(file, this.db);
		}
		super.approveSelection();
	}
}