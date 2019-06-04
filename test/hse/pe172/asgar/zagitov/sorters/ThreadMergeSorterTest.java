package hse.pe172.asgar.zagitov.sorters;

import hse.pe172.asgar.zagitov.tools.TestSortBase;

public class ThreadMergeSorterTest extends TestSortBase {

    @Override
    protected void initSorter() {
        sorter = new ThreadMergeSorter();
    }

    @Override
    protected void disposeSorter() {}

}
