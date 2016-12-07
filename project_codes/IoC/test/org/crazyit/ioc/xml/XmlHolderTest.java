package org.crazyit.ioc.xml;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;

public class XmlHolderTest extends TestCase {

	XmlDocumentHolder holder;
	
	protected void setUp() throws Exception {
		holder = new XmlDocumentHolder();
	}

	protected void tearDown() throws Exception {
		holder = null;
	}
	
	public void testGetDoc() {
		String filePath = "test/resources/XmlHolder.xml";
		//获得Document对象
		Document doc = holder.getDocument(filePath);
		assertNotNull(doc);
		Element root = doc.getRootElement();
		assertEquals(root.getName(), "beans");
		System.out.println(root.getName());
		//重新获取一次, 判断两个Document对象是否一致
		Document doc2 = holder.getDocument(filePath);
		System.out.println(doc);
		System.out.println(doc2);
	}

}
