package itmo.nxzage.client.input.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInput implements InputSource {
    private final BufferedReader reader;

    public FileInput(String filePath) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filePath));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public InputSourceType getType() {
        return InputSourceType.SCRIPT;
    }
}
