package io.github.attt.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author atpexgo
 */
public final class JsonLexer {

    public JsonToken lex(String jsonStr) {
        JsonString jsonString = new JsonString(jsonStr);
        List<Token> tokens = new ArrayList<>();
        while (jsonString.size() > 0) {
            Token stringToken = lexString(jsonString);
            if (stringToken != null) {
                tokens.add(stringToken);
            }
            Token numberToken = lexNumber(jsonString);
            if (numberToken != null) {
                tokens.add(numberToken);
            }
            Token boolToken = lexBool(jsonString);
            if (boolToken != null) {
                tokens.add(boolToken);
            }
            Token nullToken = lexNull(jsonString);
            if (nullToken != null) {
                tokens.add(boolToken);
            }
            String nextStr = jsonString.initialString();
            Syntax syntax;
            if (Syntax.WHITE_SPACES.test(nextStr) != null) {
                jsonString.sliceFrom(1);
            } else if ((syntax = Syntax.testAndGetJsonSyntax(nextStr)) != null) {
                tokens.add(new Token(String.valueOf(nextStr), syntax));
                jsonString.sliceFrom(1);
            } else {
                throw new RuntimeException(String.format("Unexpected character: %s", nextStr));
            }
        }
        return new JsonToken(tokens);
    }

    private Token lexString(JsonString jsonStr) {
        String initialToken;
        if ((initialToken = Syntax.APOSTROPHE.test(jsonStr.initialString())) == null
                && (initialToken = Syntax.DOUBLE_QUOTES.test(jsonStr.initialString())) == null) return null;
        jsonStr.sliceFrom(initialToken.length());
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (initialToken.equals(next)) {
                jsonStr.sliceFrom(next.length());
                return new Token(stringBuilder.toString(), Syntax.STRING);
            }
            stringBuilder.append(next);
            jsonStr.sliceFrom(next.length());
        }
        throw new RuntimeException("Expected end-of-string quote");
    }

    private Token lexNumber(JsonString jsonStr) {
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (Syntax.NUMBER.test(next) == null) {
                break;
            }
            stringBuilder.append(next);
            jsonStr.sliceFrom(1);
        }
        if (stringBuilder.length() == 0) return null;
        String numberString = stringBuilder.toString();
        // TODO: 2021/12/23 set scale
        if (numberString.contains("e") || numberString.contains("E")) {
            return new Token(new BigDecimal(numberString), Syntax.NUMBER);
        }
        if (numberString.contains(".")) {
            return new Token(Double.valueOf(numberString), Syntax.NUMBER);
        } else {
            return new Token(Long.valueOf(numberString), Syntax.NUMBER);
        }
    }

    private Token lexBool(JsonString jsonStr) {
        String syntax = Syntax.BOOL.test(jsonStr.getString());
        if (syntax != null) {
            jsonStr.sliceFrom(syntax.length());
            return new Token(Boolean.valueOf(syntax), Syntax.BOOL);
        }
        return null;
    }

    private Token lexNull(JsonString jsonStr) {
        String syntax = Syntax.NULL.test(jsonStr.getString());
        if (syntax != null) { // null is not null & not null is null
            jsonStr.sliceFrom(syntax.length());
            return new Token(null, Syntax.NULL);
        }
        return null;
    }

}
