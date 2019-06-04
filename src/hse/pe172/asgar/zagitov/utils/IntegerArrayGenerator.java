package hse.pe172.asgar.zagitov.utils;

import java.util.Random;

/**
 * Класс генератор массива из случайных чисел в диапазоне
 */
public class IntegerArrayGenerator {

    public static Random random = new Random();

    /**
     * Генерация массива
     * @param N кол-во элементов 2*N
     * @param M диапазон [0, M]
     * @return сгенерированный массив
     */
    public static int[] generateArray(int N, int M) {

        int[] array = new int[2*N];

        for (int i = 0; i < 2*N; i++) {
            array[i] = getInt(0, M);
        }

        return array;
    }

    /**
     * Сгенерировать случайное число [min;max]
     * @param min левая граница
     * @param max правая граница
     * @return число
     */
    private static int getInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

}
