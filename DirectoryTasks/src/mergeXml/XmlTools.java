package mergeXml;

import java.io.File;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class XmlTools {
	public static void main(String[] args) {
		XmlTools xmlTools = new XmlTools();
//		try {
//			Document xml1 = xmlTools.parse("xml1.xml");
//			Document xml2 = xmlTools.parse("xml2.xml");
//			xmlTools.merge(xml1.getDocumentElement(), xml2.getDocumentElement());
//		} catch (SAXException | IOException | ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			xmlTools.specialMove();
//		} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException | TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			xmlTools.manipulateXmlMultiThreaded();
		} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException | TransformerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Node merge(Node node1, Node node2) {
		NodeList childNodes1 = node1.getChildNodes();
		NodeList childNodes2 = node2.getChildNodes();
		for(int i = 0; i < childNodes2.getLength(); i++) {
			Node child2 = childNodes2.item(i);
			if (child2.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			boolean found = false;
			for(int j = 0; j < childNodes1.getLength(); j++) {
				Node child1 = childNodes1.item(j);
				if (child1.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				if(isEqualNodeIgnoringChildren(child1, child2)) {
					merge(child1, child2);
					found = true;
					break;
				}
			}
			if(!found) {
				Node importedNode = node1.getOwnerDocument().importNode(child2, true);
				node1.appendChild(importedNode);
			}
		}
		return node1;
	}
	
	public Node mergeDeep(Node node1, Node node2) {
		NodeList childNodes1 = node1.getChildNodes();
		NodeList childNodes2 = node2.getChildNodes();
		for(int i = 0; i < childNodes2.getLength(); i++) {
			Node child2 = childNodes2.item(i);
			if (child2.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			boolean found = false;
			for(int j = 0; j < childNodes1.getLength(); j++) {
				Node child1 = childNodes1.item(j);
				if (child1.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				if(isEqualNode(child1, child2)) {
					mergeDeep(child1, child2);
					found = true;
					break;
				} 
			}
			if(!found) {
				Node importedNode = node1.getOwnerDocument().importNode(child2, true);
				node1.appendChild(importedNode);
			}
		}
		return node1;
	}


	public Document parse(String path) throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}


	private boolean isEqualNodeIgnoringChildren(Node node1, Node node2) {
		if (node2 == this) {
			return true;
		}
		if (node2.getNodeType() != node1.getNodeType()) {
			return false;
		}
		if (node1.getNodeName() == null) {
			if (node2.getNodeName() != null) {
				return false;
			}
		} else if (node2.getNodeName() == null) {
			if (node1.getNodeName() != null) {
				return false;
			}
		} else if (!node1.getNodeName().equals(node2.getNodeName())) {
			return false;
		}

		if (node1.getLocalName() == null) {
			if (node2.getLocalName() != null) {
				return false;
			}
		} else if (node2.getLocalName() == null) {
			if (node1.getLocalName() != null) {
				return false;
			}
		} else if (!node1.getLocalName().equals(node2.getLocalName())) {
			return false;
		}

		if (node1.getNamespaceURI() == null) {
			if (node2.getNamespaceURI() != null) {
				return false;
			}
		} else if (node2.getNamespaceURI() == null) {
			if (node1.getNamespaceURI() != null) {
				return false;
			}
		} else if (!node1.getNamespaceURI().equals(node2.getNamespaceURI())) {
			return false;
		}

		if (node1.getPrefix() == null) {
			if (node2.getPrefix() != null) {
				return false;
			}
		} else if (node2.getPrefix() == null) {
			if (node1.getPrefix() != null) {
				return false;
			}
		} else if (!node1.getPrefix().equals(node2.getPrefix())) {
			return false;
		}

		if (node1.getNodeValue() == null) {
			if (node2.getNodeValue() != null) {
				return false;
			}
		} else if (node2.getNodeValue() == null) {
			if (node1.getNodeValue() != null) {
				return false;
			}
		} else if (!node1.getNodeValue().equals(node2.getNodeValue())) {
			return false;
		}
		
		if (!node1.hasAttributes() && node2.hasAttributes() || node1.hasAttributes() && !node2.hasAttributes()) {
			return false;
		}
		else if (node1.hasAttributes() && node2.hasAttributes() && !isAttributeMapSame(node1.getAttributes(), node2.getAttributes())) {
			return false;
		}


		return true;
	}
	
	private boolean isEqualNode(Node node1, Node node2) {
		if (node2 == this) {
			return true;
		}
		if (node2.getNodeType() != node1.getNodeType()) {
			return false;
		}
		if (node1.getNodeName() == null) {
			if (node2.getNodeName() != null) {
				return false;
			}
		} else if (node2.getNodeName() == null) {
			if (node1.getNodeName() != null) {
				return false;
			}
		} else if (!node1.getNodeName().equals(node2.getNodeName())) {
			return false;
		}

		if (node1.getLocalName() == null) {
			if (node2.getLocalName() != null) {
				return false;
			}
		} else if (node2.getLocalName() == null) {
			if (node1.getLocalName() != null) {
				return false;
			}
		} else if (!node1.getLocalName().equals(node2.getLocalName())) {
			return false;
		}

		if (node1.getNamespaceURI() == null) {
			if (node2.getNamespaceURI() != null) {
				return false;
			}
		} else if (node2.getNamespaceURI() == null) {
			if (node1.getNamespaceURI() != null) {
				return false;
			}
		} else if (!node1.getNamespaceURI().equals(node2.getNamespaceURI())) {
			return false;
		}

		if (node1.getPrefix() == null) {
			if (node2.getPrefix() != null) {
				return false;
			}
		} else if (node2.getPrefix() == null) {
			if (node1.getPrefix() != null) {
				return false;
			}
		} else if (!node1.getPrefix().equals(node2.getPrefix())) {
			return false;
		}

		if (node1.getNodeValue() == null) {
			if (node2.getNodeValue() != null) {
				return false;
			}
		} else if (node2.getNodeValue() == null) {
			if (node1.getNodeValue() != null) {
				return false;
			}
		} else if (!node1.getNodeValue().equals(node2.getNodeValue())) {
			return false;
		}
		
		if (!node1.hasAttributes() && node2.hasAttributes() || node1.hasAttributes() && !node2.hasAttributes()) {
			return false;
		}
		else if (!isAttributeMapSame(node1.getAttributes(), node2.getAttributes())) {
			return false;
		}
		else if (node1.getNodeName().equals(node2.getNodeName())) {
			if (!isAttributeMapSame(node1.getAttributes(), node2.getAttributes())) {
				return false;
			}
			else if (node1.getChildNodes().getLength() <= 1 && node2.getChildNodes().getLength() <= 1
					&& !node1.getTextContent().equals(node2.getTextContent())) {
				node1.setTextContent(node2.getTextContent());
				return true;
			}
		}

		return true;
	}
	
	public boolean isAttributeMapSame(NamedNodeMap map1, NamedNodeMap map2) {
		if (map1 == map2)
			return true;

		if (!(map1 instanceof NamedNodeMap) || !(map2 instanceof NamedNodeMap))
			return false;
		if (map1.getLength() != map2.getLength())
			return false;

		for (int j = 0; j < map2.getLength(); j++) {
			Attr attributeMap2 = (Attr)map2.item(j);
			String attributeNameMap2 = attributeMap2.getName();
			String attributeValueMap2 = attributeMap2.getValue();
			boolean same = false;
			for (int i = 0; i < map1.getLength(); i++) {
				Attr attributeMap1 = (Attr)map1.item(i);
				String attributeNameMap1 = attributeMap1.getName();
				String attributeValueMap1 = attributeMap1.getValue();
				if (attributeNameMap1.equals(attributeNameMap2)) {
					if (attributeValueMap1.equals(attributeValueMap2)) {
						same = true;
						break;
					}
				}
			}
			if (!same) {
				return false;
			}
		}

		return true;
	}
	
	
	
	
	
	
	
	
	
	public Node specialMove() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException {
		Document xml7 = this.parse("path-to-file");
		// find all relevant nodes
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("/r/a");
		NodeList customers = (NodeList)expr.evaluate(xml7, XPathConstants.NODESET); 
		for (int i = 0; i < customers.getLength(); i++) {
			Node item = customers.item(i);
			//GET null!!! in here!!!
			NodeList childNodes = item.getChildNodes();
//			NamedNodeMap kAttributes = null;
//			NamedNodeMap sAttributes = null;
			
			Map<String, Node> childNodeMap = new HashMap<>();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					childNodeMap.put(childNodes.item(j).getNodeName(), childNodes.item(j));
				}
			}
			Node xNode = childNodeMap.get("x");
			String xAttribute = (xNode != null ? xNode.getTextContent() : "");
			Node yNode = childNodeMap.get("y");
			String yAttribute = (yNode != null ? yNode.getTextContent() : "");
			// for all relevant nodes: 
			// add group node with key corresponding to child node value of relevant node
			Element groupNode = xml7.createElement("groupNode" + (!xAttribute.isEmpty() ? "_" + xAttribute : xAttribute) 
					 + (!yAttribute.isEmpty() ? "_" + yAttribute : yAttribute));
			groupNode.setAttribute("xkey", xAttribute);
			groupNode.setAttribute("ykey", yAttribute);
			// add group node as sibling of relvant node
			item.getParentNode().insertBefore(groupNode, item.getNextSibling());
			
//			for (int j = 0; j < childNodes.getLength(); j++) {
//				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE && 
//						childNodes.item(j).getNodeName().equals("x")) {
//					kAttributes = childNodes.item(j).getAttributes();
//					if (kAttributes.getLength() > 0 && kAttributes.getNamedItem("key") != null) {
//						// for all relevant nodes: 
//						// add group node with key corresponding to child node value of relevant node
//						groupNode = xml7.createElement("groupNode_" + ((Attr)kAttributes.getNamedItem("key")).getTextContent());
//						groupNode.setAttribute("key", ((Attr)kAttributes.getNamedItem("key")).getTextContent());
//						// add group node as sibling of relvant node
//						item.getParentNode().insertBefore(groupNode, item.getNextSibling());
//					}
//					break;
//				}
//			}
			// move relevant node by adding relevant node as child node of group node
			if (groupNode != null) {
				groupNode.insertBefore(item, null);
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer1 = new StringWriter();
		StreamResult streamResult1 = new StreamResult(writer1);
		DOMSource dom = new DOMSource(xml7.getDocumentElement());

		transformer.transform(dom, streamResult1);
		System.out.println(writer1.toString());
		
		return xml7;
	}
	
	public void manipulateXmlMultiThreaded() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException, InterruptedException {
		Document xml = this.parse("path-to-file");
		// find all relevant nodes
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//*[not(*)]");
		NodeList customers = (NodeList)expr.evaluate(xml, XPathConstants.NODESET); 
		System.out.println(customers.getLength());
		double packages = Math.ceil((customers.getLength() + 1) / 1000);
		System.out.println(packages);
		long start = System.currentTimeMillis();
		
		// Single Threaded:
		for (int i = 0; i < customers.getLength(); i++) {
//			System.out.println(getXPath(customers.item(i)));
//			System.out.println(customers.item(i).getTextContent());
			customers.item(i).setTextContent("1");
//			System.out.println(customers.item(i).getTextContent());
		}
		
		// Multi Threaded:
//		List<List<Node>> nodePackages = new ArrayList<>();
//		ArrayList<Node> l = new ArrayList<>();
//		nodePackages.add(l);
//		for (int i = 0; i < customers.getLength(); i++) {
//			if (i % 5000 == 0) {
//				l = new ArrayList<>();
//				nodePackages.add(l);
//				l.add(customers.item(i));
//			} else {
//				l.add(customers.item(i));
//			}
//		}
//		CountDownLatch countDownLatch = new CountDownLatch(nodePackages.size());
//		for(List<Node> nodePackage : nodePackages) {
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					for (int i = 0; i < nodePackage.size(); i++) {
////						System.out.println(nodePackage.hashCode());
//						nodePackage.get(i).setTextContent("1");
//					}
//					countDownLatch.countDown();
//				}
//			}).start();
//		}
//		countDownLatch.await();
		
		// Ausgabe
//		TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		StringWriter writer1 = new StringWriter();
//		StreamResult streamResult1 = new StreamResult(writer1);
//		DOMSource dom = new DOMSource(xml.getDocumentElement());
//		transformer.transform(dom, streamResult1);
//		System.out.println(writer1.toString());
		
		// Zeitnahme
		System.out.println(System.currentTimeMillis() - start);
	}
	
	String getXPath(Node node) {
	    Node parent = node.getParentNode();
	    if (parent == null)
	    {
	        return "";
	    }
	    return getXPath(parent) + "/" + node.getNodeName();
	}

}