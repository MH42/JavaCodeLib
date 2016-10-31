package json;

import static org.junit.Assert.*;
import json.JsonToTreeParser.Node;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonToTreeParserTest {
	private JsonToTreeParser jsonToTreeParser;

	@Before
	public void setUp() throws Exception {
		jsonToTreeParser = new JsonToTreeParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() {
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
		final Node tree = jsonToTreeParser.parse(json);
		final String actual = jsonToTreeParser.traverseNodes(tree);
		final String expected = "+Label: root+---Label: Unknown|   - Key: abc|     Value: x+---Label: glossary|   - Key: title|     Value: example glossary|   - Key: a3|     Value: b3+------Label: GlossDiv|      - Key: title|        Value: S|      - Key: a2|        Value: b2+---------Label: GlossList+------------Label: GlossEntry|            - Key: SortAs|              Value: SGML|            - Key: GlossSee|              Value: markup|            - Key: GlossTerm|              Value: Standard Generalized Markup Language|            - Key: ID|              Value: SGML|            - Key: Acronym|              Value: SGML|            - Key: Abbrev|              Value: ISO 8879:1986+---------------Label: GlossDef|               - Key: para|                 Value: A meta-markup language, used to create markup languages such as DocBook.+------------------Label: GlossSeeAlso|                  - Key: null|                    Value: GML|                  - Key: null|                    Value: XML+---Label: Unknown|   - Key: def|     Value: y";
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void testParse2() {
//		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, "a2": "b2"}}, "abc": "x", "def": "y"}
//		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, {{"a2": "b2"},{"c2": "d2"}}, {{"e2": "f2"},{"g2": "h2"}}}, "a3": "b3"}, "abc": "x", "def": "y"}
//		String json = "{" +
//				"\"glossary\": {" +
//				"\"title\": \"example glossary\"," +
//				"\"GlossDiv\": {" +
//				"\"title\": \"S\"," +
//				"\"GlossList\": {" +
//				"\"GlossEntry\": {" +
//				"\"ID\": \"SGML\"," +
//				"\"SortAs\": \"SGML\"," +
//				"\"GlossTerm\": \"Standard Generalized Markup Language\"," +
//				"\"Acronym\": \"SGML\"," +
//				"\"Abbrev\": \"ISO 8879:1986\"," +
//				"\"GlossDef\": {" +
//				"\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\"," +
//				"\"GlossSeeAlso\": [\"GML\", \"XML\"]" +
//				"}," +
//				"\"GlossSee\": \"markup\"" +
//				"}"+
//				"},"+
//				"{{\"a2\": \"b2\"}," +
//				"{\"c2\": \"d2\"}}," +
//				"{{\"e2\": \"f2\"}," +
//				"{\"g2\": \"h2\"}}" +
//				"},"+
//				"\"a3\": \"b3\"" +
//				"}," +
//				"\"abc\": \"x\","+
//				"\"def\": \"y\""+
//				"}";
//		final Node tree = jsonToTreeParser.parse(json);
//		final String actual = jsonToTreeParser.traverseNodes(tree);
//		final String expected = "+Label: root+---Label: Unknown|   - Key: abc|     Value: x+---Label: glossary|   - Key: title|     Value: example glossary|   - Key: a3|     Value: b3+------Label: GlossDiv|      - Key: title|        Value: S|      - Key: a2|        Value: b2+---------Label: GlossList+------------Label: GlossEntry|            - Key: SortAs|              Value: SGML|            - Key: GlossSee|              Value: markup|            - Key: GlossTerm|              Value: Standard Generalized Markup Language|            - Key: ID|              Value: SGML|            - Key: Acronym|              Value: SGML|            - Key: Abbrev|              Value: ISO 8879:1986+---------------Label: GlossDef|               - Key: para|                 Value: A meta-markup language, used to create markup languages such as DocBook.+------------------Label: GlossSeeAlso|                  - Key: null|                    Value: GML|                  - Key: null|                    Value: XML+---Label: Unknown|   - Key: def|     Value: y";
//		assertEquals(expected, actual);
//	}
	
	@Test(expected = JSONException.class)
	public void testValidateJSON() {
		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, "a2": "b2"}}, "abc": "x", "def": "y"}
		// {"glossary": {"title": "example glossary","GlossDiv": {"title": "S","GlossList": {"GlossEntry": {"ID": "SGML", "SortAs": "SGML", "GlossTerm": "Standard Generalized Markup Language", "Acronym": "SGML", "Abbrev": "ISO 8879:1986", "GlossDef": {"para": "A meta-markup language, used to create markup languages such as DocBook.", "GlossSeeAlso": ["GML", "XML"]}, "GlossSee": "markup"}}, "a2": "b2"}, "a3": "b3"}, "abc": "x", "def": "y"}
		String json = "{" +
				"\"glossary\": {" +
				"[\"X\", \"Y\"]," +
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
		assertFalse(jsonToTreeParser.validateJSON(json));
	}

}
