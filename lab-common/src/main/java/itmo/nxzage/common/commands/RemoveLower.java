package itmo.nxzage.common.commands;

import itmo.nxzage.common.data.Person;

public final class RemoveLower extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.REMOVE_LOWER;
        NAME = "remove_lower";
        DESCRIPTION = "remove_lower description";
    }

    private Person element;

    public RemoveLower(Person element) {
        super(TYPE, NAME, DESCRIPTION);
        this.element = element;
    }

    public Person getElement() {
        return element;
    }
}
