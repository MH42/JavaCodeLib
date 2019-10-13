package mergeXml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


class XmlToolsTest {
	private static final String DIR_PATH = "path-to-dir";
	private XmlTools xmlTools;
	private Document xml1;
	private Document xml2;
	private Document merged;
	@BeforeEach

	
	void setUp() throws Exception {
		xmlTools = new XmlTools();
		try {
			xml1 = xmlTools.parse(DIR_PATH + "xml1.xml");
			xml2 = xmlTools.parse(DIR_PATH + "xml2.xml");
			merged = xmlTools.parse(DIR_PATH + "merged12.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testMerge() throws TransformerException {
		Element mergedDoc = merged.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml1.getDocumentElement(), xml2.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge34() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml3 = xmlTools.parse(DIR_PATH + "xml3.xml");
		Document xml4 = xmlTools.parse(DIR_PATH + "xml4.xml");
		Document merged34 = xmlTools.parse(DIR_PATH + "merged34.xml");

		Element mergedDoc = merged34.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml3.getDocumentElement(), xml4.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge56() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml5 = xmlTools.parse(DIR_PATH + "xml5.xml");
		Document xml6 = xmlTools.parse(DIR_PATH + "xml6.xml");
		Document merged56 = xmlTools.parse(DIR_PATH + "merged56.xml");

		Element mergedDoc = merged56.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml5.getDocumentElement(), xml6.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge56b() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml5 = xmlTools.parse(DIR_PATH + "xml5.xml");
		Document xml6b = xmlTools.parse(DIR_PATH + "xml6b.xml");
		Document merged56b = xmlTools.parse(DIR_PATH + "merged56b.xml");

		Element mergedDoc = merged56b.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml5.getDocumentElement(), xml6b.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge78() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml7 = xmlTools.parse(DIR_PATH + "xml7.xml");
		Document xml8 = xmlTools.parse(DIR_PATH + "xml8.xml");
		Document merged78 = xmlTools.parse(DIR_PATH + "merged78.xml");

		Element mergedDoc = merged78.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml7.getDocumentElement(), xml8.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge910() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml9 = xmlTools.parse(DIR_PATH + "xml9.xml");
		Document xml10 = xmlTools.parse(DIR_PATH + "xml10.xml");
		Document merged910 = xmlTools.parse(DIR_PATH + "merged910.xml");

		Element mergedDoc = merged910.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml9.getDocumentElement(), xml10.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge1112() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml11 = xmlTools.parse(DIR_PATH + "xml11.xml");
		Document xml12 = xmlTools.parse(DIR_PATH + "xml12.xml");
		Document merged1112 = xmlTools.parse(DIR_PATH + "merged1112.xml");

		Element mergedDoc = merged1112.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml11.getDocumentElement(), xml12.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}

	@Test
	void testMerge1112b() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		Document xml11 = xmlTools.parse(DIR_PATH + "xml11.xml");
		Document xml12b = xmlTools.parse(DIR_PATH + "xml12b.xml");
		Document merged1112b = xmlTools.parse(DIR_PATH + "merged1112b.xml");

		Element mergedDoc = merged1112b.getDocumentElement();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource mergedDom = new DOMSource(mergedDoc);

		Node mergedNode = xmlTools.merge(xml11.getDocumentElement(), xml12b.getDocumentElement());
		TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
		Transformer transformer2 = transformerFactory2.newTransformer();
		DOMSource mergedDom2 = new DOMSource(mergedNode);

		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging

		transformer.transform(mergedDom, streamResult1);
		transformer2.transform(mergedDom2, streamResult2);

		assertEquals(writer1.toString(), writer2.toString());
	}
	
	@Test
	public void testSpecialMove() throws TransformerException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		Document groupedXml = xmlTools.parse(DIR_PATH + "groupedXml7.xml");
		Node node = xmlTools.specialMove();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Transformer transformer2 = transformerFactory.newTransformer();
		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		StringWriter writer2 = new StringWriter();
		StreamResult streamResult2 = new StreamResult(writer2);
		DOMSource dom = new DOMSource(node);
		DOMSource expected = new DOMSource(groupedXml.getDocumentElement());

		transformer.transform(dom, streamResult1);
		transformer2.transform(expected, streamResult2);
		assertEquals(writer2.toString(), writer1.toString());
	}
}


