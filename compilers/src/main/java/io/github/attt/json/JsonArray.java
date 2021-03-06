package io.github.attt.json;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author atpexgo
 */
public class JsonArray implements Json {

    private final List<Object> array = new ArrayList<>();

    public void add(Object obj) {
        array.add(obj);
    }

    public int size() {
        return array.size();
    }

    public <V> V get(int idx, Class<V> vType) {
        return vType.cast(array.get(idx));
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public List<Object> scan(String keyRegex, boolean deepScan) {
        List<Object> result = new ArrayList<>();
        for (Object o : array) {
            if (o instanceof Json) result.addAll(((Json) o).scan(keyRegex, deepScan));
        }
        return result;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (Object v : array) {
            joiner.add(v instanceof String ? "'" + v + "'" : v.toString());
        }
        return joiner.toString();
    }

}
