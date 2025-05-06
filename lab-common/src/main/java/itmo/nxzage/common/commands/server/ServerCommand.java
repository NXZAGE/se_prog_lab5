package itmo.nxzage.common.commands.server;

import itmo.nxzage.common.commands.Command;
import itmo.nxzage.common.commands.CommandNature;
import itmo.nxzage.common.commands.CommandType;

public abstract class ServerCommand extends Command {
    protected ServerCommand(CommandType type, String name, String description) {
        super(CommandNature.SERVER, type, name, description);
    }
}
