package itmo.nxzage.server.executableCommands;

import itmo.nxzage.server.ControlPanel;
import itmo.nxzage.server.Executionresponse;

public interface Executable {
    public Executionresponse execute(ControlPanel controlPanel);
}
