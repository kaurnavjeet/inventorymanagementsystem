package inventory.inventorymanagementsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.atomic.AtomicInteger;

/***
 * Inventory class stores all parts and all products in observable lists.
 * It contains all methods necessary to add, modify, delete, search all parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** use AtomicInteger class to generate a unique ID */
    public static AtomicInteger generateId = new AtomicInteger();

    /** @param newPart adds a new part to observable list allParts */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** @param newProduct adds a new product to observable list allProducts */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** @param partId searches for a part by given ID
     * @return part if found, otherwise null
     * */
    public static Part lookUpPart (int partId) {

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if(part.getId() == partId) {
                return part;
            }
        }

        return null;
    }

    /** @param productId searches for a product by given ID
     * @return product if found, otherwise null
     * */
    public static Product lookUpProduct (int productId) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            if(product.getId() == productId) {
                return product;
            }
        }

        return null;
    }

    /** @param partName searches for a part by given part name (partial or full)
     * @return observable list of part(s)
     * */
    public static ObservableList<Part> lookUpPart (String partName) {

        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                namedParts.add(part);
            }
        }

        return namedParts;
    }

    /** @param productName searches for a product by given product name (partial or full)
     * @return observable list of product(s)
     * */
    public static ObservableList<Product> lookUpProduct (String productName) {

        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : getAllProducts()) {
            if(product.getName().contains(productName)) {
                namedProducts.add(product);
            }
        }

        return namedProducts;
    }

    /**
     * @param index updates the part at selected index
     * @param selectedPart updates the selected part at index
     * */
    public static void updatePart (int index, Part selectedPart) {

        allParts.set(index, selectedPart);

    }

    /**
     * @param index updates the product at selected index
     * @param selectedProduct updates the selected product at index
     * */
    public static void updateProduct (int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);

    }

    /** @param selectedPart deletes selected part
     * @return true if part is deleted, otherwise false
     * */
    public static boolean deletePart (Part selectedPart) {
        if(selectedPart == null) {
            return false;
        } else {
            return allParts.remove(selectedPart);
        }
    }

    /** @param selectedProduct deletes selected product
     * @return true if product is deleted, otherwise false
     * */
    public static boolean deleteProduct (Product selectedProduct) {
        if(selectedProduct == null) {
            return false;
        } else {
           return allProducts.remove(selectedProduct);
        }
    }

    /** @return an observable list of all parts */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** @return an observable list of all products */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
