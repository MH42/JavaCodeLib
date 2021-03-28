package filter;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AddNodeFilterCriterion extends StructuralFilterCriterion {
	private String nodeName;
	private String xPath;

	public AddNodeFilterCriterion(String nodeName, String xPath) {
		this.nodeName = nodeName;
		this.xPath = xPath;
	}

	@Override
	public Document execute(Document node) {
		try {
			// find all relevant nodes
			XPath xpath = XPathFactory.newInstance().newXPath();
//			XPathExpression expr = xpath.compile("//*[not(*)]");
			XPathExpression expr = xpath.compile(xPath);
			NodeList customers = (NodeList)expr.evaluate(node, XPathConstants.NODESET); 
			for (int i = 0; i < customers.getLength(); i++) {
				Element element = node.createElement(nodeName);
				customers.item(i).appendChild(element);
			}
		} catch (XPathExpressionException | DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	public Document restore(Document node) {
		try {
			// find all relevant nodes
			XPath xpath = XPathFactory.newInstance().newXPath();
//			XPathExpression expr = xpath.compile("//*[not(*)]");
			XPathExpression expr = xpath.compile("//" + nodeName);
			NodeList customers = (NodeList)expr.evaluate(node, XPathConstants.NODESET); 
			for (int i = 0; i < customers.getLength(); i++) {
				customers.item(i).getParentNode().removeChild(customers.item(i));
			}
		} catch (XPathExpressionException | DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
}
