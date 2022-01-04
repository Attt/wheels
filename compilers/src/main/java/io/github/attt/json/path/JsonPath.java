package io.github.attt.json.path;

import io.github.attt.json.DynamicString;
import io.github.attt.json.Json;
import io.github.attt.json.LexerUtil;
import io.github.attt.json.Syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author atpexgo
 */
public class JsonPath {

    private List<Function<Object, List<Object>>> functions;

    private final Json json;

    public JsonPath(Json json) {
        this.json = json;
    }

    public Object compile(String path) {
        DynamicString dynamicString = new DynamicString(path);
        char initialChar = dynamicString.getChar(0);
        if (!LexerUtil.test(initialChar, Syntax.DOLLAR)) {
            throw new RuntimeException(String.format("Unexpected character found, got %s", initialChar));
        }
        StringBuilder scanKeyBuilder = new StringBuilder();
        dynamicString.slice(1);
        for (char ch : dynamicString) {
            if (LexerUtil.test(ch, Syntax.DOT)) {
                if (scanKeyBuilder.length() > 0) {
                    // resolve
                    resolveScanKey(scanKeyBuilder);
                    // start next level scanning
                    scanKeyBuilder.delete(0, scanKeyBuilder.length());
                }
            } else {
                scanKeyBuilder.append(ch);
            }
        }
        // resolve
        resolveScanKey(scanKeyBuilder);
        return resolve(json);
    }

    private void resolveScanKey(StringBuilder scanKeyBuilder) {
        // *a*b*c*
        boolean deepScan = LexerUtil.test(scanKeyBuilder.charAt(0), Syntax.DOT);
        scanWithFilter(deepScan ? scanKeyBuilder.substring(1) : scanKeyBuilder.toString(), deepScan, null);
    }

    private Object resolve(Json json) {
        if (functions == null || functions.size() == 0) {
            return json;
        }
        List<Object> inputs = new ArrayList<>();
        List<Object> outputs = new ArrayList<>();
        inputs.add(json);
        for (Function<Object, List<Object>> function : functions) {
            for (Object input : inputs) {
                List<Object> output = function.apply(input);
                if (output != null) outputs.addAll(output);
            }
            inputs = outputs;
            outputs = new ArrayList<>();
        }
        return inputs;
    }

    private void scanWithFilter(String key, boolean deepScan, Predicate<Object> predicate) {
        addFunction(o -> {
            List<Object> objects;
            if (o instanceof Json) objects = ((Json) o).scan(key, deepScan);
            else return null;
            return predicate == null ? objects : objects.stream().filter(predicate).collect(Collectors.toList());
        });
    }

    private void addFunction(Function<Object, List<Object>> function) {
        if (functions == null) functions = new ArrayList<>();
        functions.add(function);
    }
}
