package hse.pe172.asgar.zagitov.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerArrayGeneratorTest {

    @Test
    public void generateArray() {
        for (int j = 0; j < 20; j++) {
            int[] array = IntegerArrayGenerator.generateArray(5, 10);
            assertEquals(array.length, 10);
            for (int i : array) {
                assertTrue((i <= 10) && (i >= 0));
            }

            array = IntegerArrayGenerator.generateArray(10, 20);
            assertEquals(array.length, 20);
            for (int i : array) {
                assertTrue((i <= 20) && (i >= 0));
            }
        }
    }
}
