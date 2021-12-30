package io.github.attt.json;

import java.util.List;

/**
 * @author atpexgo
 */
public final class LexisList {

    private final List<Lexis> lexis;

    private int cursor;

    public LexisList(List<Lexis> lexis) {
        this.lexis = lexis;
        reset();
    }

    public void forward() {
        cursor++;
    }

    public void reset() {
        cursor = 0;
    }

    public int size() {
        return lexis.size();
    }

    public Lexis get() {
        if (cursor >= lexis.size()) throw new RuntimeException("Unexpected end-of-json");
        return lexis.get(cursor);
    }

    public Lexis getAndForward() {
        Lexis lexis = get();
        forward();
        return lexis;
    }

    @Override
    public String toString() {
        return "JsonLexis{" +
                "lexis=" + lexis +
                ", cursor=" + cursor +
                '}';
    }
}
