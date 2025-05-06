package itmo.nxzage.server.executableCommands;

import itmo.nxzage.common.commands.server.FilterStartsWithPassportID;
import itmo.nxzage.server.ControlPanel;
import itmo.nxzage.server.ExecutionResponse;

public class FilterStartsWithPassportIDExe implements Executable {
    private String prefix;

    public FilterStartsWithPassportIDExe(FilterStartsWithPassportID command) {
        prefix = command.getPrefix();
    }

    @Override
    public ExecutionResponse execute(ControlPanel controlPanel) {
        return null;
    }
    
}
