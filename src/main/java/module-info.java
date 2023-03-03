module inventory.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens inventory.inventorymanagementsystem to javafx.fxml;
    exports inventory.inventorymanagementsystem;
    exports inventory.inventorymanagementsystem.controller;
    opens inventory.inventorymanagementsystem.controller to javafx.fxml;
    exports inventory.inventorymanagementsystem.model;
    opens inventory.inventorymanagementsystem.model to javafx.fxml;
}