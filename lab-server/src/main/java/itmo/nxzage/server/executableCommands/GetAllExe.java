package itmo.nxzage.server.executableCommands;

import itmo.nxzage.common.commands.server.GetAll;
import itmo.nxzage.server.ControlPanel;
import itmo.nxzage.server.ExecutionResponse;

public class GetAllExe implements Executable {
    public GetAllExe(GetAll command) {
        return;
    }

    @Override
    public ExecutionResponse execute(ControlPanel controlPanel) {
        return null;
    }
}
