package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance.FacilityMaintenanceSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance.FacilityMaintenanceSRFormEvaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
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

    //Confused about how to properly abstract clickSubmit, I needed a downcast for it to work, which doesn't seem right.
    @FXML
    protected FacilityMaintenanceSR clickSubmit(ActionEvent event) {

        FacilityMaintenanceSRFormEvaluator fMSRFE = new FacilityMaintenanceSRFormEvaluator();

        //Even though the initialize function in ServiceRequestController sets up the assigneeID and location textfields to only read in integers, parseInt() still needs to be used on these text fields.
        int assigneeIDInt = Integer.parseInt(assigneeID.getText());
        int locationInt = Integer.parseInt(location.getText());

        ArrayList <ServiceRequestUserInputValidationErrorItem> errors = fMSRFE.getFacilityMaintenanceSRValidationTestResult(assigneeIDInt, locationInt, priority.getSelectionModel(), status.getSelectionModel(), maintType.getText());

        if(errors.isEmpty())
        {
            FacilityMaintenanceSR fMSR = new FacilityMaintenanceSR();

            fMSR.setAssigneeID(assigneeID.getText());
            fMSR.setLocation(location.getText());
            fMSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue())); //getValue directly returns the value of a selected item from a JavaFX ComboBox
            fMSR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            fMSR.setDescription(description.getText());

            fMSR.setMaintenanceType(FacilityMaintenanceSR.MaintenanceType.valueOf(maintType.getText()));
            fMSR.setRequestType(ServiceRequest.RequestType.Facility_Maintenance);

            clickReset(event);

            // Add Table Entry
            FacilityMaintenanceSRTable met = new FacilityMaintenanceSRTable(fMSR);
            METList.add(met);

            return fMSR;
        }
        else
        {
            return null; //Code will be placed here to display error messages
        }
    }

}
