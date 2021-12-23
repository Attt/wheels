package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class JsonParser {

    public Object parse(JsonToken tokens) {
        if (tokens == null || tokens.size() == 0) return null;
        Token token = tokens.get();
        tokens.forward();
        if (token.getSyntax() == Syntax.LEFT_BRACKET) {
            return parseArray(tokens);
        } else if (token.getSyntax() == Syntax.LEFT_BRACE) {
            return parseObj(tokens);
        } else {
            return token.getValue();
        }
    }

    public JsonObj parseObj(JsonToken tokens) {
        JsonObj jsonObj = new JsonObj();
        Token token = tokens.get();
        if (token.getSyntax() == Syntax.RIGHT_BRACE) {
            return jsonObj;
        }
        while (true) {
            Token stringKey = tokens.get();
            if (stringKey.getSyntax() != Syntax.STRING || !(stringKey.getValue() instanceof String)) {
                throw new RuntimeException(String.format("Expected string key, got: %s", stringKey.getValue()));
            }
            tokens.forward();
            Token colon = tokens.get();
            if (colon.getSyntax() != Syntax.COLON) {
                throw new RuntimeException(String.format("Expected colon after key in object, got: %s", colon.getValue()));
            }
            tokens.forward();
            Object value = parse(tokens);
            jsonObj.put(String.valueOf(stringKey.getValue()), value);
            Token rightBrace = tokens.get();
            if (rightBrace.getSyntax() == Syntax.RIGHT_BRACE) {
                tokens.forward();
                return jsonObj;
            } else if (rightBrace.getSyntax() != Syntax.COMMA) {
                throw new RuntimeException(String.format("Expected comma after pair in object, got: %s", rightBrace.getValue()));
            }
            tokens.forward();
        }
    }

    public JsonArray parseArray(JsonToken tokens) {
        JsonArray jsonArray = new JsonArray();
        Token jsonToken = tokens.get();
        if (jsonToken.getSyntax() == Syntax.RIGHT_BRACKET) {
            return jsonArray;
        }
        while (true) {
            Object json = parse(tokens);
            jsonArray.add(json);
            Token token = tokens.get();
            if (token.getSyntax() == Syntax.RIGHT_BRACKET) {
                tokens.forward();
                return jsonArray;
            } else if (token.getSyntax() != Syntax.COMMA) {
                throw new RuntimeException("Expected comma after object in array");
            }
            tokens.forward();
        }
    }
}
