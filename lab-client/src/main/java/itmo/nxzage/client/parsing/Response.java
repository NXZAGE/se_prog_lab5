package itmo.nxzage.client.parsing;

import itmo.nxzage.common.commands.Command;

public abstract class Response {
    public static enum Status {
        SUCCESS,
        ERROR;
    }

    private Status status;
    private String description;

    protected Response(Status status, String descriptioin) {
        this.status = status;
        this.description = descriptioin;
    }

    public Boolean successful() {
        return (status == Status.SUCCESS);
    }

    public Boolean hasError() {
        return (status == Status.ERROR);
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
