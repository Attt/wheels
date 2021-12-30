package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class Lexis {

    private Object value;

    private LexisType type;

    public Lexis(Object value, LexisType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public LexisType getType() {
        return type;
    }

    public void setType(LexisType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Lexis{" +
                "value=" + value +
                ", type=" + type +
                '}';
    }
}
