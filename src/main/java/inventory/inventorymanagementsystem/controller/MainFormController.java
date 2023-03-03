package inventory.inventorymanagementsystem.controller;

import inventory.inventorymanagementsystem.model.Inventory;
import inventory.inventorymanagementsystem.model.Part;
import inventory.inventorymanagementsystem.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * RUNTIME_ERROR: In method onActionModifyProduct(), there was runtime error because the incorrect XML file path was given to FXML loader.
 * Fixed the error by correcting the XML file path.
 * <p><b>
 * MainFormController provides logic for the main screen when app launches.
 * </b></p>
 */
public class MainFormController implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, Integer> partInvLvl;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TextField partSearchTxtField;

    @FXML
    private TableColumn<Product, Double> productCost;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, Integer> productIncLvl;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TextField productSearchTxtField;


    /** @param event search parts table with a string or id with enter.
     * creates an observable list of results from lookupPart method from Inventory class.
     * parts table shows search results or alert that part is not found.
     * */
    @FXML
    public void onActionPartsSearchResult(ActionEvent event) {

        String search = partSearchTxtField.getText();

        partsTableView.setItems(Inventory.getAllParts());

        ObservableList<Part> parts = Inventory.lookUpPart(search);

        if(parts.size() == 0) {
            try {
                int partId = Integer.parseInt(search);
                Part part = Inventory.lookUpPart(partId);
                if(part != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException e) {
            }
        }

        if(parts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
            Optional<ButtonType> result = alert.showAndWait();

        } else {
            partsTableView.setItems(parts);
            partSearchTxtField.setText("");
        }


    }


    /** @param event search products table with a string or id with enter
     * creates an observable list of results from lookupProduct method from Inventory class
     * products table shows search results or alert that product is not found
     * */
    @FXML
    public void onActionProductSearchResult(ActionEvent event) {

        String search = productSearchTxtField.getText();

        productsTableView.setItems(Inventory.getAllProducts());

        ObservableList<Product> products = Inventory.lookUpProduct(search);

        if(products.size() == 0) {
            try {
                int productId = Integer.parseInt(search);
                Product product = Inventory.lookUpProduct(productId);
                if(product != null) {
                    products.add(product);
                }
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        if(products.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
            Optional<ButtonType> result = alert.showAndWait();

        } else {
            productsTableView.setItems(products);
            productSearchTxtField.setText("");
        }


    }

    /** @param event open add part form when add button is clicked on parts table*/
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/AddPartForm.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /** @param event open add product form when add button is clicked on products table*/
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/AddProductForm.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /** @param event select a part to delete.
     * if no part is selected, alert user to select a part.
     * ask for confirmation to delete the selected part.
     * call on deletePart from Inventory to delete the selected part.
     * */
    @FXML
    public void onActionDeletePart(ActionEvent event) {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /** @param event select a product to delete.
     * if no product is selected, alert user to select a product.
     * if selected product has associated parts, alert user this product cannot be deleted.
     * ask for confirmation to delete the selected product.
     * call on deleteProduct from Inventory to delete the selected product.
     * */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete");
            Optional<ButtonType> result = alert.showAndWait();
        }else if(selectedProduct.getAllAssociatedParts().size() != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "This product has associated parts, it cannot be deleted");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }

        }


    }

    /** @param event open modify part form when modify is clicked.
     * if no part is selected, alert user to select a part.
     * get data from ModifyPartFormController sendData method to populate modify part form with the selected part data.
     * */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/inventorymanagementsystem/view/ModifyPartForm.fxml"));
            Parent root = loader.load();

            ModifyPartFormController modifyPartFormController = loader.getController();
            modifyPartFormController.sendData(partsTableView.getSelectionModel().getSelectedIndex(), partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to modify");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /** @param event open modify product form when modify is clicked.
     * if no product is selected, alert user to select a product.
     * get data from ModifyProductFormController sendData method to populate modify product form with the selected product data.
     * */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/inventorymanagementsystem/view/ModifyProductForm.fxml"));
            Parent root = loader.load();

            ModifyProductFormController modifyProductFormController = loader.getController();
            modifyProductFormController.sendData(productsTableView.getSelectionModel().getSelectedIndex(), productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /** @param event exits the app when exit button is clicked and user confirms OK */
    @FXML
    public void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    /** @param url
     * @param resourceBundle
     * populate all parts table
     * populate all products table
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productIncLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}