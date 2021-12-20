package io.github.attt.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author atpexgo
 */
public class JsonLexer {

    public List<JsonToken> lex(String jsonStr) {
        JsonString jsonString = new JsonString(jsonStr);
        List<JsonToken> tokens = new ArrayList<>();
        while (jsonString.size() > 0) {
            JsonToken stringToken = lexString(jsonString);
            if (stringToken != null) {
                tokens.add(stringToken);
            }
            JsonToken numberToken = lexNumber(jsonString);
            if (numberToken != null) {
                tokens.add(numberToken);
            }
            JsonToken boolToken = lexBool(jsonString);
            if (boolToken != null) {
                tokens.add(boolToken);
            }
            JsonToken nullToken = lexNull(jsonString);
            if (nullToken != null) {
                tokens.add(boolToken);
            }
            String nextStr = jsonString.stringAt(0);
            if (TokenType.WHITE_SPACES.findToken(nextStr)) {
                jsonString.cutAndUpdate(1);
            } else if (TokenType.LEFT_BRACKET.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.LEFT_BRACKET));
                jsonString.cutAndUpdate(1);
            } else if (TokenType.RIGHT_BRACKET.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.RIGHT_BRACKET));
                jsonString.cutAndUpdate(1);
            } else if (TokenType.LEFT_BRACE.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.LEFT_BRACE));
                jsonString.cutAndUpdate(1);
            } else if (TokenType.RIGHT_BRACE.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.RIGHT_BRACE));
                jsonString.cutAndUpdate(1);
            } else if (TokenType.COMMA.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.COMMA));
                jsonString.cutAndUpdate(1);
            } else if (TokenType.COLON.findToken(nextStr)) {
                tokens.add(new JsonToken(String.valueOf(nextStr), TokenType.COLON));
                jsonString.cutAndUpdate(1);
            } else {
                throw new RuntimeException(String.format("Unexpected character: %s", nextStr));
            }
        }
        return tokens;
    }

    private JsonToken lexString(JsonString jsonStr) {
        if (!TokenType.JSON_QUOTE.findToken(jsonStr.cutTo(1))) {
            return null;
        }
        jsonStr.cutAndUpdate(1);
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (TokenType.JSON_QUOTE.findToken(next)) {
                jsonStr.cutAndUpdate(1);
                return new JsonToken(stringBuilder.toString(), TokenType.STRING);
            }
            stringBuilder.append(next);
            jsonStr.cutAndUpdate(1);
        }
        throw new RuntimeException("Expected end-of-string quote");
    }

    private JsonToken lexNumber(JsonString jsonStr) {
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (TokenType.NUMBER.findToken(next)) {
                stringBuilder.append(next);
            } else {
                break;
            }
            jsonStr.cutAndUpdate(1);
        }
        if (stringBuilder.length() == 0) return null;
        String numberString = stringBuilder.toString();
        if (numberString.contains(".")) {
            return new JsonToken(Double.valueOf(numberString), TokenType.NUMBER);
        } else {
            return new JsonToken(Long.valueOf(numberString), TokenType.NUMBER);
        }
    }

    private JsonToken lexBool(JsonString jsonStr) {
        for (String token : TokenType.BOOL.getTokens()) {
            if (jsonStr.size() > token.length() && token.equals(jsonStr.cutTo(token.length()))) {
                jsonStr.cutAndUpdate(token.length());
                return new JsonToken(Boolean.valueOf(token), TokenType.BOOL);
            }
        }
        return null;
    }

    private JsonToken lexNull(JsonString jsonStr) {
        for (String token : TokenType.NULL.getTokens()) {
            if (jsonStr.size() > token.length() && token.equals(jsonStr.cutTo(token.length()))) {
                jsonStr.cutAndUpdate(token.length());
                return new JsonToken(null, TokenType.NULL);
            }
        }
        return null;
    }
}
