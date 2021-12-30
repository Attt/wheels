package io.github.attt.json;

import java.util.List;

/**
 * @author atpexgo
 */
public interface Json {

    boolean isArray();

    List<Object> scan(String key, boolean deepScan);
}
