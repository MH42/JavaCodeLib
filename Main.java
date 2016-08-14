package xmlStringParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><library><booktitle>Pattern-Oriented Software Architecture: Volume 1</booktitle><author>Buschmann, Meunier, Rohnert, Sommerlad, Stal</author><booktitle>Pattern-Oriented Software Architecture: Volume 2</booktitle><author>Schmidt, Stal, Rohnert, Buschmann</author></library>";
		Main main = new Main();
		try {
			Document document = main.loadXMLFromString(s);
			final Map<String, String> map = main.collectElements(document, "booktitle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Document loadXMLFromString(String s) throws Exception {
	    final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    final InputSource inputSource = new InputSource(new StringReader(s));
	    return documentBuilder.parse(inputSource);
	}
	
	private Map<String, String> collectElements(Document document, String nodeName) {
		final NodeList elements = document.getElementsByTagName(nodeName);
		Map<String, String> m = new HashMap<String, String>();
		for(int i = 0; i < elements.getLength(); i++) {
			final Node node = elements.item(i);
			m.put(node.getNodeName(), node.getFirstChild().getTextContent());
			System.out.println(node.getNodeName());
			System.out.println(node.getFirstChild().getTextContent());
		}
		return m;
	}

}
