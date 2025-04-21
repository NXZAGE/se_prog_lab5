package itmo.nxzage.common.commands;

import itmo.nxzage.common.data.Person;

public final class Update extends Command { 
    private static final CommandType TYPE;
    private static final String NAME;
    private static final String DESCRIPTION;

    static {
        TYPE = CommandType.UPDATE;
        NAME = "update";
        DESCRIPTION = "update description";
    }

    private Integer id;
    private Person element;

    public Update(Integer id, Person element) {
        super(TYPE, NAME, DESCRIPTION);
        this.id = id;
        this.element = element;
    }

    public Person getElement() {
        return element;
    }
}
