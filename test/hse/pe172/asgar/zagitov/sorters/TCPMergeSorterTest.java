package hse.pe172.asgar.zagitov.sorters;

import hse.pe172.asgar.zagitov.Slave;
import hse.pe172.asgar.zagitov.tools.TestSortBase;

import java.io.IOException;
import java.net.Socket;

public class TCPMergeSorterTest extends TestSortBase {

    private Slave slave;
    private Socket clientSocket;

    @Override
    public void initSorter() {
        int port = 8080;
        slave = new Slave();
        try {
            slave.start(port, 1, "localhost", false);
            clientSocket = new Socket("localhost", port);
            sorter = TCPMergeSorterClient.getInstance(clientSocket);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void disposeSorter() {
        try {
            slave.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
