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
        setTextLengthLimiter(maintType, 20);
    }

    @Override
    public void setTextLengthLimiter(TextField textF, int maxLength) {
        super.setTextLengthLimiter(textF, maxLength);
    }

    @FXML
    protected void clickReset(ActionEvent event) { //A JavaFX action event is a JavaFX Event, which represents some kind of action performed by the user or the program.
        super.clickReset(event);
        maintType.clear();
    }

    @FXML
    protected FacilityMaintenanceSR clickSubmit(ActionEvent event) {

        FacilityMaintenanceSRFormEvaluator fMSRFE = new FacilityMaintenanceSRFormEvaluator();

        //Even though the initialize function in ServiceRequestController sets up the assigneeID and location textfields to only read in integers, parseInt() still needs to be used on these text fields.
        int assigneeIDInt = Integer.parseInt(assigneeID.getText());
        int locationInt = Integer.parseInt(location.getText());

        ArrayList <ServiceRequestUserInputValidationErrorItem> errors = fMSRFE.getFacilityMaintenanceSRValidationTestResult(assigneeIDInt, locationInt, priority.getSelectionModel(), status.getSelectionModel(), maintType.getText());

        if(errors.isEmpty())
        {
            FacilityMaintenanceSR fMSR = (FacilityMaintenanceSR) super.clickSubmit(event);

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
            generateErrorMessages(errors);
            return null; //Code will be placed here to display error messages
        }
    }

    @Override
    public void generateErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.generateErrorMessages(l);
    }
}
