package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance.FacilityMaintenanceSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRCreateController extends ServiceRequestCreateController {

    //Fields:
    @FXML private TextField maintType;

    // For table
    @FXML private JFXTreeTableView<FacilityMaintenanceSRTable> table;
    ObservableList<FacilityMaintenanceSRTable> METList = FXCollections.observableArrayList();
    final TreeItem<FacilityMaintenanceSRTable> root = new RecursiveTreeItem<FacilityMaintenanceSRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<FacilityMaintenanceSRTable> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        FacilityMaintenanceSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    @FXML
    protected void clickReset(ActionEvent event) { //A JavaFX action event is a JavaFX Event, which represents some kind of action performed by the user or the program.
        super.clickReset(event);
        maintType.clear();
    }

    @FXML
    protected FacilityMaintenanceSR clickSubmit(ActionEvent event) {
        FacilityMaintenanceSR fmsr = new FacilityMaintenanceSR();
        fmsr.setMaintenanceType(maintType.getText());
        fmsr.setAssigneeID(assigneeID.getText());
        fmsr.setLocation(location.getText());
        fmsr.setPriority(ServiceRequest.Priority.valueOf(priority.getValue())); //getValue directly returns the value of a selected item from a JavaFX ComboBox
        fmsr.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
        fmsr.setDescription(description.getText());
        //fmsr.setRequestType("Facilities Maintenance");

        //Dealing with the equipment type and the enumerator
        //int type = fmsr.getMaintenanceTypeEnum(maintType.getText());
        clickReset(event);

        // Add Table Entry
        FacilityMaintenanceSRTable met = new FacilityMaintenanceSRTable(fmsr);
        METList.add(met);

        return fmsr;
    }

}
