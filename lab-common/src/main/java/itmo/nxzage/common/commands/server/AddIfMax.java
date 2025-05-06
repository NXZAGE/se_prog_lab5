package itmo.nxzage.common.commands.server;

import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.data.Person;

public final class AddIfMax extends ServerCommand {
    private static CommandType TYPE;
    private static String DESCRIPTION;
    private static String NAME;

    static {
        TYPE = CommandType.ADD_IF_MAX;
        DESCRIPTION = "add_if_max descriptioin";
        NAME = "add_if_max";
    }

    private Person element;

    public AddIfMax(Person element) {
        super(TYPE, NAME, DESCRIPTION);
        this.element = element;
    }

    public Person getElement() {
        return element;
    }
}
