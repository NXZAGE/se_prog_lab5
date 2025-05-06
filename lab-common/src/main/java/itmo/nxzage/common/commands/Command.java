package itmo.nxzage.common.commands;

public abstract class Command {
    private CommandNature nature;
    private CommandType type;
    private String name;
    private String description;

    protected Command(CommandNature nature, CommandType type, String name, String description) {
        this.nature = nature;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public CommandType getType() {
        return type;
    }

    public CommandNature getNature() {
        return nature;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean forlient() {
        return nature == CommandNature.CLIENT;
    }

    public Boolean forServer() {
        return nature == CommandNature.SERVER;
    }
}
