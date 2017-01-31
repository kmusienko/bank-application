package ua.spalah.bank.ioCommander;

import java.util.Scanner;

/**
 * Created by Kostya on 31.01.2017.
 */
public class ConsoleIO implements IO {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void write(String s) {
        System.out.println(s);
    }
}
