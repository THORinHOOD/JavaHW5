package hse.pe172.asgar.zagitov.tools;

import hse.pe172.asgar.zagitov.sorters.Sorter;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class TestSortBase {

    protected Sorter sorter;

    @Test
    public void sort() {
        initSorter();
        testSort(new int[]{});
        testSort(new int[]{5});
        testSort(new int[]{4, 2});
        testSort(new int[]{2, 4});
        testSort(new int[]{2, 2});
        testSort(new int[]{1, 2, 3, 4, 5});
        testSort(new int[]{1, 2, 1, 2, 1});
        testSort(new int[]{1, 10, 2, 3, 7, 9, 11, 2, 1, 4});
        testSort(new int[]{1, 4, 2, 10, 100, 5, 2, 1});
        testSort(new int[]{7, 7, 1, 0, 10, 5, 4, 2, 3});
        disposeSorter();
    }

    private void testSort(int[] origin) {
        assertNotNull(sorter);

        int[] copy = new int[origin.length];
        for (int i = 0; i < origin.length; i++) {
            copy[i] = origin[i];
        }

        sorter.sort(copy);
        boolean[] checked = new boolean[origin.length];
        for (int i = 0; i < checked.length; i++) {
            checked[i] = false;
        }

        for (int i = 0; i < copy.length - 1; i++) {
            assertTrue(copy[i] <= copy[i + 1]);
        }

        for (int i = 0; i < copy.length; i++) {
            boolean found = false;
            for (int j = 0; (j < checked.length) && !found; j++) {
                if (copy[i] == origin[j] && !checked[j]) {
                    checked[j] = true;
                    found = true;
                }
            }
            assertTrue(found);
        }

        for (int i = 0; i < checked.length; i++) {
            assertTrue(checked[i]);
        }

        assertTrue(sorter.getAllTime() >= 0);
        assertTrue(sorter.getMergeTime() >= 0);
        assertTrue(sorter.getPartLeftTime() >= 0);
        assertTrue(sorter.getPartRightTime() >= 0);
    }

    protected void assertArraysEquality(int[] first, int[] second) {
        assertEquals(first.length, second.length);
        for (int i = 0; i < first.length; i++) {
            assertEquals(first[i], second[i]);
        }
    }

    protected abstract void initSorter();
    protected abstract void disposeSorter();
}
