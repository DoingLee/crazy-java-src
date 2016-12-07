package org.crazyit.mysql.table.object;

/**
 * 在操作中被修改的外键对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UpdateForeignField {

	//界面中新的外键
	private ForeignField newForeignField;
	
	//数据库中旧的外键
	private ForeignField sourceForeignField;

	public UpdateForeignField(ForeignField sourceForeignField, 
			ForeignField newForeignField) {
		this.newForeignField = newForeignField;
		this.sourceForeignField = sourceForeignField;
	}

	public ForeignField getNewForeignField() {
		return newForeignField;
	}

	public void setNewForeignField(ForeignField newForeignField) {
		this.newForeignField = newForeignField;
	}

	public ForeignField getSourceForeignField() {
		return sourceForeignField;
	}

	public void setSourceForeignField(ForeignField sourceForeignField) {
		this.sourceForeignField = sourceForeignField;
	}
	
	
}
