package net.foxdenstudio.foxcore.api.util;

import java.util.Arrays;

public class StaticUtils {

    public static boolean arrayContainsIgnoreCase(String[] names, String input) {
        if (input == null || names == null || input.isEmpty()) return false;
        for (String alias : names) {
            if (alias.equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    public static <T> T[] concatenateArrays(T[] primary, T... secondary){
        T[] result = Arrays.copyOf(primary, primary.length + secondary.length);
        System.arraycopy(secondary, 0, result, primary.length, secondary.length);
        return result;
    }
}
