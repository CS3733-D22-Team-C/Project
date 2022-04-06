package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
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
        MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();

        medicineDeliverySR.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        if (assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty()
        || medicine.getText().isEmpty() || dosage.getText().isEmpty() || patientID.getText().isEmpty()) {
            return null;
        }

        // Set from Field
        medicineDeliverySR.setAssigneeID(assigneeID.getText());
        medicineDeliverySR.setDescription(description.getText());
        medicineDeliverySR.setLocation(location.getText());

        medicineDeliverySR.setMedicine(medicine.getText());
        medicineDeliverySR.setDosage(dosage.getText());
        medicineDeliverySR.setPatientID(patientID.getText());

        // Set from Dropdowns
        medicineDeliverySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
        medicineDeliverySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));

        medicineDeliverySR.setRequestType(ServiceRequest.RequestType.Medicine_Delivery);

        clickReset(event);

        // Add to Table List
        tableDisplay.addObject(medicineDeliverySR);

        ServiceRequestDAO serviceRequestDAO = new MedicineDeliverySRDAOImpl();
        serviceRequestDAO.insertServiceRequest(medicineDeliverySR);

        return medicineDeliverySR;
    }
}
