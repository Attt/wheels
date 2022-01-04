package io.github.attt.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * <strong>Warning</strong>: {@link #put(String, Object)} is not thread safe
 *
 * @author atpexgo
 */
public class JsonObj implements Json {

    private final JsonObjEle[] elements = (JsonObjEle[]) Array.newInstance(JsonObjEle.class, 16);

    public void put(String key, Object value) {
        JsonObjEle newEle = new JsonObjEle();
        newEle.key = key;
        newEle.value = value;
        int offset = hash(key) % elements.length;
        JsonObjEle ele = elements[offset];
        if (ele != null) {
            while (ele != null) {
                if (ele.key.equals(key)) {
                    ele.value = value;
                    return;
                }
                ele = ele.next;
            }
            newEle.next = elements[offset];
        }
        elements[offset] = newEle;
    }

    public <V> V get(String key, Class<V> vType) {
        int offset = hash(key) % elements.length;
        JsonObjEle ele = elements[offset];
        while (ele != null) {
            if (ele.key.equals(key)) {
                return vType.cast(ele.value);
            }
            ele = ele.next;
        }
        return null;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode());
    }

    public static class JsonObjEle {
        String key;
        Object value;
        JsonObjEle next;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "'" + key + "':" + (value instanceof String ? "'" + value + "'" : value);
        }
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public List<Object> scan(String keyRegex, boolean deepScan) {
        List<Object> result = new ArrayList<>();
        for (JsonObjEle element : elements) {
            if (element != null) {
                if (isKeyMatch(keyRegex, element.key)) result.add(element.value);
                if (deepScan && element.value instanceof Json)
                    result.addAll(((Json) element.value).scan(keyRegex, deepScan));
                while (element.next != null) {
                    element = element.next;
                    if (isKeyMatch(keyRegex, element.key)) result.add(element.value);
                    if (deepScan && element.value instanceof Json)
                        result.addAll(((Json) element.value).scan(keyRegex, deepScan));
                }
            }
        }
        return result;
    }

    private boolean isKeyMatch(String keyRegex, String target) {
        keyRegex = keyRegex.replaceAll("\\*", "(.*)");
        Pattern pattern = Pattern.compile(keyRegex);
        return pattern.matcher(target).matches();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        for (JsonObjEle element : elements) {
            if (element != null) {
                joiner.add(element.toString());
            }
        }
        return joiner.toString();
    }
}
