package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.lab_system.LabSystemSRFormEvaluator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LabSystemSRCreateController extends ServiceRequestCreateController<LabSystemSR> {

    //Fields
    @FXML private TextField patientID;

    //Dropdowns
    @FXML private JFXComboBox<String> labType;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        //For equipment type drop down
        labType.getItems().add("Blood_Sample");
        labType.getItems().add("Urine_Sample");
        labType.getItems().add("X_Ray");
        labType.getItems().add("Cat_Scan");
        labType.getItems().add("MRI");

        tableDisplay = new LabSystemSRTableDisplay(table);

        setIDFieldToNumeric(patientID);
        setTextLengthLimiter(patientID, 10);
    }

    @Override
    public void setIDFieldToNumeric(TextField tf) {
        super.setIDFieldToNumeric(tf);
    }

    @Override
    public void setTextLengthLimiter(TextField textF, int maxLength) {
        super.setTextLengthLimiter(textF, maxLength);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);

        patientID.clear();
        labType.setValue(null);
    }

    @FXML
    protected LabSystemSR clickSubmit(ActionEvent event) {
        resetErrorMessages();
        LabSystemSRFormEvaluator lSSRFE = new LabSystemSRFormEvaluator();
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = lSSRFE.getLabSystemSRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), labType.getSelectionModel(), patientID.getText());

        if(lSSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            LabSystemSR lSSR = new LabSystemSR();

            lSSR.setAssigneeID(assigneeID.getText());
            lSSR.setLocation(location.getText());
            lSSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            lSSR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            lSSR.setDescription(description.getText());

            //Sets from textFields
            lSSR.setPatientID(patientID.getText());

            //Sets from combo boxes
            lSSR.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));

            lSSR.setRequestType(ServiceRequest.RequestType.Lab_System);

            //Table Entry
            tableDisplay.addObject(lSSR);

            clickReset(event);

            return lSSR;
        }
        else
        {
            prepareErrorMessages(errors);
            errors.clear();
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
