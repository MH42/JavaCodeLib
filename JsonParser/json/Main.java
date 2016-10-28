package json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	private static List<Data> list = new ArrayList<Data>();
	private static Main main;
	private static Node root;
	private static String identation = "   ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main = new Main();
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
						"}"+
						"}"+
						"}"+
						"}";
		JSONObject jsonObject = new JSONObject(json);
		parse(jsonObject, null);
		
		System.out.println("\n\nTREE:");
		traverseNodes(root, 0);
	}

	private static void parse(JSONObject jsonObject, Node node) {
		final Iterator<String> keys = jsonObject.keys();
		while(keys.hasNext()) {
			final String next = keys.next();
			System.out.println(next);
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
			final Node node = main.new Node(next);
			if(parent == null) {
				root = node;
			}
			else {
				parent.children.add(node);
			}
			parse((JSONObject) o, node);
		}
		else if(o instanceof JSONArray) {
			parse((JSONArray) o, parent);
		}
		else if(o instanceof String) {
			String value = (String) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value);
			parent.dataList.add(data);
			list.add(data);
		}
		else if(o instanceof Integer) {
			final Integer value = (Integer) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value.toString());
			parent.dataList.add(data);
			list.add(data);
		}
		else if(o instanceof Double) {
			final Double value = (Double) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = main.new Data(next, value.toString());
			parent.dataList.add(data);
			list.add(data);
		}
		else {
			System.out.println("Whatever");
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

		public Data(String next, String value2) {
			this.key = next;
			this.value = value2;
		}
	}
	
	private class Node {
		private String label;
		private List<Data> dataList = new ArrayList<Data>();
		private List<Node> children = new ArrayList<Node>();
		public Node(String next) {
			this.label = next;
		}
	}
}
