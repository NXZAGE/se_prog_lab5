package itmo.nxzage.common.commands;

public class RemoveByID extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.REMOVE_BY_ID;
        NAME = "remove_by_id";
        DESCRIPTION = "remove_by_id description";
    }

    private Integer id;

    public RemoveByID(Integer id) {
        super(TYPE, NAME, DESCRIPTION);
        this.id = id;
    }

    public Integer getID() {
        return id;
    }
}
