package itmo.nxzage.common.commands;

public final class Exit extends Command {
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
