package itmo.nxzage.client.input.tools;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleInput implements InputSource {
    private final Scanner reader = new Scanner(System.in);

    @Override
    public String readLine() throws IOException {
        return reader.nextLine();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public InputSourceType getType() {
        return InputSourceType.CONSOLE;
    }
}
