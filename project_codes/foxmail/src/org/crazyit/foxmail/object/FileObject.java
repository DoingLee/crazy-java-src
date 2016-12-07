package org.crazyit.foxmail.object;

import java.io.File;

/**
 * 附件的对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FileObject {

	//源文件名字
	private String sourceName;
	//对应的文件
	private File file;
	
	public FileObject(String sourceName, File file) {
		this.sourceName = sourceName;
		this.file = file;
	}

	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return this.sourceName;
	}
	
	
}
