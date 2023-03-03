package inventory.inventorymanagementsystem;

import inventory.inventorymanagementsystem.model.InHouse;
import inventory.inventorymanagementsystem.model.Inventory;
import inventory.inventorymanagementsystem.model.Outsourced;
import inventory.inventorymanagementsystem.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FUTURE_ENHANCEMENT: The search method for the products can show the associated parts in the results and the inventory of those parts
 * in a separate table.
 * <p><b>
 * This class creates an app that displays parts and products
 * </b></p>
 * <p>
 * JavaDoc folder found in separate zip file labeled JavaDoc
 * </p>
 */

public class Main extends Application {

    /**
     * start method initializes fxml file MainForm.fxml
     * @param stage root Stage object
     * */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  main method with some data to be populated in the Parts and Products table at the start of the app
     * @param args
     * */
    public static void main(String[] args) {
       InHouse inHousePart = new InHouse(1, "engine", 20.00, 5, 1, 10, 1);
       Inventory.addPart(inHousePart);

        Outsourced ourSourcedPart = new Outsourced(2, "tires", 10.00, 10, 1, 20, "TireMax");
        Inventory.addPart(ourSourcedPart);

        Product product1 = new Product(1, "car", 1000.00, 10, 1, 20);
        Inventory.addProduct(product1);
        product1.addAssociatedPart(inHousePart);
        Product product2 = new Product(2, "bike", 2000.00, 20, 1, 30);
        Inventory.addProduct(product2);
        product2.addAssociatedPart(ourSourcedPart);


        launch(args);
    }

    /** @param txt method checks if txt is an Integer
     * @return true if txt is an integer, otherwise false
     * */
    public static boolean isInt(String txt) {
        if(txt == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}