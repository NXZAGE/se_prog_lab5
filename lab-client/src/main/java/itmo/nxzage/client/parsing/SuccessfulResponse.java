package itmo.nxzage.client.parsing;

import itmo.nxzage.common.commands.Command;

public final class SuccessfulResponse extends Response {
    private Command command;

    public SuccessfulResponse(Command command) {
        super(Response.Status.SUCCESS, "OK");
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
