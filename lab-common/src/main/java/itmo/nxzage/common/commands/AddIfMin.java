package itmo.nxzage.common.commands;

import itmo.nxzage.common.data.Person;

public final class AddIfMin extends Command {
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.ADD_IF_MIN;
        NAME = "add_id_min";
        DESCRIPTION = "add_if_min description";
    }

    private Person element;

    public AddIfMin(Person element) {
        super(TYPE, NAME, DESCRIPTION);
        this.element = element;
    }

    public Person getElement() {
        return element;
    }
}
