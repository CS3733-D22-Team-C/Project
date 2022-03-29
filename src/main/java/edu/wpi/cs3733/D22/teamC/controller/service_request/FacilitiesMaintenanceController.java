package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.awt.event.InputMethodEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilitiesMaintenanceController implements Intializable {

    //Fields:

    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField maintType;
    @FXML private TextField location;

    //Dropdowns:

    @FXML JFXComboBox<String> status;
    @FXML JFXComboBox<String> priority;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Add options to priority dropdown list:
        priority.getItems().add("Low"); //getItems() returns a list of dropdown items from the JFXComboBox variable, and add() allows you to add a dropdown item to the list.
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //Add options to status dropdown list:
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");



    }

}
