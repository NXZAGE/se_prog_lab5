package itmo.nxzage.common.commands.server;

import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.data.Person;

public final class Add extends ServerCommand {
    private static final CommandType TYPE;
    private static final String DESCRIPTION;
    private static final String NAME;

    static {
        TYPE = CommandType.ADD;
        NAME = "add";
        DESCRIPTION = "ADD desciption";
    }

    private Person element;
    
    public Add(Person element) {
        super(TYPE, NAME, DESCRIPTION);
        this.element = element;
    }

    public Person getElement() {
        return element;
    }
}
