package edu.wpi.cs3733.D22.teamC.controller.csv_io;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVFacade;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CSVComponent implements Initializable {

    //Checkboxes
    @FXML private MFXCheckbox employeesExport;
    @FXML private MFXCheckbox employeesImport;
    @FXML private MFXCheckbox patientImport;
    @FXML private MFXCheckbox patientExport;
    @FXML private MFXCheckbox facilityMaintenanceExport;
    @FXML private MFXCheckbox facilityMaintenanceImport;
    @FXML private MFXCheckbox floorsExport;
    @FXML private MFXCheckbox floorsImport;
    @FXML private MFXCheckbox locationsExport;
    @FXML private MFXCheckbox locationsImport;
    @FXML private MFXCheckbox medicalEquipmentExport;
    @FXML private MFXCheckbox medicalEquipmentImport;
    @FXML private MFXCheckbox medicineDeliveryExport;
    @FXML private MFXCheckbox medicineDeliveryImport;
    @FXML private MFXCheckbox sanitationExport;
    @FXML private MFXCheckbox sanitationImport;
    @FXML private MFXCheckbox securityExport;
    @FXML private MFXCheckbox securityImport;
    @FXML private MFXCheckbox labSystemExport;
    @FXML private MFXCheckbox labSystemImport;
    @FXML private MFXCheckbox medicalEquipmentEntityExport;
    @FXML private MFXCheckbox medicalEquipmentEntityImport;
    @FXML private MFXCheckbox deliveryImport;
    @FXML private MFXCheckbox deliveryExport;
    @FXML private MFXCheckbox patientTransportImport;
    @FXML private MFXCheckbox patientTransportExport;
    @FXML private MFXCheckbox laundryImport;
    @FXML private MFXCheckbox laundryExport;
    @FXML private MFXCheckbox translatorImport;
    @FXML private MFXCheckbox translatorExport;
    @FXML private JFXButton importButton;
    @FXML private JFXButton exportButton;
    
    @FXML private MFXCheckbox selectAllImport;
    @FXML private MFXCheckbox selectAllExport;

    //Textfields
    @FXML private TextField exportText;
    @FXML private TextField importText;

    File savedFile;

    public static final String FLOOR_CSV = "TowerFloors.csv";
    public static final String PATIENT_CSV = "Patients.csv";
    public static final String LOCATION_CSV = "TowerLocations.csv";
    public static final String MEDICAL_EQUIPMENT_CSV = "MedEquipReq.csv";
    public static final String MEDICINE_DELIVERY_CSV = "MedDeliveryReq.csv";
    public static final String SANITATION_CSV = "SanitationReq.csv";
    public static final String LAB_SYSTEM_CSV = "LabReq.csv";
    public static final String FACILITY_MAINTENANCE_CSV = "FacilityReq.csv";
    public static final String SECURITY_CSV = "SecurityReq.csv";
    public static final String EMPLOYEE_CSV = "Employees.csv";
    public static final String MEDICAL_EQUIPMENT_ENTITY_CSV = "MedicalEquip.csv";
    public static final String DELIVERY_SYSTEM_CSV = "DeliverySysReq.csv";
    public static final String PATIENT_TRANSPORT_CSV = "PatientTransportReq.csv";
    public static final String LAUNDRY_CSV = "LaundryReq.csv";
    public static final String TRANSLATOR_CSV = "TranslatorReq.csv";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SVGParser svgParser = new SVGParser();
        String folderIcon = svgParser.getPath("static/icons/folder_icon.svg");

        SVGGlyph folderContent = new SVGGlyph(folderIcon);
        SVGGlyph folderContent2 = new SVGGlyph(folderIcon);
        folderContent.getStyleClass().add("folder-icon");
        folderContent2.getStyleClass().add("folder-icon");
        importButton.setGraphic(folderContent);
        exportButton.setGraphic(folderContent2);
    }

    @FXML
    void chooseExportCSV(ActionEvent event) {
        chooseCSV(exportText);
    }

    @FXML
    void chooseImportCSV(ActionEvent event) {
        chooseCSV(importText);
    }

    @FXML
    void clickExportFiles(ActionEvent event) {
        if(!exportText.getText().equals("")) {
            entitiesChecked(true);
            setFieldsImport(false, false);
            setFieldsExport(false, false);
        }
    }

    @FXML
    void clickImportFiles(ActionEvent event) {
        if(!importText.getText().equals("")) {
            entitiesChecked(false);
            setFieldsImport(false, false);
            setFieldsExport(false, false);
        }

    }
    
    @FXML
    void toggleSelectionsImport(ActionEvent event) {
        setFieldsImport(selectAllImport.isSelected(), true);
    }
    
    @FXML
    void toggleSelectionsExport(ActionEvent event) {
        setFieldsExport(selectAllExport.isSelected(), true);
    }

    void chooseCSV(TextField csvName) {
        // Create a file chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Export CSV File");
        File file = directoryChooser.showDialog(App.instance.getStage());
        csvName.setText("..."+ File.separator+ file.getName());
        savedFile = file;

        //Path to the files
        for (int i = 0; i < savedFile.list().length; i++) {
            System.out.println(savedFile.getPath() + File.separator + savedFile.list()[i]);
        }
    }

    void entitiesChecked(boolean isExport){
        if(isExport){
            //Map
            if(floorsExport.isSelected()){
                FloorDAO floorDAO = new FloorDAO();
                List<Floor> floors = floorDAO.getAll();
                CSVFacade.write(Floor.class, savedFile.getPath() + File.separator + FLOOR_CSV, floors);
            }
            if(locationsExport.isSelected()) {
                LocationDAO locationDAO = new LocationDAO();
                List<Location> locations = locationDAO.getAll();
                CSVFacade.write(Location.class, savedFile.getPath() + File.separator + LOCATION_CSV, locations);
            }

            //Service Request
            if(medicalEquipmentExport.isSelected()) {
                MedicalEquipmentSRDAO medicalEquipmentSRDAO = new MedicalEquipmentSRDAO();
                List<MedicalEquipmentSR> medicalEquipmentSRS = medicalEquipmentSRDAO.getAll();
                CSVFacade.write(MedicalEquipmentSR.class, savedFile.getPath() + File.separator + MEDICAL_EQUIPMENT_CSV, medicalEquipmentSRS);
            }
            if(medicineDeliveryExport.isSelected()) {
                MedicineDeliverySRDAO medicineDeliverySRDAO = new MedicineDeliverySRDAO();
                List<MedicineDeliverySR> medicineDeliverySRS = medicineDeliverySRDAO.getAll();
                CSVFacade.write(MedicineDeliverySR.class, savedFile.getPath() + File.separator + MEDICINE_DELIVERY_CSV, medicineDeliverySRS);
            }
            if(sanitationExport.isSelected()) {
                SanitationSRDAO sanitationSRDAO = new SanitationSRDAO();
                List<SanitationSR> sanitationSRS = sanitationSRDAO.getAll();
                CSVFacade.write(SanitationSR.class, savedFile.getPath() + File.separator + SANITATION_CSV, sanitationSRS);
            }
            if(labSystemExport.isSelected()) {
                LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAO();
                List<LabSystemSR> labSystemSRS = labSystemSRDAO.getAll();
                CSVFacade.write(LabSystemSR.class, savedFile.getPath() + File.separator + LAB_SYSTEM_CSV, labSystemSRS);
            }
            if(facilityMaintenanceExport.isSelected()) {
                FacilityMaintenanceSRDAO facilityMaintenanceSRDAO = new FacilityMaintenanceSRDAO();
                List<FacilityMaintenanceSR> facilityMaintenanceSRS = facilityMaintenanceSRDAO.getAll();
                CSVFacade.write(FacilityMaintenanceSR.class, savedFile.getPath() + File.separator + FACILITY_MAINTENANCE_CSV, facilityMaintenanceSRS);
            }
            if(securityExport.isSelected()) {
                SecuritySRDAO securitySRDAO = new SecuritySRDAO();
                List<SecuritySR> securitySRS = securitySRDAO.getAll();
                CSVFacade.write(SecuritySR.class, savedFile.getPath() + File.separator + SECURITY_CSV, securitySRS);
            }
            if(deliveryExport.isSelected()) {
                DeliverySystemSRDAO deliverySystemSRDAO = new DeliverySystemSRDAO();
                List<DeliverySystemSR> deliverySystemSRS = deliverySystemSRDAO.getAll();
                CSVFacade.write(DeliverySystemSR.class, savedFile.getPath() + File.separator + DELIVERY_SYSTEM_CSV, deliverySystemSRS);
            }
            if(patientTransportExport.isSelected()) {
                PatientTransportSRDAO patientTransportSRDAO = new PatientTransportSRDAO();
                List<PatientTransportSR> patientTransportSRS = patientTransportSRDAO.getAll();
                CSVFacade.write(PatientTransportSR.class, savedFile.getPath() + File.separator + PATIENT_TRANSPORT_CSV, patientTransportSRS);
            }
            if(laundryExport.isSelected()) {
                LaundrySRDAO laundrySRDAO = new LaundrySRDAO();
                List<LaundrySR> laundrySRS = laundrySRDAO.getAll();
                CSVFacade.write(LaundrySR.class, savedFile.getPath() + File.separator + LAUNDRY_CSV, laundrySRS);
            }
            if(translatorExport.isSelected()) {
                TranslatorSRDAO translatorSRDAO = new TranslatorSRDAO();
                List<TranslatorSR> translatorSRS = translatorSRDAO.getAll();
                CSVFacade.write(TranslatorSR.class, savedFile.getPath() + File.separator + TRANSLATOR_CSV, translatorSRS);
            }
            //Employee
            if(employeesExport.isSelected()) {
                EmployeeDAO employeeDAO = new EmployeeDAO();
                List<Employee> employees = employeeDAO.getAll();
                CSVFacade.write(Employee.class, savedFile.getPath() + File.separator + EMPLOYEE_CSV, employees);
            }

            if(patientExport.isSelected()) {
                PatientDAO patientDAO =  new PatientDAO();
                List<Patient> patients = patientDAO.getAll();
                CSVFacade.write(Patient.class, savedFile.getPath() + File.separator + PATIENT_CSV, patients);
            }

            //Medical Equipment Entity
            if(medicalEquipmentEntityExport.isSelected()) {
                MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
                List<MedicalEquipment> medicalEquipments = medicalEquipmentDAO.getAll();
                CSVFacade.write(MedicalEquipment.class, savedFile.getPath() + File.separator + MEDICAL_EQUIPMENT_ENTITY_CSV, medicalEquipments);
            }
        }
        else {
            //Map
            if(floorsImport.isSelected()){
                List<Floor> floors = CSVFacade.read(Floor.class, savedFile.getPath() + File.separator + FLOOR_CSV);
                FloorDAO floorDAO = new FloorDAO();
                floorDAO.deleteAllFromTable();
                floors.forEach(floorDAO::insert);
            }
            if(locationsImport.isSelected()) {
                List<Location> locations = CSVFacade.read(Location.class, savedFile.getPath() + File.separator + LOCATION_CSV);
                LocationDAO locationDAO = new LocationDAO();
                locationDAO.deleteAllFromTable();
                locations.forEach(locationDAO::insert);
            }

            //Employee
            if(employeesImport.isSelected()) {
                List<Employee> employees = CSVFacade.read(Employee.class, savedFile.getPath() + File.separator + EMPLOYEE_CSV);
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.deleteAllFromTable();
                employees.forEach(employeeDAO::insert);
            }

            if(patientImport.isSelected()){
                List<Patient> patients = CSVFacade.read(Patient.class, savedFile.getPath()+ File.separator + PATIENT_CSV);
                PatientDAO patientDAO = new PatientDAO();
                patientDAO.deleteAllFromTable();
                patients.forEach(patientDAO::insert);
            }

            //Medical Equipment Entity
            if(medicalEquipmentEntityImport.isSelected()) {
                List<MedicalEquipment> medicalEquipments = CSVFacade.read(MedicalEquipment.class, savedFile.getPath() + File.separator + MEDICAL_EQUIPMENT_ENTITY_CSV);
                MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
                medicalEquipmentDAO.deleteAllFromTable();
                medicalEquipments.forEach(medicalEquipmentDAO::insert);
            }

            //Service Request
            if(medicalEquipmentImport.isSelected()) {
                List<MedicalEquipmentSR> medicalEquipmentSRS = CSVFacade.read(MedicalEquipmentSR.class, savedFile.getPath() + File.separator + MEDICAL_EQUIPMENT_CSV);
                MedicalEquipmentSRDAO medicalEquipmentSRDAO = new MedicalEquipmentSRDAO();
                medicalEquipmentSRDAO.deleteAllFromTable();
                medicalEquipmentSRS.forEach(medicalEquipmentSRDAO::insert);
            }
            if(medicineDeliveryImport.isSelected()) {
                List<MedicineDeliverySR> medicineDeliverySRS = CSVFacade.read(MedicineDeliverySR.class, savedFile.getPath() + File.separator + MEDICINE_DELIVERY_CSV);
                MedicineDeliverySRDAO medicineDeliverySRDAO = new MedicineDeliverySRDAO();
                medicineDeliverySRDAO.deleteAllFromTable();
                medicineDeliverySRS.forEach(medicineDeliverySRDAO::insert);
            }
            if(sanitationImport.isSelected()) {
                List<SanitationSR> sanitationSRS = CSVFacade.read(SanitationSR.class, savedFile.getPath() + File.separator + SANITATION_CSV);
                SanitationSRDAO sanitationSRDAO = new SanitationSRDAO();
                sanitationSRDAO.deleteAllFromTable();
                sanitationSRS.forEach(sanitationSRDAO::insert);
            }
            if(labSystemImport.isSelected()) {
                List<LabSystemSR> labSystemSRS = CSVFacade.read(LabSystemSR.class, savedFile.getPath() + File.separator + LAB_SYSTEM_CSV);
                LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAO();
                labSystemSRDAO.deleteAllFromTable();
                labSystemSRS.forEach(labSystemSRDAO::insert);
            }
            if(facilityMaintenanceImport.isSelected()) {
                List<FacilityMaintenanceSR> facilityMaintenanceSRS = CSVFacade.read(FacilityMaintenanceSR.class, savedFile.getPath() + File.separator + FACILITY_MAINTENANCE_CSV);
                FacilityMaintenanceSRDAO facilityMaintenanceSRDAO = new FacilityMaintenanceSRDAO();
                facilityMaintenanceSRDAO.deleteAllFromTable();
                facilityMaintenanceSRS.forEach(facilityMaintenanceSRDAO::insert);
            }
            if(securityImport.isSelected()) {
                List<SecuritySR> securitySRS = CSVFacade.read(SecuritySR.class, savedFile.getPath() + File.separator + SECURITY_CSV);
                SecuritySRDAO securitySRDAO = new SecuritySRDAO();
                securitySRDAO.deleteAllFromTable();
                securitySRS.forEach(securitySRDAO::insert);
            }
            if(deliveryImport.isSelected()) {
                List<DeliverySystemSR> deliverySystemSRS = CSVFacade.read(DeliverySystemSR.class, savedFile.getPath() + File.separator + DELIVERY_SYSTEM_CSV);
                DeliverySystemSRDAO deliverySystemSRDAO = new DeliverySystemSRDAO();
                deliverySystemSRDAO.deleteAllFromTable();
                deliverySystemSRS.forEach(deliverySystemSRDAO::insert);
            }
            if(patientTransportImport.isSelected()) {
                List<PatientTransportSR> patientTransportSRS = CSVFacade.read(PatientTransportSR.class, savedFile.getPath() + File.separator + PATIENT_TRANSPORT_CSV);
                PatientTransportSRDAO patientTransportSRDAO = new PatientTransportSRDAO();
                patientTransportSRDAO.deleteAllFromTable();
                patientTransportSRS.forEach(patientTransportSRDAO::insert);
            }
            if(laundryImport.isSelected()) {
                List<LaundrySR> laundrySRS = CSVFacade.read(LaundrySR.class, savedFile.getPath() + File.separator + LAUNDRY_CSV);
                LaundrySRDAO laundrySRDAO = new LaundrySRDAO();
                laundrySRDAO.deleteAllFromTable();
                laundrySRS.forEach(laundrySRDAO::insert);
            }
            if(translatorImport.isSelected()) {
                List<TranslatorSR> translatorSRS = CSVFacade.read(TranslatorSR.class, savedFile.getPath() + File.separator + TRANSLATOR_CSV);
                TranslatorSRDAO translatorSRDAO = new TranslatorSRDAO();
                translatorSRDAO.deleteAllFromTable();
                translatorSRS.forEach(translatorSRDAO::insert);
            }
        }
    }
    
    /**
     * Bulk changes for checkboxes and button selection options.
     * @param selected True to select the elements.
     * @param justCheckBoxes Just the checkboxes or everything.
     */
    void setFieldsImport(boolean selected, boolean justCheckBoxes){
        //Map
        floorsImport.setSelected(selected);
        locationsImport.setSelected(selected);

        //Service Requests
        facilityMaintenanceImport.setSelected(selected);
        medicalEquipmentImport.setSelected(selected);
        medicineDeliveryImport.setSelected(selected);
        sanitationImport.setSelected(selected);
        securityImport.setSelected(selected);
        labSystemImport.setSelected(selected);
        deliveryImport.setSelected(selected);
        laundryImport.setSelected(selected);
        translatorImport.setSelected(selected);
        patientTransportImport.setSelected(selected);
    
        medicalEquipmentEntityImport.setSelected(selected);
        employeesImport.setSelected(selected);
        patientImport.setSelected(selected);
    
    
        if (!justCheckBoxes) {
            selectAllImport.setSelected(selected);
            importText.setText("");
        }
    }
    
    /**
     * Bulk changes for checkboxes and button selection options.
     * @param selected True to select the elements.
     * @param justCheckBoxes Just the checkboxes or everything.
     */
    void setFieldsExport(boolean selected, boolean justCheckBoxes){
        //Map
        floorsExport.setSelected(selected);
        locationsExport.setSelected(selected);
        
        //Service Requests
        facilityMaintenanceExport.setSelected(selected);
        medicalEquipmentExport.setSelected(selected);
        medicineDeliveryExport.setSelected(selected);
        sanitationExport.setSelected(selected);
        securityExport.setSelected(selected);
        labSystemExport.setSelected(selected);
        deliveryExport.setSelected(selected);
        laundryExport.setSelected(selected);
        translatorExport.setSelected(selected);
        patientTransportExport.setSelected(selected);
        
        medicalEquipmentEntityExport.setSelected(selected);
        employeesExport.setSelected(selected);
        patientExport.setSelected(selected);
        
        if (!justCheckBoxes) {
            selectAllExport.setSelected(selected);
            exportText.setText("");
        }
    }


}
