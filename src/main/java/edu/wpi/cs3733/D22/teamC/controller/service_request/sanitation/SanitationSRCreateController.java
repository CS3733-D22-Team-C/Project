package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
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
    protected void clickReset(javafx.event.ActionEvent event) {
        super.clickReset(event);

        // Clear dropdown menu
        sanitationType.valueProperty().set(null);
    }

    @FXML
    protected SanitationSR clickSubmit(ActionEvent event) {
        SanitationSR sanitationSR = new SanitationSR();

        if(assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty() || sanitationType.getSelectionModel().isEmpty()) {
            return null;
        }
        // Start setting up a Java object for a SanitationServiceRequest
        // Text field setting
        sanitationSR.setAssigneeID(assigneeID.getText());
        sanitationSR.setLocation(location.getText());

        // Dropdown Boxes
        sanitationSR.setStatus(status.getValue());
        sanitationSR.setSanitationType(sanitationType.getValue());
        sanitationSR.setPriority(priority.getValue());

        // Sanitation type to enum
        int sanitationTypeEnum = sanitationSR.getSanitationTypeEnum(sanitationType.getValue());

        clickReset(event);

        SanitationSRTable tableEntry = new SanitationSRTable(sanitationSR);

        sanitationList.add(tableEntry);

        return sanitationSR;
    }
}