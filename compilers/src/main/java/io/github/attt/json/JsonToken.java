package io.github.attt.json;

/**
 * @author atpexgo
 */
public class JsonToken {

    private Object value;

    private TokenType type;

    public JsonToken(Object value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JsonToken{" +
                "value=" + value +
                ", type=" + type +
                '}';
    }
}
