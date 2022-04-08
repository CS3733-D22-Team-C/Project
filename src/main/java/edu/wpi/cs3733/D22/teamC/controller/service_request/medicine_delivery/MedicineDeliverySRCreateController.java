package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery.MedicineDeliverySRFormEvaluator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.sql.Timestamp;
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
            MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();
            medicineDeliverySR.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        if (assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty()
        || medicine.getText().isEmpty() || dosage.getText().isEmpty() || patientID.getText().isEmpty()) {
            return null;
        }

            medicineDeliverySR.setAssigneeID(assigneeID.getText());
            medicineDeliverySR.setLocation(location.getText());
            medicineDeliverySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            medicineDeliverySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            medicineDeliverySR.setDescription(description.getText());

            // Set from Field
            medicineDeliverySR.setMedicine(medicine.getText());
            medicineDeliverySR.setDosage(dosage.getText());
            medicineDeliverySR.setPatientID(patientID.getText());

            medicineDeliverySR.setRequestType(ServiceRequest.RequestType.Medicine_Delivery);

            // Add to Table List
            tableDisplay.addObject(medicineDeliverySR);

            clickReset(event);

            //Database entry:
            MedicineDeliverySRDAO serviceRequestDAO = new MedicineDeliverySRDAO();
            serviceRequestDAO.insert(medicineDeliverySR);

            return medicineDeliverySR;
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
