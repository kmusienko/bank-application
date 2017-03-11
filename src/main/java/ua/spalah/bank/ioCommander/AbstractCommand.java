package ua.spalah.bank.ioCommander;

/**
 * Created by Kostya on 31.01.2017.
 */
public class AbstractCommand {
    private final IO io;

    public AbstractCommand(IO io) {
        this.io = io;
    }

    protected String read() {
        return io.read();
    }
    protected void write(String s) {
        io.write(s);
    }
}

