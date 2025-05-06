package itmo.nxzage.common.commands;

public enum CommandType {
    GET_ALL(false),
    FILTER_STARTS_WITH_PASSPORT_ID(false),
    GET_ALL_ASCENDING_NATIONALITY(false),
    GET_ALL_DESCENDING_NATIONALITY(false),
    ADD(false),
    ADD_IF_MIN(false),
    ADD_IF_MAX(false),
    UPDATE(false),
    REMOVE_BY_ID(false),
    REMOVE_LOWER(false),
    CLEAR(false),
    SAVE(false),
    EXECUTE_SCRIPT(true),
    EXIT(true),
    HELP(true);

    public static CommandType parse(String name) {
        return switch (name) {
            case "get_all" -> CommandType.GET_ALL;
            case "filter_starts_with_passport_id" -> CommandType.FILTER_STARTS_WITH_PASSPORT_ID;
            case "get_all_ascending_nationality" -> CommandType.GET_ALL_ASCENDING_NATIONALITY;
            case "get_all_descending_nationality" -> CommandType.GET_ALL_DESCENDING_NATIONALITY;
            case "add" -> CommandType.ADD;
            case "add_if_min" -> CommandType.ADD_IF_MIN;
            case "add_if_max" -> CommandType.ADD_IF_MAX;
            case "update" -> CommandType.UPDATE;
            case "remove_by_id" -> CommandType.REMOVE_BY_ID;
            case "remove_lower" -> CommandType.REMOVE_LOWER;
            case "clear" -> CommandType.CLEAR;
            case "save" -> CommandType.SAVE;
            case "execute_script" -> CommandType.EXECUTE_SCRIPT;
            case "exit" -> CommandType.EXIT;
            case "help" -> CommandType.HELP;
            default -> null;
        };
    }

    private Boolean forClient;

    public Boolean forClient() {
        return forClient;
    }

    private CommandType(Boolean forClient) {
        this.forClient = forClient;
    }
}
