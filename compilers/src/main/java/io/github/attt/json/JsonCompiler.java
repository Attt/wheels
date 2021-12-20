package io.github.attt.json;

import java.util.List;

/**
 * @author atpexgo
 */
public class JsonCompiler {

    public static Object compile(String jsonStr) {
        JsonLexer lexer = new JsonLexer();
        List<JsonToken> jsonTokens = lexer.lex(jsonStr);
        JsonParser parser = new JsonParser();
        return parser.parse(jsonTokens);
    }

}
