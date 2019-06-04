package hse.pe172.asgar.zagitov.sorters;

import static java.lang.System.currentTimeMillis;

/**
 * Класс сортировки
 */
public abstract class Sorter {

    protected long allTime;
    protected long partLeftTime;
    protected long partRightTime;
    protected long mergeTime;

    /**
     * Сортировка массива чисел
     * @param array массив
     */
    public void sort(int[] array) {
        int middle = (array.length - 1) / 2;
        long start = currentTimeMillis();
        sortLogic(array, middle);
        allTime = currentTimeMillis() - start;
    }

    /**
     * Сортировка левой части массива
     * @param array массив
     * @param middle индекс среднего элемента массива
     */
    protected void sortLeft(int[] array, int middle) {
        long start = currentTimeMillis();
        sort(array, 0, middle);
        partLeftTime = currentTimeMillis() - start;
    }

    /**
     * Сортировка правой части массива
     * @param array массив
     * @param middle индекс среднего элемента массива
     */
    protected void sortRight(int[] array, int middle) {
        long start = currentTimeMillis();
        sort(array, middle + 1, array.length - 1);
        partRightTime = currentTimeMillis() - start;
    }

    /**
     * Логика слияния полученных частей массива
     * @param array массив
     * @param middle индекс среднего элемента массива
     */
    protected void mainMerge(int[] array, int middle) {
        long mergeStart = currentTimeMillis();
        merge(array, 0, middle, middle + 1, array.length - 1);
        mergeTime = currentTimeMillis() - mergeStart;
    }

    /**
     * Основная логика сортировки
     * @param array массив
     * @param middle индекс среднего элемента массива
     */
    protected void sortLogic(int[] array, int middle) {
        sortLeft(array, middle);
        sortRight(array, middle);
        mainMerge(array, middle);
    }

    /**
     * Метод сортировки [start;end]
     * @param array массив
     * @param start с какого элемента нужно отсортировать
     * @param end по какой элемент нужно сортировать
     */
    protected abstract void sort(int[] array, int start, int end);

    /**
     * Метод слияния частей [leftStart; leftEnd] [rightStart; rightEnd]
     * @param array массив
     * @param leftStart начало левой части
     * @param leftEnd конец левой части
     * @param rightStart начало правой части
     * @param rightEnd конец правой части
     */
    protected abstract void merge(int[] array, int leftStart, int leftEnd, int rightStart, int rightEnd);

    /**
     * Получить время сортировки
     * @return время в msec
     */
    public long getAllTime() {
        return allTime;
    }

    /**
     * Получить время сортировки левой части
     * @return время в msec
     */
    public long getPartLeftTime() {
        return partLeftTime;
    }

    /**
     * Получить время правой части
     * @return время в msec
     */
    public long getPartRightTime() {
        return partRightTime;
    }

    /**
     * Получить время слияния
     * @return время в msec
     */
    public long getMergeTime() {
        return mergeTime;
    }
}
