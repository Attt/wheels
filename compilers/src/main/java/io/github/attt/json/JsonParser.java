package io.github.attt.json;

import java.util.List;

/**
 * @author atpexgo
 */
public class JsonParser {

    int currentTokenIdx = 0;

    public Object parse(List<JsonToken> tokens) {
        if (tokens == null || tokens.size() == 0) return null;
        JsonToken jsonToken = tokens.get(currentTokenIdx);
        currentTokenIdx++;
        if (jsonToken.getType() == TokenType.LEFT_BRACKET) {
            return parseArray(tokens);
        } else if (jsonToken.getType() == TokenType.LEFT_BRACE) {
            return parseObj(tokens);
        } else {
            return jsonToken.getValue();
        }
    }

    public JsonObj parseObj(List<JsonToken> tokens) {
        JsonObj jsonObj = new JsonObj();
        JsonToken jsonToken = tokens.get(currentTokenIdx);
        if (jsonToken.getType() == TokenType.RIGHT_BRACE) {
            return jsonObj;
        }
        while (true) {
            JsonToken stringKey = tokens.get(currentTokenIdx);
            if (stringKey.getType() == TokenType.STRING && stringKey.getValue() instanceof String) {
                currentTokenIdx++;
            } else {
                throw new RuntimeException(String.format("Expected string key, got: %s", stringKey.getValue()));
            }
            JsonToken colon = tokens.get(currentTokenIdx);
            if (colon.getType() != TokenType.COLON) {
                throw new RuntimeException(String.format("Expected colon after key in object, got: %s", colon.getValue()));
            }
            currentTokenIdx++;
            Object value = parse(tokens);
            jsonObj.put(String.valueOf(stringKey.getValue()), value);
            JsonToken rightBrace = tokens.get(currentTokenIdx);
            if (rightBrace.getType() == TokenType.RIGHT_BRACE) {
                currentTokenIdx++;
                return jsonObj;
            } else if (rightBrace.getType() != TokenType.COMMA) {
                throw new RuntimeException(String.format("Expected comma after pair in object, got: %s", rightBrace.getValue()));
            }
            currentTokenIdx++;
        }
        //throw new RuntimeException("Expected end-of-object brace");
    }

    public JsonArray parseArray(List<JsonToken> tokens) {
        JsonArray jsonArray = new JsonArray();
        JsonToken jsonToken = tokens.get(currentTokenIdx);
        if (jsonToken.getType() == TokenType.RIGHT_BRACKET) {
            return jsonArray;
        }
        while (true) {
            Object json = parse(tokens);
            jsonArray.add(json);
            JsonToken token = tokens.get(currentTokenIdx);
            if (token.getType() == TokenType.RIGHT_BRACKET) {
                currentTokenIdx++;
                return jsonArray;
            } else if (token.getType() != TokenType.COMMA) {
                throw new RuntimeException("Expected comma after object in array");
            } else {
                currentTokenIdx++;
            }
        }
        //throw new RuntimeException("Expected end-of-array bracket");
    }
}
