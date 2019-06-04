package hse.pe172.asgar.zagitov.sorters;

/**
 * Сортировка слиянием
 */
public class MergeSorter extends Sorter {

    @Override
    protected void sort(int[] array, int start, int end) {
        if (start >= end || array.length == 0)
            return;
        int middle = (start + end) / 2;
        sort(array, start, middle);
        sort(array, middle + 1, end);
        merge(array, start, middle, middle + 1, end);
    }

    @Override
    protected void merge(int[] array, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        int[] tmp = new int[array.length];

        int left_iter = leftStart;
        int right_iter = rightStart;

        for (int i = leftStart; i <= rightEnd; i++) {
            if (left_iter <= leftEnd && right_iter <= rightEnd) {
                if (array[left_iter] <= array[right_iter]) {
                    tmp[i] = array[left_iter++];
                } else {
                    tmp[i] = array[right_iter++];
                }
            } else if (left_iter <= leftEnd) {
                tmp[i] = array[left_iter++];
            } else if (right_iter <= rightEnd) {
                tmp[i] = array[right_iter++];
            }
        }

        for (int i = leftStart; i <= rightEnd; i++) {
            array[i] = tmp[i];
        }
    }

}
