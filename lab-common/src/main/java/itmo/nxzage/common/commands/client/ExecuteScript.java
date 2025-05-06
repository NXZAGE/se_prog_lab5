package itmo.nxzage.common.commands.client;

import itmo.nxzage.common.commands.CommandType;

public final class ExecuteScript extends ClientCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.EXECUTE_SCRIPT;
        NAME = "execute_script";
        DESCRIPTION = "execute_script description";
    }

    private String fileName;

    public ExecuteScript(String fileName) {
        super(TYPE, NAME, DESCRIPTION);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
