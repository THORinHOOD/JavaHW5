package hse.pe172.asgar.zagitov.sorters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.System.currentTimeMillis;

/**
 * Сортировка массива, где вторая часть сортируется на сервере
 */
public class TCPMergeSorterClient extends MergeSorter {

    private long sendTime;
    private long receiveTime;
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    /**
     * Фабричный метод класса
     * @param socket сокет клиента
     * @return клиент для сортировки массива
     * @throws IOException
     */
    public static TCPMergeSorterClient getInstance(Socket socket) throws IOException {
        DataInputStream fromServer = new DataInputStream(socket.getInputStream());
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        return new TCPMergeSorterClient(fromServer, toServer);
    }

    /**
     * Конструктор класса
     * @param fromServer входной поток с сервера
     * @param toServer выходной поток в сервер
     */
    private TCPMergeSorterClient(DataInputStream fromServer, DataOutputStream toServer) {
        this.fromServer = fromServer;
        this.toServer = toServer;
    }

    /**
     * Логика сортировки массива
     * @param array массив
     * @param middle индекс среднего элемента массива
     */
    @Override
    protected void sortLogic(int[] array, int middle) {
        try {
            long startSend = currentTimeMillis();
            toServer.writeInt(array.length - middle - 1);
            for (int i = middle + 1; i < array.length; i++) {
                toServer.writeInt(array[i]);
            }
            sendTime = currentTimeMillis() - startSend;

            sortLeft(array, middle);

            partRightTime = fromServer.readLong();

            long startReceive = currentTimeMillis();
            for (int i = middle + 1; i < array.length; i++) {
                int current = fromServer.readInt();
                array[i] = current;
            }
            receiveTime = currentTimeMillis() - startReceive;
            mainMerge(array, middle);
        } catch (IOException e) {
        }
    }

    /**
     * Получить время отправки массива на сервер
     * @return время в msec
     */
    public long getSendTime() {
        return sendTime;
    }

    /**
     * Получить время получения массива с сервера
     * @return время в msec
     */
    public long getReceiveTime() {
        return receiveTime;
    }
}
