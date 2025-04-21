package itmo.nxzage.common.commands;

public abstract class Command {
    private CommandType type;
    private String name;
    private String description;

    protected Command(CommandType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public CommandType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
