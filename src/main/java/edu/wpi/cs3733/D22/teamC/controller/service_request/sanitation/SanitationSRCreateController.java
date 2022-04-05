package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance.FacilityMaintenanceSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation.SanitationSRFormEvaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
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

        SanitationSRFormEvaluator sSRFE = new SanitationSRFormEvaluator();
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = sSRFE.getSanitationSRValidationTestResult(assigneeID.getText(), location.getText(), priority.getSelectionModel(), status.getSelectionModel(), sanitationType.getSelectionModel());

        if(sSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            SanitationSR sSR = new SanitationSR();

            // Start setting up a Java object for a SanitationServiceRequest
            sSR.setAssigneeID(assigneeID.getText());
            sSR.setLocation(location.getText());
            sSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            sSR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            sSR.setDescription(description.getText());

            // Dropdown Boxes
            sSR.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));

            // Sanitation type to enum
            int sanitationTypeEnum = SanitationSR.SanitationType.valueOf(sanitationType.getValue()).ordinal();

            sSR.setRequestType(ServiceRequest.RequestType.Sanitation);

            clickReset(event);

            SanitationSRTable tableEntry = new SanitationSRTable(sSR);

            sanitationList.add(tableEntry);

            return sSR;
        }
        else
        {
            prepareErrorMessages(errors);
            return null;
        }
    }

    @Override
    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.prepareErrorMessages(l);
    }

    @Override
    public void resetErrorMessages() {
        super.resetErrorMessages();
    }
}
