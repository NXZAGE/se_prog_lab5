package itmo.nxzage.common.commands;

public final class GetAll extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.GET_ALL;
        NAME = "get_all";
        DESCRIPTION = "GET_ALL";
    }

    public GetAll() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
