package org.crazyit.ioc.xml;

import java.util.Collection;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;

public class ElementLoaderTest extends TestCase {

	XmlDocumentHolder holder;
	
	ElementLoader elementLoader;
	
	protected void setUp() throws Exception {
		holder = new XmlDocumentHolder();
		elementLoader = new ElementLoaderImpl();
	}

	protected void tearDown() throws Exception {
		holder = null;
		elementLoader = null;
	}

	public void testGetElements() {
		String filePath = "test/resources/ElementLoader.xml";
		Document doc = holder.getDocument(filePath);
		elementLoader.addElements(doc);
		Element e = elementLoader.getElement("test1");
		assertNotNull(e);
		System.out.println(e);
		
		Collection<Element> es = elementLoader.getElements();
		System.out.println(es.size());
	}
	
}
