package net.foxdenstudio.foxcore.api.util;

public class StaticUtils {

    public static boolean arrayContainsIgnoreCase(String[] names, String input) {
        if (input == null || names == null || input.isEmpty()) return false;
        for (String alias : names) {
            if (alias.equalsIgnoreCase(input)) return true;
        }
        return false;
    }

}
