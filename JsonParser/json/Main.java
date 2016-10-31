package json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	private static List<Data> list = new ArrayList<Data>();
	private static Main main;
	private Node root, topNode;
//	private static Node tmp;
	private static String identation = "   ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main = new Main();
		main.root = main.new Node("root");
		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, "a2": "b2"}}, "abc": "x", "def": "y"}
		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, "a2": "b2"}, "a3": "b3"}, "abc": "x", "def": "y"}
		String json = "{" +
						"\"glossary\": {" +
						"\"title\": \"example glossary\"," +
						"\"GlossDiv\": {" +
						"\"title\": \"S\"," +
						"\"GlossList\": {" +
						"\"GlossEntry\": {" +
						"\"ID\": \"SGML\"," +
						"\"SortAs\": \"SGML\"," +
						"\"GlossTerm\": \"Standard Generalized Markup Language\"," +
						"\"Acronym\": \"SGML\"," +
						"\"Abbrev\": \"ISO 8879:1986\"," +
						"\"GlossDef\": {" +
						"\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\"," +
						"\"GlossSeeAlso\": [\"GML\", \"XML\"]" +
						"}," +
						"\"GlossSee\": \"markup\"" +
						"}"+
						"},"+
						"\"a2\": \"b2\"" +
						"},"+
						"\"a3\": \"b3\"" +
						"}," +
						"\"abc\": \"x\","+
						"\"def\": \"y\""+
						"}";
		JSONObject jsonObject = new JSONObject(json);
		parse(jsonObject, null);
		
		System.out.println("\n\nTREE:");
		traverseNodes(main.root, 0);
	}

	private static void parse(JSONObject jsonObject, Node node) {
		final Iterator<String> keys = jsonObject.keys();
		while(keys.hasNext()) {
			final String next = keys.next();
			System.out.println("next: "+next);
			final Object object = jsonObject.get(next);
			checkType(object, next, node);
		}
		
	}

	private static void parse(JSONArray jsonArray, Node node) {
		for(int i = 0; i < jsonArray.length(); i++) {
			checkType(jsonArray.get(i), null, node);
		}
	}
	
	private static void checkType(Object o, String next, Node parent) {
		if(o instanceof JSONObject) {
			final Node node = createNewNode(next, parent);
//			if(tmp != null) {
//				node.children.addAll(tmp.children);
//				node.dataList.addAll(tmp.dataList);
//				tmp = null;
//			}
			parse((JSONObject) o, node);
		}
		else if(o instanceof JSONArray) {
			final Node node = createNewNode(next, parent);
			parse((JSONArray) o, node);
		}
		else if(o instanceof String) {
			String value = (String) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value);
			addToDataList(parent, data);
			list.add(data);
		}
		else if(o instanceof Integer) {
			final Integer value = (Integer) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value.toString());
			addToDataList(parent, data);
			list.add(data);
		}
		else if(o instanceof Double) {
			final Double value = (Double) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value.toString());
			addToDataList(parent, data);
			list.add(data);
		}
		else {
			System.out.println("Whatever");
		}
	}

	private static Node createNewNode(String next, Node parent) {
		final Node node = main.new Node(next);
		if(parent == null) {
			main.root.children.add(node);
		}
		else {
			parent.children.add(node);
		}
		return node;
	}

	private static void addToDataList(Node parent, Data data) {
		if(parent == null) {
//			if(tmp == null) {
//				tmp = main.new Node();
//			}
//			tmp.dataList.add(data);
			Node node = main.new Node("Unknown");
			node.dataList.add(data);
			main.root.children.add(node);
		}
		else {
			parent.dataList.add(data);
		}
	}
	
	private static void traverseNodes(Node node, int identationCount) {
		String ident = createIdentation(identationCount);
		System.out.println(ident+"Label: "+node.label);
		System.out.println(ident+"Data:");
		for(Data d : node.dataList) {
			System.out.println(ident+"Key: "+d.key);
			System.out.println(ident+"Value: "+d.value);
		}
		for(Node child : node.children) {
			traverseNodes(child, identationCount+1);
		}
	}

	private static String createIdentation(int identationCount) {
		String identation = "";
		for(int i = 0; i < identationCount; i++) {
			identation += Main.identation;
		}
		return identation;
	}

	private class Data {
		private String key, value;

		public Data(String next, String value) {
			this.key = next;
			this.value = value;
		}
	}
	
	private class Node {
		private String label;
		private List<Data> dataList = new ArrayList<Data>();
		private List<Node> children = new ArrayList<Node>();
		
//		public Node() {}
		public Node(String next) {
			this.label = next;
		}
	}
}



//{
//   "glossary":{
//      "title":"example glossary",
//      "GlossDiv":{
//         "title":"S",
//         "GlossList":{
//            "GlossEntry":{
//               "ID":"SGML",
//               "SortAs":"SGML",
//               "GlossTerm":"Standard Generalized Markup Language",
//               "Acronym":"SGML",
//               "Abbrev":"ISO 8879:1986",
//               "GlossDef":{
//                  "para":"A meta-markup language, used to create markup languages such as DocBook.",
//                  "GlossSeeAlso":[
//                     "GML",
//                     "XML"
//                  ]
//               },
//               "GlossSee":"markup"
//            }
//         }
//      }
//   },
//   "abc":"x",
//   "def":"y"
//}

//{
//   "glossary":{
//      "title":"example glossary",
//      "GlossDiv":{
//         "title":"S",
//         "GlossList":{
//            "GlossEntry":{
//               "ID":"SGML",
//               "SortAs":"SGML",
//               "GlossTerm":"Standard Generalized Markup Language",
//               "Acronym":"SGML",
//               "Abbrev":"ISO 8879:1986",
//               "GlossDef":{
//                  "para":"A meta-markup language, used to create markup languages such as DocBook.",
//                  "GlossSeeAlso":[
//                     "GML",
//                     "XML"
//                  ]
//               },
//               "GlossSee":"markup"
//            }
//         },
//         "a2":"b2"
//      },
//		"a3":"b3"
//   },
//   "abc":"x",
//   "def":"y"
//}

















