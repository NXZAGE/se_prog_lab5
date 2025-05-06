package itmo.nxzage.common.commands;

public final class GetAllAscendingNationality extends ServerCommand {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.GET_ALL_ASCENDING_NATIONALITY;
        NAME = "get_all_ascending_nationality";
        DESCRIPTION = "get_all_ascending nationality description";
    }

    public GetAllAscendingNationality() {
        super(TYPE, NAME, DESCRIPTION);
    }
}
