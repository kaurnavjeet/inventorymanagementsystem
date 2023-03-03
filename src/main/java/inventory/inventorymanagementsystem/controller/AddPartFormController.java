package inventory.inventorymanagementsystem.controller;

import inventory.inventorymanagementsystem.Main;
import inventory.inventorymanagementsystem.model.InHouse;
import inventory.inventorymanagementsystem.model.Inventory;
import inventory.inventorymanagementsystem.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/** AddPartFormController contains all logic to add a new part on the Add Part Form */
public class AddPartFormController {

    Stage stage;
    Scene scene;

    @FXML
    private ToggleGroup addPartTGroup;

    @FXML
    private Label changeLblTxt;

    @FXML
    private TextField partIdTxt;

    @FXML
    private RadioButton partInHouse;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMachineIdTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private RadioButton partOutsourced;

    @FXML
    private TextField partPriceTxt;


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


    /** @param event when Save button is clicked, all text fields are checked
     *  if text fields are blank, user is alerted to input values in respective text fields
     *  confirm int inputs are int or alert user
     *  if part is In house, use InHouse class to add part
     *  if part is Outsourced, use Outsourced class to add part
     *  use addPart from Inventory
     *  if part is successfully added, take user back to main form
     * */
    @FXML
    public void onActionSavePart(ActionEvent event)  {
        try {
            if(partNameTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Name input is blank. Please enter valid value for Name");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partInvTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv input is blank. Please enter valid value for Inv");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partPriceTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price input is blank. Please enter valid value for Price/Cost");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partMaxTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max input is blank. Please enter valid value for Max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partMinTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min input is blank. Please enter valid value for Min");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partInHouse.isSelected() && partMachineIdTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID input is blank.Please enter valid value for Machine ID");
                Optional<ButtonType> result = alert.showAndWait();
            } else {

                int size = Inventory.getAllParts().size();
                int id = size + Inventory.generateId.incrementAndGet();
                String name = partNameTxt.getText();
                int stock = Integer.parseInt((partInvTxt.getText()));
                double price = Double.parseDouble((partPriceTxt.getText()));
                int max = Integer.parseInt(partMaxTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
//                String machineId = partMachineIdTxt.getText();
                int machineId = 0;
                String companyName;

                if(min > max ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min must be less than max");
                    Optional<ButtonType> result = alert.showAndWait();
                } else if(stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inv must be between min and max");
                    Optional<ButtonType> result = alert.showAndWait();
                }

                if(stock >= min && stock <= max) {
                    if(partInHouse.isSelected()) {
                        machineId = Integer.parseInt(partMachineIdTxt.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please press OK to add this part");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }

                    } else if (partOutsourced.isSelected()) {
                        companyName = partMachineIdTxt.getText();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please press OK to add this product");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }

                    }

                 }
            }

            // Ask about machine ID alert message when In House is selected.
        } catch (NumberFormatException | IOException exception) {
            if(!Main.isInt(partInvTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Inv");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (!Main.isInt(partMaxTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (!Main.isInt(partMinTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer value for Min");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partInHouse.isSelected() && !Main.isInt(partMachineIdTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid integer for machine ID");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid value for price");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }


    }

    /** @param event change label to machine ID when In house radio button is clicked */
    @FXML
    public void onActionSelectPartInHouse(ActionEvent event) {

        changeLblTxt.setText("Machine ID");

    }

    /** @param event change label to company name when Outsourced radio button is clicked */
    @FXML
    public void onActionSelectPartOutsourced(ActionEvent event) {

        changeLblTxt.setText("Company Name");

    }

}
