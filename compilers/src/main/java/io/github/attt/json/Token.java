package io.github.attt.json;

/**
 * @author atpexgo
 */
public final class Token {

    private Object value;

    private Syntax syntax;

    public Token(Object value, Syntax syntax) {
        this.value = value;
        this.syntax = syntax;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Syntax getSyntax() {
        return syntax;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value=" + value +
                ", syntax=" + syntax +
                '}';
    }
}
