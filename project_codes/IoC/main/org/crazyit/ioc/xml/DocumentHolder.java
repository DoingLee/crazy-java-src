package org.crazyit.ioc.xml;

import org.dom4j.Document;

/**
 * 文档对象持有者, 维护各个配置文件的对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface DocumentHolder {
	
	/**
	 * 根据文件的路径返回文档对象
	 * @param filePath
	 * @return
	 */
	Document getDocument(String filePath);
}
