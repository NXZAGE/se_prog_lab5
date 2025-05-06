package itmo.nxzage.common.commands.client;

import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.commands.server.ServerCommand;

public final class Help extends ServerCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.HELP;
        NAME = "help";
        DESCRIPTION = "Shows all avaliable commands and input rules";
    }

    public Help() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
