package itmo.nxzage.client.input.tools;

import java.io.IOException;

public interface InputSource {
    public String readLine() throws IOException;
    public void close() throws IOException;
    public InputSourceType getType();
}
