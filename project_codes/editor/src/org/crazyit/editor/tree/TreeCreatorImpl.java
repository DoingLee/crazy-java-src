package org.crazyit.editor.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.util.ImageUtil;

/**
 * 树创建实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TreeCreatorImpl implements TreeCreator {

	
	@Override
	public JTree createTree(EditorFrame editorFrame) {
		File spaceFolder = editorFrame.getWorkSpace().getFolder();
		ProjectTreeNode root = new ProjectTreeNode(spaceFolder, true);
		ProjectTreeModel treeModel = new ProjectTreeModel(root);
		JTree tree = new JTree(treeModel);
		//获取工作空间下面所有的目录（即与有projectName.project相对应的目录），也就是项目目录
		List<File> projectFolders = getProjectFolders(spaceFolder);
		//遍历项目目录集合，并为其创建子节点
		for (int i = 0; i < projectFolders.size(); i++) {
			//获取循环中的目录
			File projectFolder = projectFolders.get(i);
			//调用createNode创建它所有的子节点
			ProjectTreeNode node = createNode(projectFolder);
			//向根节点添加子节点（项目目录）
			root.add(node);
		}

		//定制节点图片
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		//目录打开时的图片
		renderer.setOpenIcon(ImageUtil.getImageIcon(ImageUtil.FOLDER_OPEN));
		//节点没有子节点的图片
		renderer.setLeafIcon(ImageUtil.getImageIcon(ImageUtil.FILE));
		//目录关闭时的图片
		renderer.setClosedIcon(ImageUtil.getImageIcon(ImageUtil.FOLDER_CLOSE));
		//设置树的部件处理类为上面的renderer
		tree.setCellRenderer(renderer);
		//为项目树添加一个树选择监听器
		tree.addMouseListener(new ProjectTreeSelectionListener(editorFrame));
		//创建根在项目树中的路径
		TreePath path = new TreePath(root);
		//让树默认展开根节点
		tree.expandPath(path);
		//设置树的根节点不可见
		tree.setRootVisible(false);
		return tree;
	}
	
	/*
	 * 根据一个目录创建它的所有直接节点
	 */
	private List<ProjectTreeNode> createNodes(File folder) {
		//获取该目录下的所有文件
		File[] files = folder.listFiles();
		List<ProjectTreeNode> result = new ArrayList<ProjectTreeNode>();
		//对该目录下的所有文件的数组进行两次遍历
		for (File file : files) {
			//第一次遍历，如果是目录的话，就加入到结果集合中
			if (file.isDirectory()) {
				result.add(new ProjectTreeNode(file, true));
			}
		}
		for (File file : files) {
			//第二次遍历，如果非目录的话，就加入到结果集合中
			if (!file.isDirectory()) {//再加普通文件
				result.add(new ProjectTreeNode(file, false));
			}
		}
		return result;
	}
	
	//根据一个目录去创建该目录所对应的节点对象，该对象的所有的子节点都已经创建
	public ProjectTreeNode createNode(File folder) {
		//创建一个父节点，即本方法即将返回的节点对象
		ProjectTreeNode parent = null;
		//如果参数foler不是一个目录的话，创建一个ProjectTreeNode对象并返回，表明它不允许拥有子节点
		if (!folder.isDirectory()) {
			return new ProjectTreeNode(folder, false);
		} else {
			//如果是一个目录的话，则创建上面的parent，表明它是一个目录，可以拥有子节点
			parent = new ProjectTreeNode(folder, true);
		}
		//利用上面的parent节点去查找它下面所有的直接节点
		List<ProjectTreeNode> nodes = createNodes(parent.getFile());
		//获取到parent下面的所有直接子节点后，再去循环递归调用本方法
		for (ProjectTreeNode node : nodes) {
			//递归创建子节点，并将返回的节点添加到parent中
			parent.add(createNode(node.getFile()));
		}
		return parent;
	}
	
	
	/**
	 * 获取工作空间目录下所有的项目名称
	 * @return
	 */
	private List<String> getProjectNames(File spaceFolder) {
		List<String> result = new ArrayList<String>();
		for (File file : spaceFolder.listFiles()) {
			if (file.getName().endsWith(".project")) {//取以.project结尾的文件
				result.add(file.getName().substring(0, file.getName().indexOf(".project")));
			}
		}
		return result;
	}
	
	/*
	 * 获取工作空间目录下所有的项目目录
	 */
	private List<File> getProjectFolders(File spaceFolder) {
		List<String> projectNames = getProjectNames(spaceFolder);
		List<File> result = new ArrayList<File>();
		//获取工作空间下面所有的文件
		File[] files = spaceFolder.listFiles();
		for (String projectName : projectNames) {
			for (File file : files) {
				if (file.isDirectory()) {//如果工作空间下面的文件是目录，再去判断是否是项目目录
					if (projectName.equals(file.getName())) {
						result.add(file);
					}
				}
			}
		}
		return result;
	}
}


