package itmo.nxzage.server.Controllers;

import java.util.ArrayList;
import itmo.nxzage.common.data.Person;
import itmo.nxzage.server.Storage;

public class DataProvisionController extends IdentifyingController {
    private static Integer nextID;
    private Storage storage;

    public DataProvisionController(Storage storage) {
        super(nextID);
        this.storage = storage;
        updateNextID();
    }

    public ArrayList<Person> getAll() {
        return storage.getAll(false);
    }

    public ArrayList<Person> getAllReversed() {
        return storage.getAll(true);
    }

    public ArrayList<Person> filterByPassportID(String prefix) {
        return storage.filterByPassportID(prefix);
    }

    @Override
    public ControllerType getType() {
        return ControllerType.DATA_PROVIDER;
    }

    @Override
    protected void updateNextID() {
        ++nextID;
    }
}
