package org.crazyit.mysql.table.object;

/**
 * 表中的一个外键
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ForeignField {

	//约束的名称
	private String constraintName;
	
	//被约束的字段，根据该字段可以找出该外键对象所属于的表
	private Field field;
	
	//外键的字段，可以根据此属性找出该关系中的外键表
	private Field referenceField;
	
	//级联删除策略
	private String onDelete;
	
	//级联更新策略
	private String onUpdate;
	
	//约束表的名称
	private String referenceTableName;
	
	//约束字段的名称
	private String referenceFieldName;
	
	//字段的uuid
	private String uuid;

	public ForeignField(String constraintName, Field field, Field referenceField) {
		this.field = field;
		this.referenceField = referenceField;
		this.constraintName = constraintName;
	}
	
	public ForeignField(String constraintName, Field field, 
			String referenceTableName, String referenceFieldName) {
		this.constraintName = constraintName;
		this.field = field;
		this.referenceTableName = referenceTableName;
		this.referenceFieldName = referenceFieldName;
	}
	
	public ForeignField() {
		
	}

	public String getReferenceFieldName() {
		return referenceFieldName;
	}

	public void setReferenceFieldName(String referenceFieldName) {
		this.referenceFieldName = referenceFieldName;
	}

	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Field getReferenceField() {
		return referenceField;
	}

	public void setReferenceField(Field referenceField) {
		this.referenceField = referenceField;
	}

	public String getOnDelete() {
		return onDelete;
	}

	public void setOnDelete(String onDelete) {
		this.onDelete = onDelete;
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean uuidEquals(Object obj) {
		if (this.uuid == null) {
			return equals(obj);
		}
		ForeignField f = (ForeignField)obj;
		if (this.uuid.equals(f.getUuid())) {
			return true;
		}
		return false;
	}
	
}
