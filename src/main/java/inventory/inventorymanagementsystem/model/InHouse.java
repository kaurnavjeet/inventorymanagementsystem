package inventory.inventorymanagementsystem.model;


/**
 * InHouse class inherited from abstract Part class.
 * */
public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** @return part machine ID */
    public int getMachineId() {
        return machineId;
    }

    /** @param machineId set part machine ID */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
