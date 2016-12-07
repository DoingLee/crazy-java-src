package org.crazyit.ioc.xml;

import java.util.List;

import junit.framework.TestCase;

import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.construct.RefElement;
import org.crazyit.ioc.xml.construct.ValueElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Document;
import org.dom4j.Element;

public class ElementReaderTest extends TestCase {

	XmlDocumentHolder holder;
	
	ElementLoader elementLoader;
		
	ElementReader reader;
	
	protected void setUp() throws Exception {
		holder = new XmlDocumentHolder();
		elementLoader = new ElementLoaderImpl();
		String filePath = "test/resources/ElementReader.xml";
		Document doc = holder.getDocument(filePath);
		elementLoader.addElements(doc);
		reader = new ElementReaderImpl();
	}

	protected void tearDown() throws Exception {
		holder = null;
		elementLoader = null;
	}
	
	public void testIsLazy() {
		//第一个元素
		Element e = elementLoader.getElement("test1-1");
		boolean result = reader.isLazy(e);
		assertTrue(result);
		//第二个元素
		e = elementLoader.getElement("test1-2");
		result = reader.isLazy(e);
		assertFalse(result);
		//第三个元素
		e = elementLoader.getElement("test1-3");
		result = reader.isLazy(e);
		assertFalse(result);
	}
	
	public void testGetConstructorElements() {
		Element e = elementLoader.getElement("test2");
		List<Element> constructorElements = reader.getConstructorElements(e);
		assertEquals(constructorElements.size(), 2);
	}
	
	public void testGetAttribute() {
		Element e = elementLoader.getElement("test3");
		String value =  reader.getAttribute(e, "class");
		assertEquals(value, "org.crazyit.ioc.xml.TestObject1");
	}
	
	public void testIsSingleton() {
		Element e = elementLoader.getElement("test4-1");
		boolean result = reader.isSingleton(e);
		assertFalse(result);
		
		e = elementLoader.getElement("test4-2");
		result = reader.isSingleton(e);
		assertTrue(result);
	}
//	
//	public void testGetConstructorArgs() {
//		Element e = elementLoader.getElement("test5");
//		List<String> values = reader.getConstructorArgsValue(e);
//		assertEquals(values.size(), 2);
//		assertEquals(values.get(0), "yangenxiong");
//		assertEquals(values.get(1), "crazyit");
//	}
	
	public void testGetPropertyElements() {
		Element e = elementLoader.getElement("test6");
		List<Element> elements = reader.getPropertyElements(e);
		assertEquals(elements.size(), 2);
	}
	
//	public void testGetPropertyValues() {
//		Element e = elementLoader.getElement("test7");
//		List<String> values = reader.getPropertyValues(e);
//		assertEquals(values.size(), 2);
//		assertEquals(values.get(0), "yangenxiong");
//		assertEquals(values.get(1), "crazyit");
//	}
	
//	public void testGetPropertyRef() {
//		Element e = elementLoader.getElement("test8");
//		assertEquals(reader.getAttribute(e, "id"), "test8");
//		List<String> refs = reader.getPropertyRef(e);
//		assertEquals(refs.size(), 2);
//		assertEquals(refs.get(0), "test1");
//		assertEquals(refs.get(1), "test2");
//	}
	
//	public void testGetConstructorRef() {
//		Element e = elementLoader.getElement("test9");
//		assertEquals(reader.getAttribute(e, "id"), "test9");
//		List<String> refs = reader.getConstructorRef(e);
//		assertEquals(refs.size(), 2);
//		assertEquals(refs.get(0), "test1");
//		assertEquals(refs.get(1), "test2");
//	}
	
	public void testGetAutowire() {
		Element e = elementLoader.getElement("test10-1");
		assertEquals(reader.getAttribute(e, "id"), "test10-1");
		Autowire result = reader.getAutowire(e);
		assertEquals(result.getValue(), "byName");
		
		e = elementLoader.getElement("test10-2");
		result = reader.getAutowire(e);
		assertEquals(result.getValue(), "no");
		
		e = elementLoader.getElement("test10-3");
		result = reader.getAutowire(e);
		assertEquals(result.getValue(), "no");
	}
	
	public void testGetConstructorValue() {
		Element e = elementLoader.getElement("test11");
		assertEquals(reader.getAttribute(e, "id"), "test11");
		List<DataElement> result = reader.getConstructorValue(e);
		assertEquals(result.size(), 3);
		
		ValueElement ve1 = (ValueElement)result.get(0);
		assertEquals(ve1.getValue(), "yangenxiong");
		
		RefElement re = (RefElement)result.get(1);
		assertEquals(re.getValue(), "test2");
		
		ValueElement ve2 = (ValueElement)result.get(2);
		assertEquals(ve2.getValue(), 20);
	}
	
	public void testGetPropertyValue() {
		Element e = elementLoader.getElement("test12");
		List<PropertyElement> eles = reader.getPropertyValue(e);
		assertEquals(eles.size(), 3);
		assertEquals(eles.get(0).getName(), "property1");
		assertEquals(eles.get(0).getDataElement().getValue(), "yangenxiong");
		assertEquals(eles.get(0).getDataElement().getType(), "value");
	}

}
