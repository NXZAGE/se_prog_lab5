package itmo.nxzage.common.commands;

public final class ExecuteScript extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.EXECUTE_SCRIPT;
        NAME = "execute_script";
        DESCRIPTION = "execute_script description";
    }

    public ExecuteScript() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
