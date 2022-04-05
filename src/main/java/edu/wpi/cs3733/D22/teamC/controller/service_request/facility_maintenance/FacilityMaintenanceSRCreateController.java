package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance.FacilityMaintenanceSRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRCreateController extends ServiceRequestCreateController<FacilityMaintenanceSR> {

    //Fields:
    @FXML private JFXComboBox maintType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        maintType.getItems().add("Cleaning");
        maintType.getItems().add("Organizing");
        tableDisplay = new FacilityMaintenanceSRTableDisplay(table);
    }

    @FXML
    protected void clickReset(ActionEvent event) { //A JavaFX action event is a JavaFX Event, which represents some kind of action performed by the user or the program.
        super.clickReset(event);
        maintType.valueProperty().setValue(null);
    }

    @FXML
    protected FacilityMaintenanceSR clickSubmit(ActionEvent event) {
        FacilityMaintenanceSR fmsr = new FacilityMaintenanceSR();
        fmsr.setMaintenanceType(FacilityMaintenanceSR.MaintenanceType.valueOf(maintType.getValue().toString()));
        fmsr.setAssigneeID(assigneeID.getText());
        fmsr.setLocation(location.getText());
        fmsr.setPriority(ServiceRequest.Priority.valueOf(priority.getValue())); //getValue directly returns the value of a selected item from a JavaFX ComboBox
        fmsr.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
        fmsr.setDescription(description.getText());

        fmsr.setRequestType(ServiceRequest.RequestType.Facility_Maintenance);


        //Dealing with the equipment type and the enumerator
        //int type = fmsr.getMaintenanceTypeEnum(maintType.getText());
        clickReset(event);

        // Add Table Entry
        tableDisplay.addObject(fmsr);

        return fmsr;
    }

}
