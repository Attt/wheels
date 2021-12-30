package io.github.attt.json;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author atpexgo
 */
public final class DynamicString implements Iterable<Character> {

    private final char[] value;

    private int offset = 0;

    private int count;

    public DynamicString(String string) {
        this.value = Arrays.copyOf(string.toCharArray(), string.toCharArray().length);
        this.count = this.value.length;
    }

    public String getString(int from, int to) {
        if (to <= from) throw new IllegalArgumentException("'to' is smaller than 'from'");
        int tmpCount = to - from;
        int tmpOffset = offset + from;
        if (tmpOffset + tmpCount > value.length) throw new StringIndexOutOfBoundsException();
        return String.valueOf(value, tmpOffset, tmpCount);
    }

    public char getChar(int offset) {
        int tmpOffset = offset + this.offset;
        if (tmpOffset >= value.length) throw new StringIndexOutOfBoundsException();
        return charAt(tmpOffset);
    }

    public void slice(int from) {
        count = count - from;
        offset += from;
        checkBound();
    }

    public void slice(int from, int to) {
        if (to <= from) throw new IllegalArgumentException("'to' is smaller than 'from'");
        count = to - from;
        offset += from;
        checkBound();
    }

    public int length() {
        return count;
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {

            private int idx = offset;

            private final int bound = offset + count;

            @Override
            public boolean hasNext() {
                return idx < bound;
            }

            @Override
            public Character next() {
                Character next = charAt(idx);
                idx++;
                return next;
            }
        };
    }

    private char charAt(int idx) {
        if (idx >= value.length) throw new ArrayIndexOutOfBoundsException();
        return value[idx];
    }

    @Override
    public String toString() {
        return String.valueOf(value, offset, count);
    }

    private void checkBound() {
        if (offset > value.length || offset + count > value.length) throw new StringIndexOutOfBoundsException();
    }

}
