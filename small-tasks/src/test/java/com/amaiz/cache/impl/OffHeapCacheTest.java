package com.amaiz.cache.impl;

import com.amaiz.cache.impl.clazz.BaseClass;
import com.amaiz.cache.impl.clazz.InnerClass;
import com.amaiz.cache.impl.clazz.OuterClass;
import com.amaiz.cache.impl.clazz.SubClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class OffHeapCacheTest {

    private OffHeapCache<Integer, OuterClass> offHeapCacheComplex;
    private OffHeapCache<Integer, BaseClass> offHeapCacheInheritance;

    @BeforeEach
    void setUp() {
        offHeapCacheComplex = new OffHeapCache<>();
        offHeapCacheInheritance = new OffHeapCache<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testComplexObject() {
        InnerClass innerClassForOuterClass = new InnerClass();
        innerClassForOuterClass.setInteger(2000);
        innerClassForOuterClass.setString("stringInnerClassForOuterClass");

        InnerClass innerClass1 = new InnerClass();
        innerClass1.setString("innerClass1");
        innerClass1.setInteger(3000);

        InnerClass innerClass2 = new InnerClass();
        innerClass2.setString("innerClass2");
        innerClass2.setInteger(4000);

        Map<Integer, InnerClass> mapIntegerInnerClass = new HashMap<>();
        mapIntegerInnerClass.put(1, innerClass1);
        mapIntegerInnerClass.put(2, innerClass2);

        Map<String, InnerClass> mapStringInnerClass = new HashMap<>();
        mapStringInnerClass.put("innerClass1", innerClass1);
        mapStringInnerClass.put("innerClass2", innerClass2);

        OuterClass outerClass = new OuterClass();
        outerClass.setInnerClass(innerClassForOuterClass);
        outerClass.setInteger(1000);
        outerClass.setString("stringOuterClass");
        outerClass.setMapIntegerInnerClass(mapIntegerInnerClass);
        outerClass.setMapStringInnerClass(mapStringInnerClass);

        offHeapCacheComplex.put(1, outerClass);
        OuterClass outerClassFromCache = offHeapCacheComplex.get(1);

        assertEquals(outerClass, outerClassFromCache);
    }

    @Test
    void testInheritance() {
        SubClass subClass = new SubClass();
        subClass.setIntegerSubClass(1);
        subClass.setStringSubClass("stringSubClass");
        subClass.setIntegerBaseClass(2);
        subClass.setStringBaseClass("stringBaseClass");
        offHeapCacheInheritance.put(1, subClass);
        SubClass subClassFromCache = (SubClass) offHeapCacheInheritance.get(1);
        assertEquals(subClass.getIntegerSubClass(), subClassFromCache.getIntegerSubClass());
        assertEquals(subClass.getStringSubClass(), subClassFromCache.getStringSubClass());
        assertEquals(subClass.getStringBaseClass(), subClassFromCache.getStringBaseClass());
        assertEquals(subClass.getIntegerBaseClass(), subClassFromCache.getIntegerBaseClass());
    }

    @Test
    void testExpire() {
        offHeapCacheComplex.put(1, new OuterClass(), TimeUnit.SECONDS, 2);
        assertNotNull(offHeapCacheComplex.get(1));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
        assertNull(offHeapCacheComplex.get(1));
    }

}