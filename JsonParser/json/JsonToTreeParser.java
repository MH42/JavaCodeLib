package json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonToTreeParser {
	private Node root;
	private static final String LABEL_IDENTATION = "---";
	private static final String IDENTATION = "   ";

	public JsonToTreeParser() {
	}
	
	public Node parse(String json) {
		root = new Node("root");
		JSONObject jsonObject = new JSONObject(json);
		parse(jsonObject, null);
		return root;
	}
	
	public boolean validateJSON(String json) {
		JSONObject jsonObject = new JSONObject(json);
		try {
			JSONObject.testValidity(jsonObject);
			return true;
		}
		catch (JSONException e) {
			return false;
		}
	}

	private void parse(JSONObject jsonObject, Node node) {
		final Iterator<String> keys = jsonObject.keys();
		while(keys.hasNext()) {
			final String next = keys.next();
			System.out.println("next: "+next);
			final Object object = jsonObject.get(next);
			checkType(object, next, node);
		}
		
	}

	private void parse(JSONArray jsonArray, Node node) {
		for(int i = 0; i < jsonArray.length(); i++) {
			checkType(jsonArray.get(i), null, node);
		}
	}
	
	private void checkType(Object o, String next, Node parent) {
		if(o instanceof JSONObject) {
			final Node node = createNewNode(next, parent);
			parse((JSONObject) o, node);
		}
		else if(o instanceof JSONArray) {
			final Node node = createNewNode(next, parent);
			parse((JSONArray) o, node);
		}
		else if(o instanceof String) {
			String value = (String) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = new Data(next, value);
			addToDataList(parent, data);
		}
		else if(o instanceof Integer) {
			final Integer value = (Integer) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = new Data(next, value.toString());
			addToDataList(parent, data);
		}
		else if(o instanceof Double) {
			final Double value = (Double) o;
			System.out.println("Data-Entry: "+next+" "+value);
			Data data = new Data(next, value.toString());
			addToDataList(parent, data);
		}
		else {
			System.out.println("Whatever");
		}
	}

	private Node createNewNode(String next, Node parent) {
		final Node node = new Node(next);
		if(parent == null) {
			root.children.add(node);
		}
		else {
			parent.children.add(node);
		}
		return node;
	}

	private void addToDataList(Node parent, Data data) {
		if(parent == null) {
			Node node = new Node("Unknown");
			node.dataList.add(data);
			root.children.add(node);
		}
		else {
			parent.dataList.add(data);
		}
	}
	

	public String traverseNodes(Node tree) {
		return traverseNodes(tree, 0);
	}
	
	private String traverseNodes(Node node, int identationCount) {
		String labelIdent = createIdentation(identationCount, LABEL_IDENTATION);
		String ident = createIdentation(identationCount, IDENTATION);
		String treeStructure = "";
		final String label = "+"+labelIdent+"Label: "+node.label;
		System.out.println(label);
		treeStructure += label;
//		System.out.println(ident+"Data:");
		for(Data d : node.dataList) {
			final String key = "|"+ident+"- Key: "+d.key;
			System.out.println(key);
			treeStructure += key;
			final String value = "|"+ident+"  Value: "+d.value;
			System.out.println(value);
			treeStructure += value;
		}
		for(Node child : node.children) {
			treeStructure += traverseNodes(child, identationCount+1);
		}
		return treeStructure;
	}

	private String createIdentation(int identationCount, String identationKind) {
		String identation = "";
		for(int i = 0; i < identationCount; i++) {
			identation += identationKind;
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
	
	// public modifier for usage in JUnit-Tests
	public class Node {
		private String label;
		private List<Data> dataList = new ArrayList<Data>();
		private List<Node> children = new ArrayList<Node>();
		
		public Node(String next) {
			this.label = next;
		}
	}
	
}
