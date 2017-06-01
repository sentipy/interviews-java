package com.amaiz.cache.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Utils {

    private static Unsafe theUnsafe;

    static {
        try {
            theUnsafe = (Unsafe) getField(Unsafe.class, "theUnsafe").get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Field getField(Class cls, String name) {
        try {
            Field f = cls.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Unsafe getUnsafe() throws IllegalAccessException {
        return theUnsafe;
    }
}
