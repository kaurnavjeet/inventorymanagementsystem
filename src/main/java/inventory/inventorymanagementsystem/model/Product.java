package inventory.inventorymanagementsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class contains all methods to get, add, and delete associated parts.
 * Provides product objects
 * */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** @return observable list of associated parts */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /** @param part add part to associated parts observable list. */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** @param selectedAssociatedPart to delete.
     * @return true if selected associated part is deleted, otherwise false. */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart) {
        if(selectedAssociatedPart == null) {
            return false;
        }
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** @return product id */
    public int getId() {
        return id;
    }

    /** @param id set product ID */
    public void setId(int id) {
        this.id = id;
    }

    /** @return product name */
    public String getName() {
        return name;
    }

    /** @param name set product name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return product price */
    public double getPrice() {
        return price;
    }

    /** @param price set product price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return product stock */
    public int getStock() {
        return stock;
    }

    /** @param stock set product stock */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @return product min */
    public int getMin() {
        return min;
    }

    /** @param min set product min */
    public void setMin(int min) {
        this.min = min;
    }

    /** @return product max */
    public int getMax() {
        return max;
    }

    /** @param max set product max */
    public void setMax(int max) {
        this.max = max;
    }

}
