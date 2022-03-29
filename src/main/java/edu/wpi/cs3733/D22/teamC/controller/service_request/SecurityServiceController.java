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

public class SecurityServiceController implements Initializable {

    //Fields
    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField location;

    //Dropdowns

    @FXML private JFXComboBox<String> priority;
    @FXML private JFXComboBox<String> status;
    @FXML private JFXComboBox<String> securityType;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //For equipment type drop down
        securityType.getItems().add("security type 1");
        securityType.getItems().add("security type 2");
        securityType.getItems().add("security type 3");
        securityType.getItems().add("security type 4");

        //For priority dropdown
        priority.getItems().add("Low");
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //For status dropdown
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");

        //Colums for table
        TreeTableColumn ID = new TreeTableColumn("ID");
        ID.setPrefWidth(80);
        TreeTableColumn Asignee = new TreeTableColumn("Asignee");
        Asignee.setPrefWidth(80);
        TreeTableColumn Status = new TreeTableColumn("Status");
        Status.setPrefWidth(80);
        TreeTableColumn Location = new TreeTableColumn("Location");
        Location.setPrefWidth(80);
        TreeTableColumn Type = new TreeTableColumn("Type");
        Type.setPrefWidth(80);
        TreeTableColumn TypeNum = new TreeTableColumn("Type #");
        TypeNum.setPrefWidth(80);

        table.getColumns().addAll(ID, Asignee, Status, Location, Type, TypeNum);

    }
    @FXML
    void clickGoBack(ActionEvent event) {

    }

    @FXML
    void clickReset(ActionEvent event) {
    }

    @FXML
    void clickSubmit(ActionEvent event) {

    }


}