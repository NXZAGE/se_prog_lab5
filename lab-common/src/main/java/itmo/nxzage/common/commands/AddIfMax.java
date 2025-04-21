package itmo.nxzage.common.commands;

import itmo.nxzage.common.data.Person;

public final class AddIfMax extends Command {
    private static String DESCRIPTION;
    private static String NAME;

    static {
        DESCRIPTION = "add_if_max descriptioin";
        NAME = "add_if_max";
    }

    private Person element;

    public AddIfMax(Person element) {
        this.element = element;
    }

    public Person getElement() {
        return element;
    }

    @Override
    public CommandType getType() {
        return CommandType.ADD_IF_MAX;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
