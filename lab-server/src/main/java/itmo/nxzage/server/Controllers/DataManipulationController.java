package itmo.nxzage.server.Controllers;

import itmo.nxzage.common.data.Person;
import itmo.nxzage.server.Storage;

public final class DataManipulationController extends IdentifyingController {
    private static Integer nextID = 1;
    private Storage storage;

    public DataManipulationController(Storage storage) {
        super(nextID);
        this.storage = storage;
        updateNextID();
    }

    public void add(Person element) {
        storage.add(element);
    }

    public void addIfMin(Person element) {
        storage.addIfMin(element);
    }

    public void addIfMax(Person element) {
        storage.addIfMax(element);
    }

    public void update(Integer id, Person element) {
        storage.update(id, element);
    }

    public void remove(Integer id) {
        storage.remove(id);
    }

    public void removeLower(Person value) {
        storage.removeLower(value);
    }

    public void clear() {
        storage.clear();
    }

    @Override
    public ControllerType getType() {
        return ControllerType.DATA_MANIPULTOR;
    }

    @Override
    protected void updateNextID() {
        ++nextID;
    }

}
