package itmo.nxzage.server;

import itmo.nxzage.server.Controllers.DataManipulationController;
import itmo.nxzage.server.Controllers.DataProvisionController;
import itmo.nxzage.server.Controllers.MemoryController;

public class ControlPanel {
    private final DataManipulationController dmController;
    private final DataProvisionController dpController;
    private final MemoryController memoryController;

    public ControlPanel(DataManipulationController dmc, DataProvisionController dpc, MemoryController mc) {
        dmController = dmc;
        dpController = dpc;
        memoryController = mc;
    }

    public DataManipulationController dmController() {
        return dmController;
    }

    public DataProvisionController dpController() {
        return dpController;
    }

    public MemoryController memoryController() {
        return memoryController;
    }
}
