package hse.pe172.asgar.zagitov.sorters;

import java.io.*;
import java.net.Socket;

/**
 * Сервер для сортировки массива
 * Сортировка массива, где вторая часть сортируется на сервере
 */
public class TCPMergeSorterServer extends MergeSorter implements Runnable {

    private Socket connectedClient;

    /**
     * Конструктор класса
     * @param connectedClient сокет клиента
     */
    public TCPMergeSorterServer(Socket connectedClient) {
        this.connectedClient = connectedClient;
    }

    /**
     * Сортирует присланный массив,
     * возвращает обратно сначала время работы, после сам массив
     */
    @Override
    public void run() {
        try {
            while(!connectedClient.isClosed() && connectedClient.isConnected()) {
                DataInputStream fromClient = new DataInputStream(connectedClient.getInputStream());
                DataOutputStream toClient = new DataOutputStream(connectedClient.getOutputStream());

                int N = fromClient.readInt();
                if (N <= 0) {
                    toClient.writeLong(0);
                    continue;
                }

                int[] array = new int[N];
                for (int i = 0; i < array.length; i++) {
                    array[i] = fromClient.readInt();
                }

                sort(array);
                toClient.writeLong(getAllTime());

                for (int value : array) {
                    toClient.writeInt(value);
                }
            }
        } catch(IOException ignore) {}
    }
}
