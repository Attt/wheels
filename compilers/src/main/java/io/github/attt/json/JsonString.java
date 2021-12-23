package io.github.attt.json;

import java.util.Iterator;

/**
 * @author atpexgo
 */
public final class JsonString implements Iterable<String> {

    private String string;

    private int size;

    public JsonString(String string) {
        this.string = string;
        this.size = this.string.length();
    }

    public String getString() {
        return string;
    }

    public String initialString() {
        return stringAt(string, 0);
    }

    public void sliceFrom(int from) {
        string = string.substring(from);
        size = string.length();
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            private int idx = -1;

            private final String stringCopy = String.copyValueOf(string.toCharArray());

            @Override
            public boolean hasNext() {
                return idx < stringCopy.length();
            }

            @Override
            public String next() {
                idx++;
                return stringAt(stringCopy, idx);
            }
        };
    }

    private String stringAt(String str, int idx) {
        if (idx >= str.length()) return str;
        return String.valueOf(str.charAt(idx));
    }
}
