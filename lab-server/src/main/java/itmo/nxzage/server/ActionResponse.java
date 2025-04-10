package itmo.nxzage.server;

public interface ActionResponse<T> {
    public abstract T status();
    public abstract Boolean successful();
    public abstract Boolean hasError();
}
