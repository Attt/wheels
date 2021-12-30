package io.github.attt.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author atpexgo
 */
public class LexerUtil {

    public final static Set<String> NUMBERS = new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "e", "E", "-", "."));
    public final static Set<String> WHITE_SPACES = new HashSet<>(Arrays.asList(Syntax.WHITE_SPACES.getSyntax()));


    public static boolean test(String str, Syntax syntax) {
        String[] syntaxStrings = syntax.getSyntax();
        for (String syntaxString : syntaxStrings) {
            if (syntaxString.equals(str)) return true;
        }
        return false;
    }

    public static Syntax testAll(String str) {
        for (Syntax syntax : Syntax.values()) {
            String[] syntaxStrings = syntax.getSyntax();
            for (String syntaxString : syntaxStrings) {
                if (syntaxString.equals(str)) return syntax;
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

}
