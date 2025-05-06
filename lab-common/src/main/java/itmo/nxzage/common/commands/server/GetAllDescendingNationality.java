package itmo.nxzage.common.commands;

public final class GetAllDescendingNationality extends ServerCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.GET_ALL_DESCENDING_NATIONALITY;
        NAME = "get_all_descending nationality";
        DESCRIPTION = "get_all_dedcending nationality description";
    }

    public GetAllDescendingNationality() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
