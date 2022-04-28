package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicineDeliverySRInsertCreateController implements InsertServiceRequestCreateController<MedicineDeliverySR>, Initializable {
    @FXML private TextField medicine;
    @FXML private TextField dosage;
    @FXML private JFXButton patientButton;
    @FXML private SearchableComboBox<Patient> patientSComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SVGParser svgParser = new SVGParser();
        String patientIcon = svgParser.getPath("static/icons/employee_icon.svg");
        SVGGlyph patientContent = new SVGGlyph(patientIcon);
        patientContent.setSize(20);
        patientButton.setGraphic(patientContent);
        patientButton.setText("");

        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::toString);

        patientSComboBox.getItems().setAll(patients);
    }

    @Override
    public void clearFields() {
        medicine.setText(null);
        dosage.setText(null);
        patientSComboBox.valueProperty().setValue(null);
    }

    @Override
    public MedicineDeliverySR createServiceRequest() {
        MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();

        medicineDeliverySR.setMedicine(medicine.getText());
        medicineDeliverySR.setDosage(dosage.getText());
        medicineDeliverySR.setPatient(patientSComboBox.getValue());

        return medicineDeliverySR;
    }

    @Override
    public DAO<MedicineDeliverySR> createServiceRequestDAO() {
        return new MedicineDeliverySRDAO();
    }

    @Override
    public ServiceRequestTableDisplay<MedicineDeliverySR> setupTable(JFXTreeTableView<?> table) {
        return new MedicineDeliverySRTableDisplay(table);
    }
    @Override
    public boolean requiredFieldsPresent(){
        if(medicine.getText().equals(""))
            return false;
        if(dosage.getText().equals(""))
            return false;
        if(patientSComboBox.getValue() == null){
            return false;
        }
        return true;
    }

    @FXML
    public void goToPatientTable(){
        new PatientSelectorWindow(this::setPatient);
    }
    
    public void setPatient(Patient patient){
        patientSComboBox.setValue(patient);
    }

    public MedicineDeliverySR createNewServiceRequest(){
        return new MedicineDeliverySR();
    }
}
