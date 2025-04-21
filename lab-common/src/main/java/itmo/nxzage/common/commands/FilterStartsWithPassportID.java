package itmo.nxzage.common.commands;

public final class FilterStartsWithPassportID extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;
    
    static {
        TYPE = CommandType.FILTER_STARTS_WITH_PASSPORT_ID;
        NAME = "filter_starts_with_passport_ID"; 
        DESCRIPTION = "FILTER STRATS WITH PASSPORT ID";  
    }

    private String prefix;

    public FilterStartsWithPassportID(String prefix) {
        super(TYPE, NAME, DESCRIPTION);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
