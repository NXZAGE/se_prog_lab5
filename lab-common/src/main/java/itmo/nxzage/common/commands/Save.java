package itmo.nxzage.common.commands;

public final class Save extends Command {
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
