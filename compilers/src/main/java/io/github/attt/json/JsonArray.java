package io.github.attt.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author atpexgo
 */
public class JsonArray implements Json {

    private final List<Object> array = new ArrayList<>();

    public Stream<Object> stream() {
        return array.stream();
    }

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
    public String toString() {
        return Arrays.deepToString(array.toArray());
    }
}