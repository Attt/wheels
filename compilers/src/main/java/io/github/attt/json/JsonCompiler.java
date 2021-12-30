package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class JsonCompiler {

    public static Object compile(String jsonStr) {
        JsonLexer lexer = new JsonLexer();
        LexisList lexisList = lexer.lex(jsonStr);
        JsonParser parser = new JsonParser();
        return parser.parse(lexisList);
    }

    public static JsonObj compileObject(String jsonStr) {
        Object object = compile(jsonStr);
        return (JsonObj) object;
    }

    public static JsonArray compileArray(String jsonStr) {
        Object object = compile(jsonStr);
        return (JsonArray) object;
    }
}
