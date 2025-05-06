package itmo.nxzage.common.commands.client;

import itmo.nxzage.common.commands.CommandType;

public final class Exit extends ClientCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.EXIT;
        NAME = "exit";
        DESCRIPTION = "exit description";
    }

    public Exit() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
