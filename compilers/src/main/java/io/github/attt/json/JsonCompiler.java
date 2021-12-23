package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class JsonCompiler {

    public static Object compile(String jsonStr) {
        JsonLexer lexer = new JsonLexer();
        JsonToken tokens = lexer.lex(jsonStr);
        JsonParser parser = new JsonParser();
        return parser.parse(tokens);
    }

}
