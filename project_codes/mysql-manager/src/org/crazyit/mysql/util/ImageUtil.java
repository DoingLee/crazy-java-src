package org.crazyit.mysql.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 图片工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageUtil {

	//新建表时显示是否字段允许空的列，不选中图片
	public final static Icon UN_CHECKED_ICON = new ImageIcon("images/checkbox.gif");
	
	//新建表时显示是否字段允许空的列，选中图片
	public final static Icon CHECKED_ICON = new ImageIcon("images/checkbox-selected.gif");

	//新建表时显示的主键列图片
	public final static Icon PRIMARY_KEY = new ImageIcon("images/primary-key.gif");
	//主键空白图片
	public final static Icon PRIMARY_KEY_BLANK = new ImageIcon("images/primary-key-blank.gif");
	
	//树中服务器连接图片
	public final static Icon CONNECTION_OPEN = new ImageIcon("images/tree/connection-open.gif");
	//树中服务器没连接图片
	public final static Icon CONNECTION_CLOSE = new ImageIcon("images/tree/connection-close.gif");
	
	//树中数据库连接图片
	public final static Icon DATABASE_CLOSE = new ImageIcon("images/tree/database-close.gif");
	//树中数据库没连接图片
	public final static Icon DATABASE_OPEN = new ImageIcon("images/tree/database-open.gif");	
	
	//树中表节点的图标
	public final static Icon TABLE_TREE_ICON = new ImageIcon("images/tree/table.gif");
	//树中视图节点图标
	public final static Icon VIEW_TREE_ICON = new ImageIcon("images/tree/view.gif");
	//树中存储过程节点图标
	public final static Icon PROCEDURE_TREE_ICON = new ImageIcon("images/tree/procedure.gif");
	
	//树节点打开的图片
	public final static Icon NODE_OPEN = new ImageIcon("images/tree/node-open.gif");
	//树节点关闭的图片
	public final static Icon NODE_CLOSE = new ImageIcon("images/tree/node-close.gif");

	//主界面右边列表中的表图片
	public final static Icon TABLE_DATA_ICON = new ImageIcon("images/data/table.gif");
	public final static Icon VIEW_DATA_ICON = new ImageIcon("images/data/view.gif");
	public final static Icon PROCEDURE_DATA_ICON = new ImageIcon("images/data/procedure.gif");
	public final static Icon FUNCTION_DATA_ICON = new ImageIcon("images/data/function.gif");
}
