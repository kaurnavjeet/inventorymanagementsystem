package inventory.inventorymanagementsystem.controller;

import inventory.inventorymanagementsystem.Main;
import inventory.inventorymanagementsystem.model.InHouse;
import inventory.inventorymanagementsystem.model.Inventory;
import inventory.inventorymanagementsystem.model.Outsourced;
import inventory.inventorymanagementsystem.model.Part;
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

/** ModifyPartFormController contains all logic to modify a part on the Modify Part Form */
public class ModifyPartFormController {

    Stage stage;
    Scene scene;

    @FXML
    private Label changeLblTxt;

    @FXML
    private ToggleGroup modifyPartTGroup;

    @FXML
    private RadioButton modifyPartInHouse;

    @FXML
    private RadioButton modifyPartOutsourced;

    @FXML
    private TextField partIdTxt;

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
    private TextField partPriceTxt;

    private int index = 0;

    /** @param selectedIndex the index of the part that is selected in Main Form to be modified
     * @param part the part object that is selected in Main Form to be modified
     * send data of the selected part at the selected index back to MainFormController
     * */
    public void sendData(int selectedIndex, Part part) {
        index = selectedIndex;

        if(part instanceof InHouse) {
            modifyPartInHouse.setSelected(true);
            changeLblTxt.setText("Machine ID");
            partMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            modifyPartOutsourced.setSelected(true);
            changeLblTxt.setText("Company Name");
            partMachineIdTxt.setText(((Outsourced) part).getCompanyName());
        }

        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partMaxTxt.setText(String.valueOf(part.getMax()));

    }

    /** @param event takes user back to main form when cancel button is clicked and user confirm OK */
    @FXML
    public void onActionDisplayMainForm(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /** @param event change label to machine ID when In house radio button is clicked */
    @FXML
    public void onActionModifyPartInHouse(ActionEvent event) {

        changeLblTxt.setText("Machine ID");

    }

    /** @param event change label to company name when Outsourced radio button is clicked */
    @FXML
    public void onActionModifyPartOutsourced(ActionEvent event) {

        changeLblTxt.setText("Company Name");

    }

    /** @param event when Save button is clicked, all text fields are checked
     *  if text fields are blank, user is alerted to input values in respective text fields
     *  confirm int inputs are int or alert user
     *  if part is In house, use InHouse class to add part
     *  if part is Outsourced, use Outsourced class to add part
     *  use updatePart from Inventory
     *  if part is successfully modified, take user back to main form
     * */
    @FXML
    public void onActionSavePart(ActionEvent event) {

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
            } else if(partMachineIdTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID/Company Name input is blank.Please enter valid value for Machine ID/Company Name");
                Optional<ButtonType> result = alert.showAndWait();
            } else {

                int id = Integer.parseInt((partIdTxt.getText()));
                String name = partNameTxt.getText();
                int stock = Integer.parseInt((partInvTxt.getText()));
                double price = Double.parseDouble((partPriceTxt.getText()));
                int max = Integer.parseInt(partMaxTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());

                if(min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min must be less than max");
                    Optional<ButtonType> result = alert.showAndWait();
                } else if (stock < min || stock > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inv must be between min and max");
                    Optional<ButtonType> result = alert.showAndWait();
                }

                if(stock >= min && stock <= max) {
                    if (modifyPartInHouse.isSelected()) {
                        int machineId = Integer.parseInt(partMachineIdTxt.getText());
                        InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please press OK to modify this part");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.updatePart(index, updatedPart);
                            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }

                    } else if (modifyPartOutsourced.isSelected()) {
                        String machineId = partMachineIdTxt.getText();
                        Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, machineId);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please press OK to modify this part");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.updatePart(index, updatedPart);
                            Parent root = FXMLLoader.load(getClass().getResource("/inventory/inventorymanagementsystem/view/MainForm.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                 }

            }


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
            } else if(modifyPartInHouse.isSelected() && !Main.isInt(partMachineIdTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please add valid integer for machine ID");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid value for price");
                Optional<ButtonType> result = alert.showAndWait();
            }
         }

        }


    }

