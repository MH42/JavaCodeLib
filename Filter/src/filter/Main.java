package filter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException {
		Document xml = parse("D:\\\\Matthias\\\\Projekte\\\\Java\\\\JavaCodeLib\\\\DirectoryTasks\\\\src\\\\mergeXml\\\\xml1.xml");
		printDocument(xml);
		// test 1
		FilterChain filterChain = new FilterChain();
		filterChain.addFilter(new AddNodeFilterCriterion("test", "//Empoyee"));
		xml = filterChain.filter(xml);
		printDocument(xml);
		
		// test 2
		filterChain = new FilterChain();
		filterChain.addFilter(new AddNodeFilterCriterion("test", "//Employee"));
		xml = filterChain.filter(xml);
		printDocument(xml);
		// find all relevant nodes
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		XPathExpression expr = xpath.compile("//*[not(*)]");

	}

	public static Document parse(String path) throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}
	
	private static void printDocument(Document xml) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		DOMSource dom = new DOMSource(xml.getDocumentElement());

		transformer.transform(dom, streamResult1);
		System.out.println(writer1.toString());
	}
}
