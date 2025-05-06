package itmo.nxzage.common.commands.server;

import itmo.nxzage.common.commands.CommandType;

public final class Save extends ServerCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.SAVE;
        NAME = "save";
        DESCRIPTION = "save descriptioin";
    }

    public Save() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
