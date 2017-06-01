package com.segmento.uniquepaths;

public class UniquePathCounter {

    public static int[] countUniquePaths(boolean[] allowed) {
        if (allowed == null) {
            return null;
        }
        int length = allowed.length;
        int[] result = new int[length];
        if (length == 0 || length == 1) {
            return result;
        }
        result[1] = allowed[1] ? 1 : 0;
        if (length == 2) {
            return result;
        }
        if (allowed[2]) {
            result[2] = 1 + result[1];
        }
        for (int i = 3; i < length; ++i) {
            if (!allowed[i]) {
                continue;
            }
            result[i] = result[i - 1] + result[i - 2];
        }
        return result;
    }
}
