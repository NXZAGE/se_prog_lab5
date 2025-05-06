package itmo.nxzage.common.commands.client;

import itmo.nxzage.common.commands.Command;
import itmo.nxzage.common.commands.CommandNature;
import itmo.nxzage.common.commands.CommandType;

public abstract class ClientCommand extends Command {
    protected ClientCommand(CommandType type, String name, String description) {
        super(CommandNature.CLIENT, type, name, description);
    }
}
