package io.github.attt.json;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author atpexgo
 */
public final class JsonLexer {

    public LexisList lex(String jsonStr) {
        DynamicString jsonString = new DynamicString(jsonStr);
        List<Lexis> lexis = new ArrayList<>();
        while (jsonString.length() > 0) {
            Lexis stringLexis = lexString(jsonString);
            if (stringLexis != null) {
                lexis.add(stringLexis);
            }
            Lexis numberLexis = lexNumber(jsonString);
            if (numberLexis != null) {
                lexis.add(numberLexis);
            }
            Lexis boolLexis = lexBool(jsonString);
            if (boolLexis != null) {
                lexis.add(boolLexis);
            }
            Lexis nullLexis = lexNull(jsonString);
            if (nullLexis != null) {
                lexis.add(boolLexis);
            }
            String nextStr = jsonString.getInitialString();
            Syntax syntax;
            if (LexerUtil.WHITE_SPACES.contains(nextStr)) {
                jsonString.slice(1);
            } else if ((syntax = LexerUtil.testAll(nextStr)) != null) {
                lexis.add(new Lexis(nextStr, syntax.getLexisType()));
                jsonString.slice(1);
            } else {
                throw new RuntimeException(String.format("Unexpected character: %s", nextStr));
            }
        }
        return new LexisList(lexis);
    }

    private Lexis lexString(DynamicString jsonStr) {
        Syntax initialSyntax;
        String initialString = jsonStr.getInitialString();
        if (LexerUtil.test(initialString, Syntax.APOSTROPHE)) {
            initialSyntax = Syntax.APOSTROPHE;
        } else if (LexerUtil.test(initialString, Syntax.DOUBLE_QUOTES)) {
            initialSyntax = Syntax.DOUBLE_QUOTES;
        } else {
            return null;
        }
        jsonStr.slice(1);
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (LexerUtil.test(next, initialSyntax)) {
                jsonStr.slice(next.length());
                return new Lexis(stringBuilder.toString(), LexisType.STRING);
            }
            stringBuilder.append(next);
            jsonStr.slice(next.length());
        }
        throw new RuntimeException("Expected end-of-string quote");
    }

    private Lexis lexNumber(DynamicString jsonStr) {
        Iterator<String> iterator = jsonStr.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!LexerUtil.NUMBERS.contains(next)) {
                break;
            }
            stringBuilder.append(next);
            jsonStr.slice(1);
        }
        if (stringBuilder.length() == 0) return null;
        String numberString = stringBuilder.toString();
        // TODO: 2021/12/23 set scale
        if (numberString.contains("e") || numberString.contains("E")) {
            return new Lexis(new BigDecimal(numberString), LexisType.NUMBER);
        }
        if (numberString.contains(".")) {
            return new Lexis(Double.valueOf(numberString), LexisType.NUMBER);
        } else {
            return new Lexis(Long.valueOf(numberString), LexisType.NUMBER);
        }
    }

    private Lexis lexBool(DynamicString jsonStr) {
        String bool = LexerUtil.testBool(jsonStr.getString());
        if (bool != null) {
            jsonStr.slice(bool.length());
            return new Lexis(Boolean.valueOf(bool), LexisType.BOOL);
        }
        return null;
    }

    private Lexis lexNull(DynamicString jsonStr) {
        String _null = LexerUtil.testNull(jsonStr.getString());
        if (_null != null) { // null is not null & not null is null
            jsonStr.slice(_null.length());
            return new Lexis(null, LexisType.NULL);
        }
        return null;
    }


}
