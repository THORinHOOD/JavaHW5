package hse.pe172.asgar.zagitov.sorters;

import hse.pe172.asgar.zagitov.tools.TestSortBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MergeSorterTest extends TestSortBase {

    @Test
    public void merge() {
        initSorter();
        testMerge(new int[]{1, 2, 3, 1, 2, 3}, new int[]{1, 1, 2, 2, 3, 3});
        testMerge(new int[]{3, 8, 10, 1, 4, 9}, new int[]{1, 3, 4, 8, 9, 10});
        testMerge(new int[]{1, 2, 1}, new int[]{1, 1, 2});
        testMerge(new int[]{}, new int[]{});
        testMerge(new int[]{1}, new int[]{1});
        testMerge(new int[]{1, 1}, new int[]{1, 1});
    }

    protected void testMerge(int[] origin, int[] res) {
        int middle = (origin.length - 1) / 2;
        sorter.merge(origin, 0, middle, middle + 1, origin.length - 1);
        assertArraysEquality(origin, res);
    }

    @Override
    protected void initSorter() {
        sorter = new MergeSorter();
    }

    @Override
    protected void disposeSorter() {}
}
