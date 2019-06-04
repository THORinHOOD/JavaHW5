package hse.pe172.asgar.zagitov;

import hse.pe172.asgar.zagitov.sorters.TCPMergeSorterServer;
import hse.pe172.asgar.zagitov.utils.IntegerInputHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс для сервера обработчика входных данных (их сортировка)
 */
public class Slave {

    public static final String CMD_EXIT = "exit";
    public static final String MSG_EXIT_INFO = "To exit Slave, enter \"%s\"";
    public static final String WRONG_COUNT_ARGS = "Not enough arguments, there must be one (port).";
    public static final String WRONG_PORT_TYPE = "Port argument should be integer.";
    public static final String ERROR_SOCKET = "Can't start slave.";

    private ServerSocket serverSocket;
    private ConnectionHandler connectionHandler;
    private long backlog;

    /**
     * Точка входа программы
     * @param args аргументы - порт
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(WRONG_COUNT_ARGS);
            return;
        }
        Optional<Integer> port = IntegerInputHandler.input(args[1], x -> true);

        if (port.isPresent()) {
            try {
                new Slave().start(port.get(), 10, "localhost", true);
            } catch (IOException | InterruptedException | IllegalArgumentException e) {
                System.out.println(ERROR_SOCKET);
                return;
            }
        } else {
            System.out.println(WRONG_PORT_TYPE);
            return;
        }
    }

    /**
     * Запуск сервера
     * @param port порт
     * @param backlog максимальная длина очереди входящих подключений
     * @param address адрес
     * @param withExit запускать ли консольный интерфейс для закрытия программы
     */
    public void start(int port, int backlog, String address, boolean withExit) throws IOException, InterruptedException {
        this.backlog = backlog;
        start(new ServerSocket(port, backlog, InetAddress.getByName(address)), backlog, withExit);
    }

    /**
     * Запуск сервера
     * @param serverSocket сокет сервера
     * @param backlog  максимальная длина очереди входящих подключений
     * @param withExit запускать ли консольный интерфейс для закрытия программы
     */
    private void start(ServerSocket serverSocket, int backlog, boolean withExit) throws IOException, InterruptedException {
        this.serverSocket = serverSocket;
        connectionHandler = new ConnectionHandler(serverSocket, backlog);
        connectionHandler.start();

        if (withExit) {
            System.out.println(String.format(MSG_EXIT_INFO, CMD_EXIT));
            Scanner scanner = new Scanner(System.in);
            while (!scanner.nextLine().equalsIgnoreCase(CMD_EXIT)) ;
            close();
        }
    }

    /**
     * Закрытие сервера
     */
    public void close() throws IOException, InterruptedException {
        serverSocket.close();
        connectionHandler.join();
    }

    /**
     * Класс обработчик входящих подключений
     */
    public static class ConnectionHandler extends Thread {

        private int backlog;
        private ServerSocket serverSocket;
        private ExecutorService service;

        /**
         * Конструктор
         * @param serverSocket сокет сервера
         * @param backlog макс число входящих подключений
         */
        public ConnectionHandler(ServerSocket serverSocket, int backlog) {
            this.serverSocket = serverSocket;
            this.backlog = backlog;
        }

        @Override
        public void run() {
            try {
                service = Executors.newFixedThreadPool(backlog);
                while(true) {
                    Socket connected = serverSocket.accept();
                    service.submit(new TCPMergeSorterServer(connected));
                }
            } catch (Exception ex) {}

            if (service != null) {
                service.shutdown();
            }
        }
    }

}
