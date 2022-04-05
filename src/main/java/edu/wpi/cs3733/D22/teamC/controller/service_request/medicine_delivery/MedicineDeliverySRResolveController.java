package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MedicineDeliverySRResolveController extends ServiceRequestResolveController {
    // Fields
    @FXML private TextField medicine;
    @FXML private TextField dosage;
    @FXML private TextField patientID;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);

        // Querying for full Medical Equipment Service Request
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicineDeliverySR medicineDeliverySR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());

        //title
        title.setText("Resolve Medicine Delivery Service Request");

        // Setting fields from Querying Database
        medicine.setText(medicineDeliverySR.getMedicine());
        dosage.setText(medicineDeliverySR.getDosage());
        patientID.setText(medicineDeliverySR.getPatientID());
        description.setText(medicineDeliverySR.getDescription());
        creationTime.setText(medicineDeliverySR.getCreationTimestamp().toString());

        medicine.setEditable(false);
        dosage.setEditable(false);
        patientID.setEditable(false);

        if(isEditMode){
            //title
            title.setText("Edit Medicine Delivery Service Request");

            //Textboxes now editable
            medicine.setEditable(true);
            dosage.setEditable(true);
            patientID.setEditable(true);

            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");
        }
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
        //Accessing Service Request in Database
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicineDeliverySR medicineDeliverySR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        if(isEditMode){

            medicineDeliverySR.setMedicine(medicine.getText());
            medicineDeliverySR.setDosage(dosage.getText());
            medicineDeliverySR.setPatientID(patientID.getText());

            //Status
            if(requiredFieldsPresent())
                medicineDeliverySR.setStatus(ServiceRequest.Status.Processing);
            else
                medicineDeliverySR.setStatus(ServiceRequest.Status.Blank);
            medicalEquipmentSRDAOImpl.updateServiceRequest(medicineDeliverySR);

        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    protected boolean requiredFieldsPresent(){
        if(medicine.getText().equals(""))
            return false;
        if(dosage.getText().equals(""))
            return false;
        if(patientID.getText().equals(""))
            return false;
        return super.requiredFieldsPresent();
    }

    //On action (in scenebuilder)
    @FXML
    void statusUpdated(ActionEvent event) {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("processing");
        }
        else
            secondStatus.setText("blank");
    }

    //Just for textfields
    //on key pressed (in scenebuilder)
    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated(null);
    }


}
