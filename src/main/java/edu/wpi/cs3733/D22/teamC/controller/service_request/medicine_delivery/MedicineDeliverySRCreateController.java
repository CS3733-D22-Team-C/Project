package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.lab_system.LabSystemSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery.MedicineDeliverySRFormEvaluator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicineDeliverySRCreateController extends ServiceRequestCreateController<MedicineDeliverySR> {
    // Fields
    @FXML private TextField medicine;
    @FXML private TextField dosage;
    @FXML private TextField patientID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        tableDisplay = new MedicineDeliverySRTableDisplay(table);

        //For Patient ID:
        setIDFieldToNumeric(patientID);

        setTextLengthLimiter(medicine, 10);
        setTextLengthLimiter(dosage, 10);
        setTextLengthLimiter(patientID, 10);
    }

    @Override
    public void setTextLengthLimiter(TextField textF, int maxLength) {
        super.setTextLengthLimiter(textF, maxLength);
    }

    @Override
    public void setIDFieldToNumeric(TextField tf) {
        super.setIDFieldToNumeric(tf);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);

        medicine.clear();
        dosage.clear();
        patientID.clear();
    }

    @FXML
    protected MedicineDeliverySR clickSubmit(ActionEvent event) {
        resetErrorMessages();
        MedicineDeliverySRFormEvaluator mDSRFE = new MedicineDeliverySRFormEvaluator();
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = mDSRFE.getMedicineDeliverySRValidationTestResult(location.getText(), assigneeID.getText(), status.getSelectionModel(), priority.getSelectionModel(), patientID.getText(), medicine.getText(), dosage.getText());

        if(mDSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            MedicineDeliverySR mdSR = new MedicineDeliverySR();

            mdSR.setAssigneeID(assigneeID.getText());
            mdSR.setLocation(location.getText());
            mdSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            mdSR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            mdSR.setDescription(description.getText());

            // Set from Field
            mdSR.setMedicine(medicine.getText());
            mdSR.setDosage(dosage.getText());
            mdSR.setPatientID(patientID.getText());

            mdSR.setRequestType(ServiceRequest.RequestType.Medicine_Delivery);

            clickReset(event);

            // Add to Table List
            tableDisplay.addObject(mdSR);

            return mdSR;
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
