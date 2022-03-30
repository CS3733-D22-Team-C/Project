package edu.wpi.cs3733.D22.teamC.controller.general;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class serviceRequest implements Initializable {

    //Buttons
    @FXML private JFXButton selectButton;
    @FXML private JFXTreeTableView<?> table;
    @FXML private JFXComboBox<String> serviceType;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {



        //service request dropdown
        serviceType.getItems().add("Medical Equipment");
        serviceType.getItems().add("Facility Maintenance");
        serviceType.getItems().add("Lab System");
        serviceType.getItems().add("Medicine Delivery");
        serviceType.getItems().add("Sanitation");
        serviceType.getItems().add("Security");

        //Colums for table
        TreeTableColumn ID = new TreeTableColumn("ID");
        ID.setPrefWidth(80);
        TreeTableColumn Type = new TreeTableColumn("Type");
        Type.setPrefWidth(80);
        TreeTableColumn Assignee = new TreeTableColumn("Assignee");
        Assignee.setPrefWidth(80);
        TreeTableColumn Status = new TreeTableColumn("Status");
        Status.setPrefWidth(80);
        TreeTableColumn Location = new TreeTableColumn("Location");
        Location.setPrefWidth(80);
        TreeTableColumn Priority = new TreeTableColumn("Priority");
        Priority.setPrefWidth(80);

        table.getColumns().addAll(ID,  Type, Assignee,Status, Location, Priority);

    }
    @FXML
    void onSelectButton(ActionEvent event) { // TODO: Set views for every service request
        switch (serviceType.getValue()){
            case "Medical Equipment":
                App.instance.setView("view/service_request/medical-equipment-view.fxml");
                break;
            case "Facility Maintenance":
                App.instance.setView("");
                break;
            case "Lab System":
                App.instance.setView("");
                break;
            case "Medicine Delivery":
                App.instance.setView("");
                break;
            case "Sanitation":
                App.instance.setView("");
                break;
            case "Security":
                App.instance.setView("");
                break;
            default:
        }

    }


}
