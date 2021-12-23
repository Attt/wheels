package io.github.attt.json;

import java.util.List;

/**
 * @author atpexgo
 */
public final class JsonToken {

    private final List<Token> tokens;

    private int cursor;

    public JsonToken(List<Token> tokens) {
        this.tokens = tokens;
        reset();
    }

    public void forward() {
        cursor++;
    }

    public void reset() {
        cursor = 0;
    }

    public int size() {
        return tokens.size();
    }

    public Token get() {
        if (cursor >= tokens.size()) throw new RuntimeException("Unexpected end-of-json");
        return tokens.get(cursor);
    }

    @Override
    public String toString() {
        return "JsonToken{" +
                "tokens=" + tokens +
                ", cursor=" + cursor +
                '}';
    }
}
