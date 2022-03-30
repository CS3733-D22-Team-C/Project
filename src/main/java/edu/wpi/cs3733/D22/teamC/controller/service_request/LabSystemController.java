package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class LabSystemController implements Initializable {

    //Fields
    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField patientID;
    @FXML private TextField location;

    //Dropdowns

    @FXML private JFXComboBox<String> priority;
    @FXML private JFXComboBox<String> status;
    @FXML private JFXComboBox<String> labType;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //For equipment type drop down
        labType.getItems().add("Blood Sample");
        labType.getItems().add("Urine Sample");
        labType.getItems().add("X-Ray");
        labType.getItems().add("CAT scans");
        labType.getItems().add("MRI");

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
        TreeTableColumn TypeNum = new TreeTableColumn("Type #");
        TypeNum.setPrefWidth(80);

        table.getColumns().addAll(ID, Assignee, Status, Location, Type, TypeNum);

    }
    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView("view/general/demo.fxml");
    }

    @FXML
    void clickReset(ActionEvent event) {
        assigneeID.clear();
        description.clear();
        patientID.clear();
        location.clear();
        priority.setValue(null);
        status.setValue(null);
        labType.setValue(null);
    }

    @FXML
    void clickSubmit(ActionEvent event) {

    }


}
