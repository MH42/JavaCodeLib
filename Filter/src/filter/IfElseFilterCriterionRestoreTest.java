package filter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IfElseFilterCriterionRestoreTest {

	private Document xml;
	private String path = "D:\\Matthias\\Projekte\\Java\\Filter\\src\\filter\\";
	
	@BeforeEach
	void setUp() throws Exception {
		xml = parse(path + "xml1.xml");
		System.out.println("Original XML");
		System.out.println(getDocumentAsString(xml));
		System.out.println("\n\n\n");
	}

	@Test
	void test1() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		filterChain.addFilter(new AddNodeFilterCriterion("test", "//Empoyee"));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "ifElseFilterCriterionTest1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
		
		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}
	
	@Test
	void test2() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		filterChain.addFilter(new AddNodeFilterCriterion("test", "//Employee"));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "ifElseFilterCriterionTest2.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
		
		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}
	
	@Test
	void test3() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		List<FilterCriterion> thenFilterCriterionList = new ArrayList<>();
		thenFilterCriterionList.add(new AddNodeFilterCriterion("test", "//Employee"));
		thenFilterCriterionList.add(new AddNodeFilterCriterion("test1", "//name"));
		Predicate<Document> condition = new Predicate<Document>() {
			@Override
			public boolean test(Document d) {
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr;
				try {
					expr = xpath.compile("//Employee");
					NodeList customers = (NodeList)expr.evaluate(d, XPathConstants.NODESET);
					return customers.getLength() > 0;
				} catch (XPathExpressionException e) {
					e.printStackTrace();
					return false;
				}
			}
		};
		filterChain.addFilter(new IfElseFilterCriterion(condition, thenFilterCriterionList, null));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "ifElseFilterCriterionTest3.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
		
		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}
	
	@Test
	void test31() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		List<FilterCriterion> thenFilterCriterionList = new ArrayList<>();
		thenFilterCriterionList.add(new AddNodeFilterCriterion("test", "//Employee"));
		thenFilterCriterionList.add(new AddNodeFilterCriterion("test1", "//name"));
		Predicate<Document> condition = new Predicate<Document>() {
			@Override
			public boolean test(Document d) {
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr;
				try {
					expr = xpath.compile("//Empoyee");
					NodeList customers = (NodeList)expr.evaluate(d, XPathConstants.NODESET);
					return customers.getLength() > 0;
				} catch (XPathExpressionException e) {
					e.printStackTrace();
					return false;
				}
			}
		};
		filterChain.addFilter(new IfElseFilterCriterion(condition, thenFilterCriterionList, null));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));

		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}
	
	@Test
	void test32() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		List<FilterCriterion> elseFilterCriterionList = new ArrayList<>();
		elseFilterCriterionList.add(new AddNodeFilterCriterion("test", "//Employee"));
		elseFilterCriterionList.add(new AddNodeFilterCriterion("test1", "//name"));
		Predicate<Document> condition = new Predicate<Document>() {
			@Override
			public boolean test(Document d) {
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr;
				try {
					expr = xpath.compile("//Employee");
					NodeList customers = (NodeList)expr.evaluate(d, XPathConstants.NODESET);
					return customers.getLength() > 0;
				} catch (XPathExpressionException e) {
					e.printStackTrace();
					return false;
				}
			}
		};
		filterChain.addFilter(new IfElseFilterCriterion(condition, null, elseFilterCriterionList));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));

		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}
	
	@Test
	void test4() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		FilterChain filterChain = new FilterChain();
		List<FilterCriterion> thenFilterCriterionList = new ArrayList<>();
		List<FilterCriterion> elseFilterCriterionList = new ArrayList<>();
		thenFilterCriterionList.add(new AddNodeFilterCriterion("t", "//a"));
		thenFilterCriterionList.add(new AddNodeFilterCriterion("t1", "//b"));
		elseFilterCriterionList.add(new AddNodeFilterCriterion("test", "//Employee"));
		elseFilterCriterionList.add(new AddNodeFilterCriterion("test1", "//gender"));
		Predicate<Document> condition = new Predicate<Document>() {
			@Override
			public boolean test(Document d) {
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr;
				try {
					expr = xpath.compile("//Empoyee");
					NodeList customers = (NodeList)expr.evaluate(d, XPathConstants.NODESET);
					return customers.getLength() > 0;
				} catch (XPathExpressionException e) {
					e.printStackTrace();
					return false;
				}
			}
		};
		filterChain.addFilter(new IfElseFilterCriterion(condition, thenFilterCriterionList, elseFilterCriterionList));
		xml = filterChain.filter(xml);
		Document expectedXml = parse(path + "ifElseFilterCriterionTest4.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));

		// Restore
		xml = filterChain.restore(xml);
		expectedXml = parse(path + "xml1.xml");
		assertEquals(getDocumentAsString(expectedXml), getDocumentAsString(xml));
	}

	private Document parse(String path) throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}
	
	private String getDocumentAsString(Document xml) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		DOMSource dom = new DOMSource(xml.getDocumentElement());

		transformer.transform(dom, streamResult1);
//		System.out.println(writer1.toString());
		return writer1.toString();
	}

}
