package hse.pe172.asgar.zagitov.sorters;

/**
 * Сортировка массива, где вторая часть сортируется в thread'e
 */
public class ThreadMergeSorter extends MergeSorter {

    @Override
    protected void sortLogic(int[] array, int middle) {
        Thread thread = new Thread(() -> sortRight(array, middle));
        thread.start();
        sortLeft(array, middle);
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        mainMerge(array, middle);
    }

}
