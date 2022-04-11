package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicineDeliverySRInsertResolveController extends InsertServiceRequestResolveController<MedicineDeliverySR> implements Initializable {


    @FXML
    private TextField dosage;

    @FXML
    private TextField medicine;

    @FXML
    private TextField patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setup(BaseServiceRequestResolveController<MedicineDeliverySR> baseServiceRequestResolveController, MedicineDeliverySR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        dosage.setEditable(isEditMode);
        medicine.setEditable(isEditMode);
        patientID.setEditable(isEditMode);

        dosage.setText(serviceRequest.getDosage());
        medicine.setText(serviceRequest.getMedicine());
        patientID.setText(serviceRequest.getPatientID());
    }

    public boolean requiredFieldsPresent(){
        if(dosage.getText().equals(""))
            return false;
        if(medicine.getText().equals(""))
            return false;
        if(patientID.getText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(MedicineDeliverySR serviceRequest){
        if(isEditMode){
                serviceRequest.setMedicine(medicine.getText());
                serviceRequest.setDosage(dosage.getText());
                serviceRequest.setPatientID(patientID.getText());
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<MedicineDeliverySR> createServiceRequestDAO() {
        return new MedicineDeliverySRDAO();
    }


    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated();
    }
}
