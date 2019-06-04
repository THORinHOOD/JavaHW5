package hse.pe172.asgar.zagitov;

import hse.pe172.asgar.zagitov.sorters.MergeSorter;
import hse.pe172.asgar.zagitov.sorters.Sorter;
import hse.pe172.asgar.zagitov.sorters.TCPMergeSorterClient;
import hse.pe172.asgar.zagitov.sorters.ThreadMergeSorter;
import hse.pe172.asgar.zagitov.utils.IntegerArrayGenerator;
import hse.pe172.asgar.zagitov.utils.IntegerInputHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Optional;

/**
 * Основной класс
 */
public class Main {

    public static final String SORT_HEADER = "%d Sort : ";
    public static final String SORT_SORTED_ARRAY = "Sorted array : %s";
    public static final String INCORRECT_INPUT_VALUE = "Parameter %s is an integer greater than 0";
    public static final String SORT_ALL_TIME = "Sequential sorting time = %d msec";
    public static final String SORT_PART_TIME = "Part %d: sorting time = %d msec";
    public static final String SORT_MERGE_TIME = "Merge time of two parts = %d msec";
    public static final String SORT_SEND_TIME = "Send time of second part to Slave = %d msec";
    public static final String SORT_RECEIVE_TIME = "Receive time of second part from Slave = %d msec";
    public static final String WRONG_COUNT_ARGS = "Not enough arguments, there must be four (N, M, adress, port).";
    public static final String WRONG_PORT_TYPE = "Port argument should be integer.";
    public static final String FILE = "results.txt";

    /**
     * Точка входа программы
     * @param args аргументы
     */
    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println(WRONG_COUNT_ARGS);
            return;
        }

        Optional<Integer> N = IntegerInputHandler.input(args[0], x -> x > 0);
        Optional<Integer> M = IntegerInputHandler.input(args[1], x -> x > 0);

        if (!N.isPresent()) {
            System.out.println(String.format(INCORRECT_INPUT_VALUE, "N"));
            return;
        }

        if (!M.isPresent()) {
            System.out.println(String.format(INCORRECT_INPUT_VALUE, "M"));
            return;
        }

        String address = args[2];
        Optional<Integer> port = IntegerInputHandler.input(args[3], x -> true);

        if (!port.isPresent()) {
            System.out.println(WRONG_PORT_TYPE);
            return;
        }

        int[] array = IntegerArrayGenerator.generateArray(N.get(), M.get());
        String originStr = "Origin array : " + arrayToString(array);
        System.out.println(originStr);
        print(false, originStr);
        System.out.println();
        print(true, FirstSort(array));
        System.out.println();
        print(true, SecondSort(array));
        System.out.println();
        print(true, ThirdSort(array, address, port.get()));
        System.out.println();
    }

    /**
     * Первая сортировка
     * @param origin исходный массив
     * @return строки с результатами
     */
    public static String[] FirstSort(int[] origin) {
        int[] array = origin.clone();
        Sorter sorter = new MergeSorter();
        sorter.sort(array);
        return new String[]{  String.format(SORT_HEADER, 1),
                              String.format(SORT_SORTED_ARRAY, arrayToString(array)),
                              String.format(SORT_ALL_TIME, sorter.getAllTime())};
    }

    /**
     * Вторая сортировка, в которой вторая часть сортируется в thread'е,
     * первая в исходном thread'e
     * @param origin исходный массив
     * @return строки с результатом
     */
    public static String[] SecondSort(int[] origin) {
        int[] array = origin.clone();
        Sorter sorter = new ThreadMergeSorter();
        sorter.sort(array);
        return new String[]{  String.format(SORT_HEADER, 2),
                              String.format(SORT_SORTED_ARRAY, arrayToString(array)),
                              String.format(SORT_ALL_TIME, sorter.getAllTime()),
                              String.format(SORT_PART_TIME, 1, sorter.getPartLeftTime()),
                              String.format(SORT_PART_TIME, 2, sorter.getPartRightTime()),
                              String.format(SORT_MERGE_TIME, sorter.getMergeTime())};
    }

    /**
     * Третий тест, используя сокеты
     * @param origin имходный сгенерированный массив
     * @param address адрес сервера
     * @param port порт сервера
     * @return строки с результатом
     */
    public static String[] ThirdSort(int[] origin, String address, Integer port) {
        try (Socket socket = new Socket(address, port)) {
            int[] array = origin.clone();
            TCPMergeSorterClient sorter = TCPMergeSorterClient.getInstance(socket);
            sorter.sort(array);
            return new String[] { String.format(SORT_HEADER, 3),
                                  String.format(SORT_SORTED_ARRAY, arrayToString(array)),
                                  String.format(SORT_ALL_TIME, sorter.getAllTime()),
                                  String.format(SORT_PART_TIME, 1, sorter.getPartLeftTime()),
                                  String.format(SORT_PART_TIME, 2, sorter.getPartRightTime()),
                                  String.format(SORT_MERGE_TIME, sorter.getMergeTime()),
                                  String.format(SORT_SEND_TIME, sorter.getSendTime()),
                                  String.format(SORT_RECEIVE_TIME, sorter.getReceiveTime())};
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Can't connect to Slave");
            return new String[]{};
        }
    }

    /**
     * Вывод результатов в консоль и в файл
     * @param append добавлять в файл или запись в новый
     * @param lines строки, которые нужно вывести
     */
    public static void print(boolean append, String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }

        try(FileOutputStream fos = new FileOutputStream(FILE, append);
            PrintStream printStream = new PrintStream(fos))
        {
            for (String line : lines) {
                printStream.println(line);
            }
            printStream.println();
        }
        catch(IOException ex) {
            System.out.println("Не удалось записать в файл");
        }
    }

    /**
     * Преобразование массив чисел в сроку
     * @param elements массив чисел
     * @return строка
     */
    public static String arrayToString(int[] elements) {
        String res = "";
        for (int i : elements) {
            res += i + " ";
        }
        return res.trim();
    }

}
