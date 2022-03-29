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

public class MedicalEquipmentController implements Initializable {

    //Fields
    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField equipID;
    @FXML private TextField location;

    //Dropdowns

    @FXML private JFXComboBox<String> priority;
    @FXML private JFXComboBox<String> status;
    @FXML private JFXComboBox<String> equipType;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //For equipment type drop down
        equipType.getItems().add("Bed (20)");
        equipType.getItems().add("Recliners (6)");
        equipType.getItems().add("Portable X-Ray");
        equipType.getItems().add("Infusion Pumps (30)");

        //For priority dropdown
        priority.getItems().add("Low");
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //For status dropdown
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");

        //Columns for table
        TreeTableColumn ID = new TreeTableColumn("ID");
        ID.setPrefWidth(80);
        TreeTableColumn Assignee = new TreeTableColumn("Assignee");
        Assignee.setPrefWidth(80);
        TreeTableColumn Status = new TreeTableColumn("Status");
        Status.setPrefWidth(80);
        TreeTableColumn Location = new TreeTableColumn("Location");
        Location.setPrefWidth(80);
        TreeTableColumn Type = new TreeTableColumn("Type");
        Type.setPrefWidth(80);
        TreeTableColumn TypeID = new TreeTableColumn("Type ID");
        TypeID.setPrefWidth(80);

        table.getColumns().addAll(ID, Assignee, Status, Location, Type, TypeID);

    }
    @FXML
    void clickGoBack(ActionEvent event) {

    }

    @FXML
    void clickReset(ActionEvent event) {
        //clearing text fields/areas
        assigneeID.clear();
        location.clear();
        equipID.clear();
        description.clear();

        //Clearing combo boxes
        priority.valueProperty().set(null);
        status.valueProperty().set(null);
        equipType.valueProperty().setValue(null);

    }

    @FXML
    void clickSubmit(ActionEvent event) {
        
    }


}
