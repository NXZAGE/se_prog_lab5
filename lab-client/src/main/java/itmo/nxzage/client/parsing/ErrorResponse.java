package itmo.nxzage.client.parsing;

public class ErrorResponse extends Response {
    private Exception exception;

    public ErrorResponse(Exception exception) {
        super(Response.Status.ERROR, "Parser returned to the safe state");
        this.exception = exception;
    }

    private Exception getException() {
        return exception;
    }
}
