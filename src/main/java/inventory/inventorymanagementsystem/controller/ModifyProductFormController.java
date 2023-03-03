package inventory.inventorymanagementsystem.controller;

import inventory.inventorymanagementsystem.Main;
import inventory.inventorymanagementsystem.model.*;
import javafx.collections.FXCollections;
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

/** ModifyProductFormController contains all logic to modify a product on the Modify Product Form */
public class ModifyProductFormController implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private TableView<Part> productAllTblView;

    @FXML
    private TableView<Part> productAssTblView;

    @FXML
    private TableColumn<Part, Integer> addedPartId;

    @FXML
    private TableColumn<Part, Integer> addedPartInvLvl;

    @FXML
    private TableColumn<Part, String> addedPartName;

    @FXML
    private TableColumn<Part, Double> addedpartPrice;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, Integer> partInvLvl;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField partSearchTxtField;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int index = 0;

    /** @param selectedIndex the index of the product that is selected in Main Form to be modified
     * @param selectedProduct the product object that is selected in Main Form to be modified
     * loop through all associated parts of the selected product and add it to new observable list associatedParts
     * send data of the selected product at the selected index back to MainFormController
     * */
    public void sendData(int selectedIndex, Product selectedProduct) {
        index = selectedIndex;

        for(Part part : selectedProduct.getAllAssociatedParts()) {
            associatedParts.add(part);
        }

        productIdTxt.setText(String.valueOf(selectedProduct.getId()));
        productNameTxt.setText(selectedProduct.getName());
        productInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        productPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        productMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        productMaxTxt.setText(String.valueOf(selectedProduct.getMax()));

    }

    /** @param event search parts table with a string or id with enter
     * creates an observable list of results from lookupPart method from Inventory class
     * parts table shows search results or alert that part is not found
     * */
    @FXML
    public void onActionPartSearchResult(ActionEvent event) {
        String search = partSearchTxtField.getText();

        productAllTblView.setItems(Inventory.getAllParts());

        ObservableList<Part> parts = Inventory.lookUpPart(search);

        if(parts.size() == 0) {
            try {
                int partId = Integer.parseInt(search);
                Part part = Inventory.lookUpPart(partId);
                if(part != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        if(parts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
            Optional<ButtonType> result = alert.showAndWait();

        } else {
            productAllTblView.setItems(parts);
            partSearchTxtField.setText("");
        }


    }

    /** @param event add selected part in all parts table to the associated parts table when add button is clicked
     * if no part is selected, alert user to select a part
     * */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        Part selectedPart = productAllTblView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            Optional<ButtonType> result = alert.showAndWait();
        }

        associatedParts.add(selectedPart);

    }

    /** @param event takes user back to main form when cancel button is clicked and user confirm OK */
    @FXML
    public void onActionDisplayMainForm(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /** @param event remove selected associated part in associated parts table when remove associated part button is clicked and user confirms OK
     * if no part is selected, alert user to select a part
     * */
    @FXML
    public void onActionRemoveAsscPart(ActionEvent event) {
        Part selectedPart = productAssTblView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove");
            Optional<ButtonType> result = alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }

    }

    /** @param event when Save button is clicked, all text fields are checked
     *  if text fields are blank, user is alerted to input values in respective text fields
     *  confirm int inputs are int or alert user
     *  loop through associated parts observable list and add any associated parts to product
     *  use updateProduct from Inventory
     *  if product is successfully modified, take user back to main form
     * */
    @FXML
    public void onActionSaveProduct(ActionEvent event) {
        try {

            if(productNameTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Name input is blank. Please enter valid value for Name");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(productInvTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv input is blank. Please enter valid value for Inv");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(productPriceTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price input is blank. Please enter valid value for Price/Cost");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(productMaxTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max input is blank. Please enter valid value for Max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(productMinTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min input is blank. Please enter valid value for Min");
                Optional<ButtonType> result = alert.showAndWait();
            } else {

                int id = Integer.parseInt((productIdTxt.getText()));
                String name = productNameTxt.getText();
                int stock = Integer.parseInt((productInvTxt.getText()));
                double price = Double.parseDouble((productPriceTxt.getText()));
                int max = Integer.parseInt(productMaxTxt.getText());
                int min = Integer.parseInt(productMinTxt.getText());

                if(min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min must be less than max");
                    Optional<ButtonType> result = alert.showAndWait();
                } else if (stock < min || stock > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inv must be between min and max");
                    Optional<ButtonType> result = alert.showAndWait();
                }

                if(stock >= min && stock <= max) {
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for(Part associatedPart : associatedParts) {
                        newProduct.addAssociatedPart(associatedPart);
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please press OK to modify this product");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        Inventory.updateProduct(index, newProduct);
                        Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }

        } catch (NumberFormatException | IOException exception) {

            if(!Main.isInt(productInvTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Inv");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (!Main.isInt(productMaxTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (!Main.isInt(productMinTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Min");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid value for price");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }

    }

    /** @param url
     * @param resourceBundle
     * populate all parts table
     * populate associated parts table with the parts associated with the selected product to be modifed
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productAllTblView.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productAssTblView.setItems(associatedParts);
        addedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedpartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }





}
