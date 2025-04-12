package itmo.nxzage.server.Controllers;

public abstract class IdentifyingController implements Controller {
    private Integer id;

    protected IdentifyingController(Integer id) {
        this.id = id;
    }

    public Integer getID() {
        return id;
    }

    protected abstract void updateNextID();
}
