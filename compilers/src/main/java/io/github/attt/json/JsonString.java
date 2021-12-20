package io.github.attt.json;

import java.util.Iterator;

/**
 * @author atpexgo
 */
public class JsonString implements Iterable<String> {

    private String string;

    private int size;

    public JsonString(String string) {
        this.string = string;
        this.size = this.string.length();
    }

    public String getString() {
        return string;
    }

    public String stringAt(int idx) {
        return stringAt(string, idx);
    }

    private String stringAt(String str, int idx) {
        if (idx >= str.length()) return str;
        return String.valueOf(str.charAt(idx));
    }

    public String cut(int from) {
        return string.substring(from);
    }

    public String cut(int from, int end) {
        return string.substring(from, end);
    }

    public String cutTo(int end) {
        return string.substring(0, end);
    }

    public String cutAndUpdate(int from) {
        string = string.substring(from);
        size = string.length();
        return string;
    }

    public String cutAndUpdateTo(int end) {
        string = string.substring(0, end);
        size = string.length();
        return string;
    }

    public String cutAndUpdate(int from, int end) {
        string = string.substring(from, end);
        size = string.length();
        return string;
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

}
