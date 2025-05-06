package itmo.nxzage.common.commands;

public final class Clear extends ServerCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.CLEAR;
        NAME = "clear";
        DESCRIPTION = "clear description";
    }

    public Clear() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
