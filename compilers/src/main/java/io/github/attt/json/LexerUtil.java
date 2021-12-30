package io.github.attt.json;

/**
 * @author atpexgo
 */
public class LexerUtil {

    private final static char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'e', 'E', '-', '.'};

    public static boolean test(char ch, Syntax syntax) {
        char[] syntaxChars = syntax.getSyntax();
        for (char syntaxCh : syntaxChars) {
            if (syntaxCh == ch) return true;
        }
        return false;
    }

    public static Syntax testAll(char ch) {
        for (Syntax syntax : Syntax.values()) {
            char[] syntaxChars = syntax.getSyntax();
            for (char syntaxCh : syntaxChars) {
                if (syntaxCh == ch) return syntax;
            }
        }
        return null;
    }

    public static String testBool(String str) {
        String initial = str.substring(0, Math.min(5, str.length())).toLowerCase();
        if (initial.startsWith("true")) {
            return "true";
        } else if (initial.startsWith("false")) {
            return "false";
        }
        return null;
    }

    public static String testNull(String str) {
        String initial = str.substring(0, Math.min(4, str.length())).toLowerCase();
        if (initial.startsWith("null") || initial.startsWith("none") || initial.startsWith("nil")) {
            return "null";
        }
        return null;
    }

    public static boolean testWhiteSpace(char ch) {
        for (char whiteSpacesSyntax : Syntax.WHITE_SPACES.getSyntax()) {
            if (ch == whiteSpacesSyntax) return true;
        }
        return false;
    }

    public static boolean testNumber(char ch) {
        for (char numbersSyntax : NUMBERS){
            if (ch == numbersSyntax) return true;
        }
        return false;
    }

}
