package hse.pe172.asgar.zagitov;

import hse.pe172.asgar.zagitov.sorters.TCPMergeSorterClient;
import hse.pe172.asgar.zagitov.tools.AssertTools;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void arrayToString() {
        int[] array = {1, 2 , 3, 10, 5, 100, 5};
        String line = Main.arrayToString(array);
        assertEquals(line, "1 2 3 10 5 100 5");
    }

    @Test
    public void firstSort() {
        int[] array = {1, 4, 2, 1, 5, 10, 12, 7};
        String[] result = Main.FirstSort(array);
        assertEquals(result[0], String.format(Main.SORT_HEADER, 1));
        String allTime = String.format(Main.SORT_ALL_TIME, 0);
        assertTrue(result[1].contains(allTime.substring(0, allTime.indexOf("="))));
    }

    @Test
    public void secondSort() {
        int[] array = {1, 4, 2, 1, 5, 10, 12, 7};
        String[] result = Main.SecondSort(array);
        assertEquals(result[0], String.format(Main.SORT_HEADER, 2));

        String allTime = String.format(Main.SORT_ALL_TIME, 0);
        assertTrue(result[1].contains(allTime.substring(0, allTime.indexOf("="))));

        String partTimeFirst = String.format(Main.SORT_PART_TIME, 1, 0);
        assertTrue(result[2].contains(partTimeFirst.substring(0, partTimeFirst.indexOf("="))));

        String partTimeSecond = String.format(Main.SORT_PART_TIME, 2, 0);
        assertTrue(result[3].contains(partTimeSecond.substring(0, partTimeSecond.indexOf("="))));

        String mergeTime = String.format(Main.SORT_MERGE_TIME, 0);
        assertTrue(result[4].contains(mergeTime.substring(0, mergeTime.indexOf("="))));
    }

    @Test
    public void thirdSort() throws IOException, InterruptedException {
        int port = 8080;
        String address = "localhost";
        Slave slave = new Slave();
        AssertTools.assertDoesNotThrow(() -> slave.start(port, 1, address, false));

        int[] array = {1, 4, 2, 1, 5, 10, 12, 7};
        String[] result = Main.ThirdSort(array, address, port);
        assertEquals(result[0], String.format(Main.SORT_HEADER, 3));

        String allTime = String.format(Main.SORT_ALL_TIME, 0);
        assertTrue(result[1].contains(allTime.substring(0, allTime.indexOf("="))));

        String partTimeFirst = String.format(Main.SORT_PART_TIME, 1, 0);
        assertTrue(result[2].contains(partTimeFirst.substring(0, partTimeFirst.indexOf("="))));

        String partTimeSecond = String.format(Main.SORT_PART_TIME, 2, 0);
        assertTrue(result[3].contains(partTimeSecond.substring(0, partTimeSecond.indexOf("="))));

        String mergeTime = String.format(Main.SORT_MERGE_TIME, 0);
        assertTrue(result[4].contains(mergeTime.substring(0, mergeTime.indexOf("="))));

        String sendTime = String.format(Main.SORT_SEND_TIME, 0);
        assertTrue(result[5].contains(sendTime.substring(0, sendTime.indexOf("="))));

        String receiveTime = String.format(Main.SORT_RECEIVE_TIME, 0);
        assertTrue(result[6].contains(receiveTime.substring(0, receiveTime.indexOf("="))));

        AssertTools.assertDoesNotThrow(slave::close);

        result = Main.ThirdSort(array, address, port);
        assertEquals(result.length, 0);
    }

}
