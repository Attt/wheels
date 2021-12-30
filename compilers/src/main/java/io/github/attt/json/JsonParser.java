package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class JsonParser {

    public Object parse(LexisList lexisList) {
        if (lexisList == null || lexisList.size() == 0) return null;
        Lexis lexis = lexisList.getAndForward();
        if (lexis.getType() == LexisType.LEFT_BRACKET) {
            return parseArray(lexisList);
        } else if (lexis.getType() == LexisType.LEFT_BRACE) {
            return parseObj(lexisList);
        } else {
            return lexis.getValue();
        }
    }

    public JsonObj parseObj(LexisList lexisList) {
        JsonObj jsonObj = new JsonObj();
        Lexis lexis = lexisList.get();
        if (lexis.getType() == LexisType.RIGHT_BRACE) {
            return jsonObj;
        }
        while (true) {
            Lexis stringKey = lexisList.get();
            if (stringKey.getType() != LexisType.STRING || !(stringKey.getValue() instanceof String)) {
                throw new RuntimeException(String.format("Expected string key, got: %s", stringKey.getValue()));
            }
            lexisList.forward();
            Lexis colon = lexisList.get();
            if (colon.getType() != LexisType.COLON) {
                throw new RuntimeException(String.format("Expected colon after key in object, got: %s", colon.getValue()));
            }
            lexisList.forward();
            Object value = parse(lexisList);
            jsonObj.put(String.valueOf(stringKey.getValue()), value);
            Lexis rightBrace = lexisList.get();
            if (rightBrace.getType() == LexisType.RIGHT_BRACE) {
                lexisList.forward();
                return jsonObj;
            } else if (rightBrace.getType() != LexisType.COMMA) {
                throw new RuntimeException(String.format("Expected comma after pair in object, got: %s", rightBrace.getValue()));
            }
            lexisList.forward();
        }
    }

    public JsonArray parseArray(LexisList lexisList) {
        JsonArray jsonArray = new JsonArray();
        Lexis lexis = lexisList.get();
        if (lexis.getType() == LexisType.RIGHT_BRACKET) {
            return jsonArray;
        }
        while (true) {
            Object json = parse(lexisList);
            jsonArray.add(json);
            lexis = lexisList.get();
            if (lexis.getType() == LexisType.RIGHT_BRACKET) {
                lexisList.forward();
                return jsonArray;
            } else if (lexis.getType() != LexisType.COMMA) {
                throw new RuntimeException("Expected comma after object in array");
            }
            lexisList.forward();
        }
    }
}
