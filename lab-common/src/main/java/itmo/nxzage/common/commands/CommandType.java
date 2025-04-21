package itmo.nxzage.common.commands;

public enum CommandType {
    GET_ALL,
    FILTER_STARTS_WITH_PASSPORT_ID,
    GET_ALL_ASCENDING_NATIONALITY,
    GET_ALL_DESCENDING_NATIONALITY,
    ADD,
    ADD_IF_MIN,
    ADD_IF_MAX,
    UPDATE,
    REMOVE_BY_ID,
    REMOVE_LOWER,
    CLEAR,
    SAVE,
    EXECUTE_SCRIPT,
    EXIT
}
