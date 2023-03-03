package inventory.inventorymanagementsystem.model;


/**
 * Outsourced class inherited from abstract Part class
 * */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** @return part company name */
    public String getCompanyName() {
        return companyName;
    }

    /** @param companyName set part company name */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
