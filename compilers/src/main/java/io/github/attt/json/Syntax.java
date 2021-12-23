package io.github.attt.json;

import java.util.Locale;
import java.util.function.Function;

/**
 * @author atpexgo
 */
public enum Syntax {

    STRING(false, str -> str),
    NUMBER(false, new Function<String, String>() {
        private final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "e", "E", "-", "."};

        @Override
        public String apply(String s) {
            if (s.length() != 0)
                for (String number : NUMBERS) {
                    if (s.equals(number)) return number;
                }
            return null;
        }
    }),
    BOOL(false, bool -> bool != null
            && ((bool.length() > 4 && (bool = bool.substring(0, 5).toLowerCase(Locale.ROOT)).equals("false")) || (bool.length() > 3 && (bool = bool.substring(0, 4).toLowerCase(Locale.ROOT)).equals("true"))) ? bool : null),
    NULL(false, _null -> _null.length() > 3 && _null.startsWith("null") ? "null" : null),
    COMMA(true, comma -> ",".equals(comma) ? "," : null),
    COLON(true, colon -> ":".equals(colon) ? ":" : null),
    WHITE_SPACES(false, new Function<String, String>() {
        private final String[] WHITE_SPACES = {"\t", "\r", "\n", " ", ""};

        @Override
        public String apply(String s) {
            for (String whiteSpace : WHITE_SPACES) {
                if (whiteSpace.equals(s)) return whiteSpace;
            }
            return null;
        }
    }),
    LEFT_BRACKET(true, leftBracket -> "[".equals(leftBracket) ? "[" : null),
    RIGHT_BRACKET(true, rightBracket -> "]".equals(rightBracket) ? "]" : null),
    LEFT_BRACE(true, leftBrace -> "{".equals(leftBrace) ? "{" : null),
    RIGHT_BRACE(true, rightBrace -> "}".equals(rightBrace) ? "}" : null),
    APOSTROPHE(false, apostrophe -> "'".equals(apostrophe) ? "'" : null),
    DOUBLE_QUOTES(false, doubleQuotes -> "\"".equals(doubleQuotes) ? "\"" : null);

    private final boolean isJsonSyntax;

    private final Function<String, String> predicate;

    Syntax(boolean isJsonSyntax, Function<String, String> predicate) {
        this.isJsonSyntax = isJsonSyntax;
        this.predicate = predicate;
    }

    public String test(String str) {
        return predicate.apply(str);
    }

    public boolean isJsonSyntax() {
        return isJsonSyntax;
    }

    public static Syntax testAndGetJsonSyntax(String str) {
        for (Syntax syntax : values()) {
            if (syntax.isJsonSyntax && syntax.test(str) != null) return syntax;
        }
        return null;
    }
}
