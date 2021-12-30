package io.github.attt.json;

/**
 * Syntax in legal json string
 *
 * @author atpexgo
 */
public enum Syntax {

    COMMA(LexisType.COMMA, ","),
    COLON(LexisType.COLON, ":"),
    WHITE_SPACES(LexisType.WHITE_SPACES, "\t", "\r", "\n", " ", ""),
    LEFT_BRACKET(LexisType.LEFT_BRACKET, "["),
    RIGHT_BRACKET(LexisType.RIGHT_BRACKET, "]"),
    LEFT_BRACE(LexisType.LEFT_BRACE, "{"),
    RIGHT_BRACE(LexisType.RIGHT_BRACE, "}"),
    APOSTROPHE(LexisType.APOSTROPHE, "'"),
    DOUBLE_QUOTES(LexisType.DOUBLE_QUOTES, "\""),
    LEFT_ANGLE_BRACKET(LexisType.LEFT_ANGLE_BRACKET, "<"),
    RIGHT_ANGLE_BRACKET(LexisType.RIGHT_ANGLE_BRACKET, ">"),
    LEFT_ROUND_BRACKET(LexisType.LEFT_ROUND_BRACKET, "("),
    RIGHT_ROUND_BRACKET(LexisType.RIGHT_ROUND_BRACKET, ")"),
    EQUAL(LexisType.EQUAL, "="),
    AT(LexisType.AT, "@"),
    QUESTION_MARK(LexisType.QUESTION_MARK, "?"),
    DOLLAR(LexisType.DOLLAR, "$"),
    ASTERISK(LexisType.ASTERISK, "*"),
    DOT(LexisType.DOT, ".");

    private final LexisType lexisType;

    private final String[] syntax;

    Syntax(LexisType lexisType, String... syntax) {
        this.lexisType = lexisType;
        this.syntax = syntax;
    }

    public LexisType getLexisType() {
        return lexisType;
    }

    public String[] getSyntax() {
        return syntax;
    }
}
