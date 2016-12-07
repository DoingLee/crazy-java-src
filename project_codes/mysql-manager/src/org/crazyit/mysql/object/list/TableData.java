package org.crazyit.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.QueryObject;
import org.crazyit.mysql.object.tree.Database;
import org.crazyit.mysql.table.object.Field;
import org.crazyit.mysql.table.object.ForeignField;
import org.crazyit.mysql.table.object.UpdateField;
import org.crazyit.mysql.table.object.UpdateForeignField;
import org.crazyit.mysql.util.ImageUtil;

/**
 * 列表中的表对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TableData extends AbstractData implements QueryObject {

	//创建表命令
	private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	
	//FOREIGN KEY字符串
	private final static String FOREIGN_KEY = "FOREIGN KEY";
	
	private final static String CONSTRAINT = "CONSTRAINT";
	
	//REFERENCES字符串
	private final static String REFERENCES = "REFERENCES";
	
	//PRIMARY KEY字符串
	private final static String PRIMARY_KEY = "PRIMARY KEY";
	
	//DEFAULT字符串
	private final static String DEFAULT = "DEFAULT";
	
	//AUTO_INCREMENT字符串
	private final static String AUTO_INCREMENT = "AUTO_INCREMENT";
	
	//NOT NULL字符串
	private final static String NOT_NULL = "NOT NULL";
	
	public final static String ON_DELETE = "ON DELETE";
	
	public final static String ON_UPDATE = "ON UPDATE";
	
	//该表所属的数据库
	private Database database;
		
	public TableData(Database database) {
		this.database = database;
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
	/**
	 * 获得表中的数据
	 * @return
	 */
	public ResultSet getDatas(String orderString) {
		try {
			Statement stmt = database.getStatement();
			String sql = getQuerySQL(orderString);
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			throw new QueryException("查询表数据异常：" + this.name);
		}
	}

	/**
	 * 返回该表的数据量
	 * @return
	 */
	public int getDataCount() {
		try {
			Statement stmt = database.getStatement();
			//得到全部记录数的SQL
			String sql = "SELECT COUNT(*) FROM " + this.name;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("查询表数据异常：" + this.name);
		}
	}
	
	public String getQueryName() {
		return this.name;
	}

	//返回表查询数据的SQL
	public String getQuerySQL(String orderString) {
		StringBuffer sql = new StringBuffer("SELECT * FROM " + this.name);
		if (orderString == null || orderString.trim().equals("")) {
			return sql.toString();
		} else {
			sql.append(" ORDER BY " + orderString);
			return sql.toString();
		}
	}
	
	/**
	 * 返回该表的所有字段
	 * @return
	 */
	public List<Field> readFields() {
		try {
			List<Field> result = new ArrayList<Field>();
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(getFieldSQL());
			while (rs.next()) {
				//得到字段的各个属性
				String fieldName = rs.getString("COLUMN_NAME");
				String type = rs.getString("COLUMN_TYPE");
				boolean allowNull = rs.getBoolean("IS_NULLABLE");
				boolean isPrimaryKey = isPrimaryKey(rs.getString("COLUMN_KEY"));
				String defaultValue = rs.getString("COLUMN_DEFAULT");
				boolean autoIncrement = isAutoIncrement(rs.getString("EXTRA"));
				//创建Field对象
				Field field = new Field(fieldName, type, allowNull, 
						isPrimaryKey, defaultValue, autoIncrement);
				//设置字段所属的表
				field.setTable(this);
				result.add(field);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("查询表字段异常：" + e.getMessage());
		}
	}
	
	/**
	 * 删除一个本类对应的表
	 */
	public void dropTable() {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DROP TABLE IF EXISTS " + this.name);
			Statement stmt = database.getStatement();
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new QueryException("删除表异常：" + e.getMessage());
		}
	}
	
	/**
	 * 返回该表的所有外键字段
	 * @return
	 */
	public List<ForeignField> getForeignFields(List<Field> fields) {
		try {
			StringBuffer sql = new StringBuffer();
			//去系统表KEY_COLUMN_USAGE查
			sql.append("SELECT * FROM information_schema.KEY_COLUMN_USAGE sc")
			.append(" WHERE sc.TABLE_SCHEMA='" + this.database.getDatabaseName() + "'")
			.append(" AND sc.TABLE_NAME='" + this.name + "'")
			.append(" AND sc.REFERENCED_COLUMN_NAME <> '' ORDER BY sc.COLUMN_NAME");
			System.out.println(sql.toString());
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			List<ForeignField> result = new ArrayList<ForeignField>();
			while (rs.next()) {
				//得到Field对象，从ResultSet中得到COLUMN_NAME，再去本表的所有字段中找
				Field field = getForeignField(fields, rs.getString("COLUMN_NAME"));
				//得到约束的名称，该名称唯一
				String constraintName = rs.getString("CONSTRAINT_NAME");
				String referenceTableName = rs.getString("REFERENCED_TABLE_NAME");
				String referenceFieldName = rs.getString("REFERENCED_COLUMN_NAME");
				result.add(new ForeignField(constraintName, field, 
						referenceTableName, referenceFieldName));
			}
			rs.close();
			//再设置每个ForeignField的约束字段
			setReferenceField(result);
			//设置ON DELETE和ON UPDATE的值
			setOnValue(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("查询表字段异常：" + e.getMessage());
		}
	}
	
	/**
	 * 设置集合中的外键字段的ON DELETE和ON UPDATE的值
	 * @param foreignFields
	 */
	private void setOnValue(List<ForeignField> foreignFields) {
		//得到创建表的SQL定义，再对这个字符串进行分析处理，最后得到值
		String createTableSQL = getCreateSQL();
		for (ForeignField field : foreignFields) {
			String onDelete = getOnDeleteValue(createTableSQL, field);
			String onUpdate = getOnUpdateValue(createTableSQL, field);
			field.setOnDelete(onDelete);
			field.setOnUpdate(onUpdate);
		}
	}
	
	/**
	 * 根据字段名称查找字段
	 * @param name
	 * @return
	 */
	public Field getFieldByName(String columnName) {
		try {
			//去系统表COLUMNS中查找
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM information_schema.COLUMNS sc")
			.append(" WHERE sc.TABLE_NAME='" + this.name + "'")
			.append(" AND sc.COLUMN_NAME='" + columnName + "'");
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			//如果找不到该字段，则返回null
			Field field = null;
			if (rs.next()) field = new Field(columnName, this);
			rs.close();
			return field;
		} catch (Exception e) {
			throw new QueryException("查询字段异常：" + e.getMessage());
		}
	}
	
	/**
	 * 设置ForeignField对象的约束字段
	 * @param foreignFields
	 */
	private void setReferenceField(List<ForeignField> foreignFields) {
		for(ForeignField f : foreignFields) {
			//找到约束的表
			TableData referenceTable = this.database.getTableByName(f.getReferenceTableName());
			//找到对应的该表中约束的字段
			if (referenceTable != null) {
				Field referenceField = referenceTable.getFieldByName(f.getReferenceFieldName());
				f.setReferenceField(referenceField);
			}
		}
	}
	
	/**
	 * 从表所有的字段中查找名字为columnName的字段
	 * @param fields
	 * @param columnName
	 * @return
	 */
	private Field getForeignField(List<Field> fields, String columnName) {
		for (Field f : fields) {
			if (f.getFieldName().equals(columnName)) return f;
		}
		return null;
	}
	
	/**
	 * 返回创建该表的SQL
	 * @return
	 */
	private String getCreateSQL() {
		try {
			String sql = "SHOW CREATE TABLE " + this.name;
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String result = "";
			while (rs.next()) result = rs.getString("Create Table");
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("获取创建表的SQL错误：" + e.getMessage());
		}
	}
	
	/**
	 * 返回ON DELETE或者ON UPDATE的值
	 * @param createSQL
	 * @param foreignField
	 * @param on
	 * @return
	 */
	private String getOnValue(String createSQL, ForeignField foreignField, 
			String on) {
		String constraintName = foreignField.getConstraintName();
		//以逗号将其分隔
		String[] temp = createSQL.split(",");
		for (int i = 0; i < temp.length; i++) {
			String tempString = temp[i];
			//如果遇到外键的字符串，则进行处理
			if (tempString.indexOf("CONSTRAINT `" + constraintName + "`") != -1) {
				//如果遇到ON DELETE或者ON UPDATE，则进行处理，返回ON DELETE或者ON UPDATE的值
				if (tempString.indexOf(on) != -1) {
					//得到ON DELETE或者ON UPDAT的位置
					int onIndex = tempString.indexOf(on) + on.length() + 1;
					String value = tempString.substring(onIndex, onIndex + 7);
					if (value.indexOf("NO ACTI") != -1) return "NO ACTION";
					else if (value.indexOf("RESTRIC") != -1)return "RESTRICT";
					else if (value.indexOf("CASCADE") != -1) return "CASCADE";
					else if (value.indexOf("SET NUL") != -1)return "SET NULL";
				}
			}
		}
		return null;
	}
	
	/*
	 * 得到外键的ON UPDATE值，适用于MySQL5.0
	 */
	private String getOnUpdateValue(String createSQL, ForeignField foreignField) {
		return getOnValue(createSQL, foreignField, ON_UPDATE);
	}
	
	/*
	 * 得到外键的ON DELETE值，适用于MySQL5.0
	 */
	private String getOnDeleteValue(String createSQL, ForeignField foreignField) {
		return getOnValue(createSQL, foreignField, ON_DELETE);
	}
	
	/*
	 * 判断一个字段值是否为主键
	 */
	private boolean isPrimaryKey(String value) {
		if ("PRI".equals(value)) return true;
		return false;
	}
	
	/*
	 * 判断一个字段值是否为自动增长
	 */
	private boolean isAutoIncrement(String value) {
		if ("auto_increment".equals(value)) return true;
		return false;
	}
	
	/*
	 * 返回查询所有字段的SQL语句
	 */
	private String getFieldSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM information_schema.COLUMNS sc")
		.append(" WHERE sc.TABLE_SCHEMA='")
		.append(this.database.getDatabaseName() + "' ")
		.append(" AND sc.TABLE_NAME='")
		.append(this.name + "' ")
		.append("ORDER BY sc.ORDINAL_POSITION");
		return sql.toString();
	}
	
	public Icon getIcon() {
		return ImageUtil.TABLE_DATA_ICON;
	}

	public String toString() {
		return this.name;
	}
	
	/**
	 * 创建一个表
	 * @param fields 表的所有字段
	 * @param foreignFields 外键字段
	 */
	public void addTable(List<Field> fields, List<ForeignField> foreignFields) {
		try {
			String createSQL = getTableSQL(this.getName(), fields, 
					foreignFields, CREATE_TABLE);
			System.out.println(createSQL);
			Statement stmt = database.getStatement();
			stmt.execute(createSQL);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("新建表错误：" + e.getMessage());
		}
	}
	
	/**
	 * 修改一个表
	 * @param addFields 需要添加的字段
	 * @param updateFields 修改的字段
	 * @param dropFields 删除的字段
	 * @param addFF 添加的外键
	 * @param updateFF 修改的外键
	 * @param dropFF 删除的外键
	 */
	public void updateTable(List<Field> addFields, List<UpdateField> updateFields, 
			List<Field> dropFields, List<ForeignField> addFF, 
			List<UpdateForeignField> updateFF, List<ForeignField> dropFF) {
		//得到添加字段的SQL
		List<String> addFieldSQL = getAlterAddFieldSQL(addFields);
		//得到修改字段的SQL
		List<String> updateFieldSQL = getAlterUpdateFieldSQL(updateFields);
		//得到删除字段的SQL
		List<String> dropFieldSQL = getAlterDropFieldSQL(dropFields);
		//得到添加外键的SQL
		List<String> addFFSQL = getAlterAddForeignFieldSQL(addFF);
		//得到修改外键的SQL
		List<String> updateFFSQL = getAlterUpdateForeignFieldSQL(updateFF);
		//得到删除外键的SQL
		List<String> dropFFSQL = getAlterDropForeignFieldSQL(dropFF);
		try {
			Statement stmt = database.getStatement();
			for (String s : addFieldSQL) stmt.addBatch(s);
			for (String s : updateFieldSQL) stmt.addBatch(s);
			for (String s : dropFieldSQL) stmt.addBatch(s);
			for (String s : addFFSQL) stmt.addBatch(s);
			for (String s : updateFFSQL) stmt.addBatch(s);
			for (String s : dropFFSQL) stmt.addBatch(s);
			stmt.executeBatch();
		} catch (Exception e) {
			throw new QueryException("更改表错误：" + e.getMessage());
		}
	}
	
	//返回全部删除外键的SQL语句
	private List<String> getAlterDropForeignFieldSQL(List<ForeignField> dropFF) {
		List<String> result = new ArrayList<String>();
		for (ForeignField ff : dropFF) {
			String sql = getDropForeignKeySQL(this.name, ff);
			result.add(sql);
		}
		return result;
	}
	
	//返回删除外键的SQL
	private String getDropForeignKeySQL(String tableName, ForeignField ff) {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE " + tableName)
		.append(" DROP FOREIGN KEY " + ff.getConstraintName());
		return sql.toString();
	}
	
	//返回全部修改外键的SQL语句，修改外键需要先删除再创建
	private List<String> getAlterUpdateForeignFieldSQL(List<UpdateForeignField> updateFF) {
		List<String> result = new ArrayList<String>();
		for (UpdateForeignField uff : updateFF) {
			ForeignField source = uff.getSourceForeignField();
			ForeignField newField = uff.getNewForeignField();
			//获得删除外键的SQL
			String dropSQL = getDropForeignKeySQL(this.name, source);
			//获得创建外键的SQL
			String newSQL = getAddForeignKeySQL(newField, this.name);
			result.add(dropSQL);
			result.add(newSQL);
		}
		return result;
	}
	
	//返回全部添加外键的ALTER TABLE ADD FOIEIGN KEY语句
	private List<String> getAlterAddForeignFieldSQL(List<ForeignField> addFF) {
		//先为各个约束字段加入索引
		List<String> result = getAddReferenceFieldIndex(addFF);
		for (ForeignField ff : addFF) {
			String sql = getAddForeignKeySQL(ff, this.name);
			result.add(sql.toString());
		}
		return result;
	}
	
	//返回创建外键约束的SQL语句
	private String getAddForeignKeySQL(ForeignField ff, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE " + tableName)
		.append(" ADD FOREIGN KEY")
		.append(" (`" + ff.getField().getFieldName() + "`)")
		.append(" REFERENCES `" + ff.getReferenceField().getTable().getName() + "`")
		.append(" (`" + ff.getReferenceField().getFieldName() + "`)");
		if (ff.getOnDelete() != null) sql.append(" ON DELETE " + ff.getOnDelete());
		if (ff.getOnUpdate() != null) sql.append(" ON UPDATE " + ff.getOnUpdate());
		return sql.toString();
	}
	
	//为约束字段加索引
	private List<String> getAddReferenceFieldIndex(List<ForeignField> addFF) {
		List<String> result = new ArrayList<String>();
		for (ForeignField ff : addFF) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + ff.getReferenceField().getTable().getName())
			.append(" ADD INDEX")
			.append(" (`" + ff.getReferenceField().getFieldName() + "`)");
			result.add(sql.toString());
		}
		return result;
	}
	
	//返回全部删除字段的SQL语句
	private List<String> getAlterDropFieldSQL(List<Field> dropFields) {
		List<String> result = new ArrayList<String>();
		for (Field f : dropFields) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name + " DROP COLUMN")
			.append(" " + f.getFieldName());
			result.add(sql.toString());
		}
		return result;
	}
	
	//返回全部的ALTER TABLE CHANGE字段语句
	private List<String> getAlterUpdateFieldSQL(List<UpdateField> updateFields) {
		List<String> result = new ArrayList<String>();
		for (UpdateField f : updateFields) {
			//得到数据库中的字段
			Field source = f.getSourceField();
			//得到修改后的字段
			Field newField = f.getNewField();
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name + " CHANGE")
			.append(" " + source.getFieldName() + " " + newField.getFieldName())
			.append(" " + newField.getType());
			if (!newField.isAllowNull()) sql.append(" NOT NULL");
			if (newField.getDefaultValue() != null) {
				sql.append(" DEFAULT '" + newField.getDefaultValue() + "'");
			}
			if (newField.isAutoIncrement()) sql.append(" AUTO_INCREMENT");
			if (!source.isPrimaryKey() && newField.isPrimaryKey()) {
				sql.append(", ADD PRIMARY KEY (" + newField.getFieldName() + ")");
			}
			result.add(sql.toString());
		}
		return result;
	}
	
	//返回全部的ALTER TABLE ADD字段语句
	private List<String> getAlterAddFieldSQL(List<Field> addFields) {
		List<String> result = new ArrayList<String>();
		for (Field f : addFields) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name)
			.append(" ADD " + f.getFieldName())
			.append(" " + f.getType());
			if (!f.isAllowNull()) sql.append(" NOT NULL");
			if (f.getDefaultValue() != null) {
				sql.append(" DEFAULT '" + f.getDefaultValue() + "'");
			}
			if (f.isAutoIncrement()) sql.append(" AUTO_INCREMENT");
			if (f.isPrimaryKey()) {
				sql.append(", ADD PRIMARY KEY (" + f.getFieldName() + ")");
			}
			result.add(sql.toString());
		}
		return result;
	}
	
	/**
	 * 获取创建一个表的SQL语句
	 * @param table
	 * @param fields
	 * @param foreignFields
	 * @param command 创建或者修改（create table or alter table）
	 * @return
	 */
	private String getTableSQL(String tableName, 
			List<Field> fields, List<ForeignField> foreignFields, String command) {
		StringBuffer sql = new StringBuffer(command + "`");
		sql.append(tableName + "` (");
		createField(sql, fields);
		createForeignFields(sql, foreignFields);
		createPrimary(sql, fields);
		sql.append(")");
		return cutLastComma(sql.toString()) + ")";
	}
	
	//去掉最后的逗号
	private String cutLastComma(String sql) {
		if (sql.lastIndexOf(",") == -1) return sql;
		int last = sql.lastIndexOf(",");
		return sql.substring(0, last);
	}
	
	//创建主键SQL
	private void createPrimary(StringBuffer sql, List<Field> fields) {
		for (Field f : fields) {
			if (f.isPrimaryKey()) {
				sql.append(PRIMARY_KEY)
				.append("(`" + f.getFieldName() + "`)")
				.append(",");
			}
		}
	}
	
	//创建外键SQL
	private void createForeignFields(StringBuffer sql, 
			List<ForeignField> foreignFields) {
		for (ForeignField f : foreignFields) {
			sql.append(FOREIGN_KEY)
			.append(" (`" + f.getField().getFieldName() + "`) ")
			.append(REFERENCES)
			.append(" `" + f.getReferenceField().getTable().getName() + "` ")
			.append("(`" + f.getReferenceField().getFieldName() + "`) ");
			if (f.getOnDelete() != null) {
				sql.append(ON_DELETE + " " + f.getOnDelete() + " ");
			}
			if (f.getOnUpdate() != null) {
				sql.append(ON_UPDATE + " " + f.getOnUpdate() + " ");
			}
			sql.append(",");
		}
	}
	
	//根据字段创建SQL
	private void createField(StringBuffer sql, List<Field> fields) {
		for (Field f : fields) {
			sql.append("`" + f.getFieldName() + "` ")
			.append(f.getType());
			//自增长加入AUTO_INCREMENT
			if (f.isAutoIncrement()) sql.append(AUTO_INCREMENT + " ");
			//该字段不允许为空, 加入NOT NULL
			if (!f.isAllowNull()) sql.append(NOT_NULL + " ");
			//该字段有默认值,并且不是自动增长
			if (!f.isAutoIncrement()) {
				if (f.getDefaultValue() != null) {
					sql.append(DEFAULT + " '" + f.getDefaultValue() + "' ");
				}
			}
			sql.append(",");
		}
	}
}
