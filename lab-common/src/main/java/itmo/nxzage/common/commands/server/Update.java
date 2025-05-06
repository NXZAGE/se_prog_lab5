package itmo.nxzage.common.commands.server;

import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.data.Person;

public final class Update extends ServerCommand { 
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

    public Integer getID() {
        return id;
    }

    public Person getElement() {
        return element;
    }
}
