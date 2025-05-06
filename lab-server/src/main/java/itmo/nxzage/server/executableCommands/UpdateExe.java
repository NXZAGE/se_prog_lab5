package itmo.nxzage.server.executableCommands;

import itmo.nxzage.common.commands.server.Update;
import itmo.nxzage.common.data.Person;
import itmo.nxzage.server.ControlPanel;
import itmo.nxzage.server.ExecutionResponse;

public class UpdateExe implements Executable {
    private Integer id;
    private Person element;

    public UpdateExe(Update command) {
        id = command.getID();
        element = command.getElement();
    }

    @Override
    public ExecutionResponse execute(ControlPanel controlPanel) {
        return null;
    }
}
