package hse.pe172.asgar.zagitov.utils;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class IntegerInputHandlerTest {

    @Test
    public void correctValue() {
        Optional<Integer> value = IntegerInputHandler.input("15", x -> x >= 15);
        assertTrue(value.isPresent() && value.get().equals(15));

        value = IntegerInputHandler.input("10", x -> x > 9);
        assertTrue(value.isPresent() && value.get().equals(10));

        value = IntegerInputHandler.input("15", x -> x > 0);
        assertTrue(value.isPresent() && value.get().equals(15));
    }

    @Test
    public void incorrectValue() {
        Optional<Integer> value = IntegerInputHandler.input("15", x -> x >= 16);
        assertFalse(value.isPresent());

        value = IntegerInputHandler.input("10", x -> x > 10);
        assertFalse(value.isPresent());

        value = IntegerInputHandler.input("15.4", x -> x > 0);
        assertFalse(value.isPresent());

        value = IntegerInputHandler.input("qwdom", x -> x > 0);
        assertFalse(value.isPresent());
    }

}
