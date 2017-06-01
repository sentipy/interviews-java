package com.segmento.uniquepaths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UniquePathCounterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void countUniquePathsNull() {
        int[] result = UniquePathCounter.countUniquePaths(null);
        assertNull(result);
    }

    @Test
    void countUniquePathsEmpty() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[0]);
        assertEquals(0, result.length);
    }

    @Test
    void countUniquePaths1FieldOnlyAndOpen() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true});
        assertEquals(1, result.length);
        assertEquals(0, result[0]);
    }

    @Test
    void countUniquePaths1FieldOnlyAndClosed() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{false});
        assertEquals(1, result.length);
        assertEquals(0, result[0]);
    }

    @Test
    void countUniquePaths2FieldsOnlyAndAllOpen() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, true});
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
    }

    @Test
    void countUniquePaths2FieldsOnlyAndLastClosed() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, false});
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void countUniquePaths3FieldsAndAllOpen() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, true, true});
        assertEquals(3, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(2, result[2]);
    }

    @Test
    void countUniquePaths3FieldsAndOnlyMiddleClosed() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, false, true});
        assertEquals(3, result.length);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        assertEquals(1, result[2]);
    }

    @Test
    void countUniquePaths3FieldsAndOnlyLastClosed() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, true, false});
        assertEquals(3, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(0, result[2]);
    }

    @Test
    void countUniquePaths4FieldsAndAllOpen() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, true, true, true});
        assertEquals(4, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(2, result[2]);
        assertEquals(3, result[3]);
    }

    @Test
    void countUniquePaths5FieldsAndAllOpen() {
        int[] result = UniquePathCounter.countUniquePaths(new boolean[]{true, true, true, true, true});
        assertEquals(5, result.length);
        assertEquals(5, result[4]);
    }

}