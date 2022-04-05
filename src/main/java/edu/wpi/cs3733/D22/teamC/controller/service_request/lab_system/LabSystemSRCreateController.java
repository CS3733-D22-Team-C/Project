package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
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

public class LabSystemSRCreateController extends ServiceRequestCreateController {

    //Fields
    @FXML private TextField patientID;


    //Dropdowns
    @FXML private JFXComboBox<String> labType;

    //For table
    @FXML private JFXTreeTableView<LabSystemSRTable> table;
    ObservableList<LabSystemSRTable> LSTList = FXCollections.observableArrayList();
    final TreeItem<LabSystemSRTable> root = new RecursiveTreeItem<LabSystemSRTable>(LSTList, RecursiveTreeObject::getChildren);

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        //For equipment type drop down
        labType.getItems().add("Blood Sample");
        labType.getItems().add("Urine Sample");
        labType.getItems().add("X-Ray");
        labType.getItems().add("CAT scans");
        labType.getItems().add("MRI");

        LabSystemSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        setIDFieldToNumeric(patientID);
        setTextLengthLimiter(patientID, 20);
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

        LabSystemSRFormEvaluator lSSRFE = new LabSystemSRFormEvaluator();

        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = lSSRFE.getLabSystemSRValidationTestResult(location.getText(), assigneeID.getText(), status.getSelectionModel(), priority.getSelectionModel(), labType.getSelectionModel(), patientID.getText());

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
            LabSystemSRTable lst = new LabSystemSRTable(lSSR);
            LSTList.add(lst);

            clickReset(event);

            return lSSR;
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

}
