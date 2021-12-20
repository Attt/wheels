package io.github.attt.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author atpexgo
 */
public enum TokenType {

    STRING(""),
    NUMBER("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "e", "E", "-", "."),
    BOOL("true","false"),
    NULL("null"),
    COMMA(","),
    COLON(":"),
    WHITE_SPACES(" "),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    JSON_QUOTE("'", "\"");

    private Set<String> tokens;

    TokenType(String... tokens) {
        this.tokens = new HashSet<>();
        this.tokens.addAll(Arrays.asList(tokens));
    }

    public boolean findToken(String str){
        return tokens.contains(str);
    }

    public Set<String> getTokens() {
        return tokens;
    }
}
