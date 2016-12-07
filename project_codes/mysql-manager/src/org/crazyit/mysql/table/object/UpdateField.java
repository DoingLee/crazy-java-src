package org.crazyit.mysql.table.object;

/**
 * 在操作中被修改的字段对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UpdateField {

	private Field sourceField;
	
	private Field newField;

	public UpdateField(Field sourceField, Field newField) {
		this.sourceField = sourceField;
		this.newField = newField;
	}

	public Field getSourceField() {
		return sourceField;
	}

	public void setSourceField(Field sourceField) {
		this.sourceField = sourceField;
	}

	public Field getNewField() {
		return newField;
	}

	public void setNewField(Field newField) {
		this.newField = newField;
	}
	
	
}
