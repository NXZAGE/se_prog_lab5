package itmo.nxzage.server.Controllers;

import itmo.nxzage.server.Storage;

public final class MemoryController extends IdentifyingController {
    private static Integer nextID = 1;
    private Storage storage;

    public MemoryController(Storage storage) {
        super(nextID);
        this.storage = storage;
        updateNextID();
    }

    public Boolean load() {
        return this.storage.load();
    }

    public Boolean dump() {
        return this.storage.dump();
    }

    @Override
    public ControllerType getType() {
        return ControllerType.MEMORY;
    }

    @Override
    protected void updateNextID() {
        ++nextID;
    }
}
