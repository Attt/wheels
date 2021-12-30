package io.github.attt.json;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author atpexgo
 */
public final class DynamicString implements Iterable<String> {

    private final char[] string;

    private int offset = 0;

    private int count;

    public DynamicString(String string) {
        this.string = Arrays.copyOf(string.toCharArray(), string.toCharArray().length);
        this.count = this.string.length;
    }

    public String getString() {
        return String.valueOf(string, offset, count);
    }

    public String getString(int from, int to) {
        if (to <= from) throw new IllegalArgumentException("'to' is smaller than 'from'");
        int tmpCount = to - from;
        int tmpOffset = offset + from;
        if (tmpOffset + tmpCount > string.length) throw new StringIndexOutOfBoundsException();
        return String.valueOf(string, tmpOffset, tmpCount);
    }

    public String getInitialString() {
        return stringAt(offset);
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
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            private int idx = offset;

            private final int bound = offset + count;

            @Override
            public boolean hasNext() {
                return idx < bound;
            }

            @Override
            public String next() {
                String next = stringAt(idx);
                idx++;
                return next;
            }
        };
    }

    private String stringAt(int idx) {
        if (idx >= string.length) return "";
        return String.valueOf(string[idx]);
    }

    @Override
    public String toString() {
        return getString();
    }

    private void checkBound() {
        if (offset > string.length || offset + count > string.length) throw new StringIndexOutOfBoundsException();
    }

}
