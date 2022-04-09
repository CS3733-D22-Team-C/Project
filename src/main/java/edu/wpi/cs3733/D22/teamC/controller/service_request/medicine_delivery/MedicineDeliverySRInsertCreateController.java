package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicineDeliverySRInsertCreateController implements InsertServiceRequestCreateController<MedicineDeliverySR>, Initializable {
    @FXML private TextField medicine;
    @FXML private TextField dosage;
    @FXML private TextField patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void clearFields() {
        medicine.setText(null);
        dosage.setText(null);
        patientID.setText(null);
    }

    @Override
    public MedicineDeliverySR createServiceRequest() {
        MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();

        medicineDeliverySR.setMedicine(medicine.getText());
        medicineDeliverySR.setDosage(dosage.getText());
        medicineDeliverySR.setPatientID(patientID.getText());

        return medicineDeliverySR;
    }

    @Override
    public DAO<MedicineDeliverySR> createServiceRequestDAO() {
        return new MedicineDeliverySRDAO();
    }

    @Override
    public ServiceRequestTableDisplay<MedicineDeliverySR> setupTable(JFXTreeTableView<?> table) {
        return new ServiceRequestTableDisplay(table);
    }
    @Override
    public boolean requiredFieldsPresent(){
        if(medicine.getText().equals(""))
            return false;
        if(dosage.getText().equals(""))
            return false;
        if(patientID.getText().equals(""))
            return false;
        return true;
    }
}
