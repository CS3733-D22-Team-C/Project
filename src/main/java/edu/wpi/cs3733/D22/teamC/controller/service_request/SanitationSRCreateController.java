package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationServiceRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ResourceBundle;

public class SanitationSRCreateController extends ServiceRequestCreateController {
    // Class specific dropdown
    @FXML
    private JFXComboBox<String> sanitationType;

    // Table stuff
    @FXML
    private JFXTreeTableView<SanitationSRTable> table;
    ObservableList<SanitationSRTable> sanitationList = FXCollections.observableArrayList();
    final TreeItem<SanitationSRTable> root = new RecursiveTreeItem<SanitationSRTable>(sanitationList, RecursiveTreeObject::getChildren);
    ObservableList<SanitationSRTable> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        // Sanitation Dropdown
        sanitationType.getItems().add("General");
        sanitationType.getItems().add("Hazardous");
        sanitationType.getItems().add("Biohazard");
        sanitationType.getItems().add("Daily Cleaning");

        SanitationSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.SERVICE_REQUEST_SELECT);
    }

    @FXML
    void clickReset(javafx.event.ActionEvent event) {
        super.clickReset(event);

        // Clear dropdown menu
        sanitationType.valueProperty().set(null);
    }

    @FXML
    SanitationServiceRequest clickSubmit(ActionEvent event) {
        SanitationServiceRequest sanitationServiceRequest = new SanitationServiceRequest();

        // Start setting up a Java object for a SanitationServiceRequest
        // Text field setting
        sanitationServiceRequest.setAssigneeID(assigneeID.getText());
        sanitationServiceRequest.setLocation(location.getText());

        // Dropdown Boxes
        sanitationServiceRequest.setStatus(status.getValue());
        sanitationServiceRequest.setSanitationType(sanitationType.getValue());
        sanitationServiceRequest.setPriority(priority.getValue());

        // Sanitation type to enum
        int sanitationTypeEnum = sanitationServiceRequest.getSanitationTypeEnum(sanitationType.getValue());

        clickReset(event);

        SanitationSRTable tableEntry = new SanitationSRTable(sanitationServiceRequest);

        sanitationList.add(tableEntry);

        return sanitationServiceRequest;
    }
}
