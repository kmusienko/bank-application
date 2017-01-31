package exampleSockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kostya on 31.01.2017.
 */
public class Server {
    public static void main(String[] args) {
        int port = 5050; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение, когда кто-то связался с сервером
            System.out.println("Got a client :) Finally, someone saw me!\n");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            out.flush(); // делаем flush, чтобы убедиться, что поток работает

            String line;
            while (true) {
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                System.out.println("The client just sent me this line : " + line);
                System.out.println("I'm sending it back...\n");
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
