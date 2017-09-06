package xmlStringParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><verzeichnis><titel>Wikipedia Städteverzeichnis</titel><eintrag name=\"abc\"><stichwort>Genf</stichwort><eintragstext>Genf ist der Sitz von ...</eintragstext></eintrag><eintrag><stichwort>Köln</stichwort><eintragstext>Köln ist eine Stadt, die ...</eintragstext></eintrag></verzeichnis>";
		System.out.println(s);
		Main2 main = new Main2();
		try {
			Document doc = main.loadXMLFromString(s);
			final NodeList elements = doc.getElementsByTagName("eintrag");
			for(int i = 0; i < elements.getLength(); i++) {
				System.out.println("NodeName "+elements.item(i).getNodeName());
				System.out.println(elements.item(i).getAttributes().getLength() > 0 ? "NodeAttribute "+elements.item(i).getAttributes().item(0).getNodeValue() : "-");
				System.out.println("TextContent "+elements.item(i).getFirstChild().getTextContent());
			}
			
			final NodeList childNodes = doc.getChildNodes();
			Map<String, String> map = new HashMap<String, String>();
			main.collectTreeContent(map, childNodes);
			for(int i = 0; i < childNodes.getLength(); i++) {
				System.out.println("NodeName2 "+childNodes.item(i).getNodeName());
				System.out.println("NodeValue "+childNodes.item(i).getNodeValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void collectTreeContent(Map<String, String> map, NodeList childNodes) {
		if(childNodes.getLength() > 0) {
			for(int i = 0; i < childNodes.getLength(); i++) {
				System.out.println("NodeName3 "+childNodes.item(i).getNodeName());
				System.out.println("TextContent3 "+childNodes.item(i).getFirstChild().getTextContent());
			}
		}
	}

	public Document loadXMLFromString(String xml) throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}

}
