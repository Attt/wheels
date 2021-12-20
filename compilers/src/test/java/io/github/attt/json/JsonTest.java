package io.github.attt.json;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author atpexgo
 */
public class JsonTest {

    @Test
    public void TestLexer() {
        JsonLexer lexer = new JsonLexer();
        List<JsonToken> tokens = lexer.lex("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}]}");
        System.out.println(tokens);
    }


    @Test
    public void TestParser() {
        JsonLexer lexer = new JsonLexer();
        List<JsonToken> tokens = lexer.lex("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}]}");
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(tokens);
        System.out.println(obj);
    }

    @Test
    public void TestEditionAfterParsing() {
        Object obj = JsonCompiler.compile("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}], 'e':[1,2,3,4]}");
        JsonObj jsonObj = ((JsonObj) obj);
        jsonObj.put("newKey111", "hey!!!!");
        System.out.println(obj);
        JsonArray jsonArray = jsonObj.get("a", JsonArray.class);
        JsonObj newObj = new JsonObj();
        newObj.put("newObjKey", null);
        jsonArray.add(newObj);
        System.out.println(obj);
    }
}
