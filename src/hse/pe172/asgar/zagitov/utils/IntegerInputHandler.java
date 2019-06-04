package hse.pe172.asgar.zagitov.utils;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Класс обработчик ввода числового значения в виде строки
 */
public class IntegerInputHandler {

    /**
     * Метод, который обрабатывает строку с числовым значением и проверяет корректность числа
     * @param valueStr строковое значение
     * @param checker проверка значения числа
     * @return объект optional, содержащий число, либо ничего не содержащий, при некорректном вводе
     */
    public static Optional<Integer> input(String valueStr, Predicate<Integer> checker) {
        Integer value;
        try {
            value = Integer.valueOf(valueStr);
        } catch(NumberFormatException exception) {
            return Optional.empty();
        }

        if (checker.test(value)) {
            return Optional.of(value);
        }

        return Optional.empty();
    }

}
