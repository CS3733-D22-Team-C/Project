package edu.wpi.cs3733.D22.teamC.controller.location.map;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVFacade;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.io.File;
import java.util.List;

public class CSVComponent {

    //Checkboxes
    @FXML private MFXCheckbox employeesExport;
    @FXML private MFXCheckbox employeesImport;
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



    //Textfields
    @FXML private TextField exportText;
    @FXML private TextField importText;

    File savedFile;

    public static final String FLOOR_CSV = "TowerFloors.csv";
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
        entitiesChecked(true);
        resetFields();
    }

    @FXML
    void clickImportFiles(ActionEvent event) {
        entitiesChecked(false);
        resetFields();

    }

    void chooseCSV(TextField csvName) {
        // Create a file chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Export CSV File");
        File file = directoryChooser.showDialog(App.instance.getStage());
        csvName.setText(":\\ ...\\" + file.getName());
        savedFile = file;

        //Path to the files
        for (int i = 0; i < savedFile.list().length; i++) {
            System.out.println(savedFile.getPath() + "\\" + savedFile.list()[i]);
        }
    }

    void entitiesChecked(boolean isExport){
        if(isExport){
            //Map
            if(floorsExport.isSelected()){
                FloorDAO floorDAO = new FloorDAO();
                List<Floor> floors = floorDAO.getAll();
                CSVFacade.write(Floor.class, savedFile.getPath() + "\\" + FLOOR_CSV, floors);
            }
            if(locationsExport.isSelected()) {
                LocationDAO locationDAO = new LocationDAO();
                List<Location> locations = locationDAO.getAll();
                CSVFacade.write(Location.class, savedFile.getPath() + "\\" + LOCATION_CSV, locations);
            }

            //Service Request
            if(medicalEquipmentExport.isSelected()) {
                MedicalEquipmentSRDAO medicalEquipmentSRDAO = new MedicalEquipmentSRDAO();
                List<MedicalEquipmentSR> medicalEquipmentSRS = medicalEquipmentSRDAO.getAll();
                CSVFacade.write(MedicalEquipmentSR.class, savedFile.getPath() + "\\" + MEDICAL_EQUIPMENT_CSV, medicalEquipmentSRS);
            }
            if(medicineDeliveryExport.isSelected()) {
                MedicineDeliverySRDAO medicineDeliverySRDAO = new MedicineDeliverySRDAO();
                List<MedicineDeliverySR> medicineDeliverySRS = medicineDeliverySRDAO.getAll();
                CSVFacade.write(MedicineDeliverySR.class, savedFile.getPath() + "\\" + MEDICINE_DELIVERY_CSV, medicineDeliverySRS);
            }
            if(sanitationExport.isSelected()) {
                SanitationSRDAO sanitationSRDAO = new SanitationSRDAO();
                List<SanitationSR> sanitationSRS = sanitationSRDAO.getAll();
                CSVFacade.write(SanitationSR.class, savedFile.getPath() + "\\" + SANITATION_CSV, sanitationSRS);
            }
            if(labSystemExport.isSelected()) {
                LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAO();
                List<LabSystemSR> labSystemSRS = labSystemSRDAO.getAll();
                CSVFacade.write(LabSystemSR.class, savedFile.getPath() + "\\" + LAB_SYSTEM_CSV, labSystemSRS);
            }
            if(facilityMaintenanceExport.isSelected()) {
                FacilityMaintenanceSRDAO facilityMaintenanceSRDAO = new FacilityMaintenanceSRDAO();
                List<FacilityMaintenanceSR> facilityMaintenanceSRS = facilityMaintenanceSRDAO.getAll();
                CSVFacade.write(FacilityMaintenanceSR.class, savedFile.getPath() + "\\" + FACILITY_MAINTENANCE_CSV, facilityMaintenanceSRS);
            }
            if(securityExport.isSelected()) {
                SecuritySRDAO securitySRDAO = new SecuritySRDAO();
                List<SecuritySR> securitySRS = securitySRDAO.getAll();
                CSVFacade.write(SecuritySR.class, savedFile.getPath() + "\\" + SECURITY_CSV, securitySRS);
            }
            if(deliveryExport.isSelected()) {
                DeliverySystemSRDAO deliverySystemSRDAO = new DeliverySystemSRDAO();
                List<DeliverySystemSR> deliverySystemSRS = deliverySystemSRDAO.getAll();
                CSVFacade.write(DeliverySystemSR.class, savedFile.getPath() + "\\" + DELIVERY_SYSTEM_CSV, deliverySystemSRS);
            }

            //Employee
            if(employeesExport.isSelected()) {
                EmployeeDAO employeeDAO = new EmployeeDAO();
                List<Employee> employees = employeeDAO.getAll();
                CSVFacade.write(Employee.class, savedFile.getPath() + "\\" + EMPLOYEE_CSV, employees);
            }

            //Medical Equipment Entity
            if(medicalEquipmentEntityExport.isSelected()) {
                MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
                List<MedicalEquipment> medicalEquipments = medicalEquipmentDAO.getAll();
                CSVFacade.write(MedicalEquipment.class, savedFile.getPath() + "\\" + MEDICAL_EQUIPMENT_ENTITY_CSV, medicalEquipments);
            }
        }
        else {
            //Map
            if(floorsImport.isSelected()){
                List<Floor> floors = CSVFacade.read(Floor.class, savedFile.getPath() + "\\" + FLOOR_CSV);
                FloorDAO floorDAO = new FloorDAO();
                floorDAO.deleteAllFromTable();
                floors.forEach(floorDAO::insert);
            }
            if(locationsImport.isSelected()) {
                List<Location> locations = CSVFacade.read(Location.class, savedFile.getPath() + "\\" + LOCATION_CSV);
                LocationDAO locationDAO = new LocationDAO();
                locationDAO.deleteAllFromTable();
                locations.forEach(locationDAO::insert);
            }


            //Employee
            if(employeesImport.isSelected()) {
                List<Employee> employees = CSVFacade.read(Employee.class, savedFile.getPath() + "\\" + EMPLOYEE_CSV);
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.deleteAllFromTable();
                employees.forEach(employeeDAO::insert);
            }

            //Medical Equipment Entity
            if(medicalEquipmentEntityImport.isSelected()) {
                List<MedicalEquipment> medicalEquipments = CSVFacade.read(MedicalEquipment.class, savedFile.getPath() + "\\" + MEDICAL_EQUIPMENT_ENTITY_CSV);
                MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
                medicalEquipmentDAO.deleteAllFromTable();
                medicalEquipments.forEach(medicalEquipmentDAO::insert);
            }

            //Service Request
            if(medicalEquipmentImport.isSelected()) {
                List<MedicalEquipmentSR> medicalEquipmentSRS = CSVFacade.read(MedicalEquipmentSR.class, savedFile.getPath() + "\\" + MEDICAL_EQUIPMENT_CSV);
                MedicalEquipmentSRDAO medicalEquipmentSRDAO = new MedicalEquipmentSRDAO();
                medicalEquipmentSRDAO.deleteAllFromTable();
                medicalEquipmentSRS.forEach(medicalEquipmentSRDAO::insert);
            }
            if(medicineDeliveryImport.isSelected()) {
                List<MedicineDeliverySR> medicineDeliverySRS = CSVFacade.read(MedicineDeliverySR.class, savedFile.getPath() + "\\" + MEDICINE_DELIVERY_CSV);
                MedicineDeliverySRDAO medicineDeliverySRDAO = new MedicineDeliverySRDAO();
                medicineDeliverySRDAO.deleteAllFromTable();
                medicineDeliverySRS.forEach(medicineDeliverySRDAO::insert);
            }
            if(sanitationImport.isSelected()) {
                List<SanitationSR> sanitationSRS = CSVFacade.read(SanitationSR.class, savedFile.getPath() + "\\" + SANITATION_CSV);
                SanitationSRDAO sanitationSRDAO = new SanitationSRDAO();
                sanitationSRDAO.deleteAllFromTable();
                sanitationSRS.forEach(sanitationSRDAO::insert);
            }
            if(labSystemImport.isSelected()) {
                List<LabSystemSR> labSystemSRS = CSVFacade.read(LabSystemSR.class, savedFile.getPath() + "\\" + LAB_SYSTEM_CSV);
                LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAO();
                labSystemSRDAO.deleteAllFromTable();
                labSystemSRS.forEach(labSystemSRDAO::insert);
            }
            if(facilityMaintenanceImport.isSelected()) {
                List<FacilityMaintenanceSR> facilityMaintenanceSRS = CSVFacade.read(FacilityMaintenanceSR.class, savedFile.getPath() + "\\" + FACILITY_MAINTENANCE_CSV);
                FacilityMaintenanceSRDAO facilityMaintenanceSRDAO = new FacilityMaintenanceSRDAO();
                facilityMaintenanceSRDAO.deleteAllFromTable();
                facilityMaintenanceSRS.forEach(facilityMaintenanceSRDAO::insert);
            }
            if(securityImport.isSelected()) {
                List<SecuritySR> securitySRS = CSVFacade.read(SecuritySR.class, savedFile.getPath() + "\\" + SECURITY_CSV);
                SecuritySRDAO securitySRDAO = new SecuritySRDAO();
                securitySRDAO.deleteAllFromTable();
                securitySRS.forEach(securitySRDAO::insert);
            }
            if(deliveryImport.isSelected()) {
                List<DeliverySystemSR> deliverySystemSRS = CSVFacade.read(DeliverySystemSR.class, savedFile.getPath() + "\\" + DELIVERY_SYSTEM_CSV);
                DeliverySystemSRDAO deliverySystemSRDAO = new DeliverySystemSRDAO();
                deliverySystemSRDAO.deleteAllFromTable();
                deliverySystemSRS.forEach(deliverySystemSRDAO::insert);
            }
        }
    }

    void resetFields(){

        //Map
        floorsExport.setSelected(false);
        floorsImport.setSelected(false);
        locationsExport.setSelected(false);
        locationsImport.setSelected(false);

        //Service Requests
        facilityMaintenanceExport.setSelected(false);
        facilityMaintenanceImport.setSelected(false);
        medicalEquipmentExport.setSelected(false);
        medicalEquipmentImport.setSelected(false);
        medicineDeliveryExport.setSelected(false);
        medicineDeliveryImport.setSelected(false);
        sanitationExport.setSelected(false);
        sanitationImport.setSelected(false);
        securityExport.setSelected(false);
        securityImport.setSelected(false);
        labSystemExport.setSelected(false);
        labSystemImport.setSelected(false);
        deliveryExport.setSelected(false);
        deliveryImport.setSelected(false);

        medicalEquipmentEntityImport.setSelected(false);
        medicalEquipmentEntityExport.setSelected(false);

        employeesExport.setSelected(false);
        employeesImport.setSelected(false);

        exportText.setText("");
        importText.setText("");
    }


}
