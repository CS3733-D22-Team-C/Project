package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.facility_maintenance.FacilityMaintenanceSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.lab_system.LabSystemSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medical_equipment.MedicalEquipmentSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medicine_delivery.MedicineDeliverySRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.sanitation.SanitationSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.security.SecuritySRCSVReader;

import java.util.List;

/**
 * Facade wrapper to handle all CSV reading operations for Service Requests.
 */
public class ServiceRequestReader {
    
    // TODO: read all from one file
    
    /**
     * Read a CSV of the data for one particular type of Service Request
     * @param serviceRequestType The type of service request.
     */
    public void readOne(ServiceRequest.RequestType serviceRequestType) {
        switch (serviceRequestType) {
            case Facility_Maintenance:
                readFacilitySR();
                break;
            case Lab_System:
                readLabSystemSR();
                break;
            case Medical_Equipment:
                readMedicalEquipmentSR();
                break;
            case Medicine_Delivery:
                readMedicineDeliverySR();
                break;
            case Sanitation:
                readSanitationSR();
                break;
            case Security:
                readSecuritySR();
                break;
        }
    }
    
    /**
     * Read all ServiceRequest database information from separate files.
     */
    public void readAll() {
        readFacilitySR();
        readLabSystemSR();
        readMedicalEquipmentSR();
        readMedicineDeliverySR();
        readSanitationSR();
        readSecuritySR();
    }
    
    protected void readFacilitySR() {
        FacilityMaintenanceSRCSVReader csvReader = new FacilityMaintenanceSRCSVReader();
        List<FacilityMaintenanceSR> serviceRequests = csvReader.readFile("FacilityReq.csv");
        if(serviceRequests != null){
            MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
            for(FacilityMaintenanceSR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
    
    protected void readLabSystemSR() {
        LabSystemSRCSVReader csvReader = new LabSystemSRCSVReader();
        List<LabSystemSR> serviceRequests = csvReader.readFile("LabSystemReq.csv");
        if(serviceRequests != null){
            MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
            for(LabSystemSR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
    
    protected void readMedicalEquipmentSR() {
        MedicalEquipmentSRCSVReader csvReader = new MedicalEquipmentSRCSVReader();
        List<MedicalEquipmentSR> serviceRequests = csvReader.readFile("MedEquipReq.csv");
        if(serviceRequests != null){
            MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
            for(MedicalEquipmentSR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
    
    protected void readMedicineDeliverySR() {
        MedicineDeliverySRCSVReader csvReader = new MedicineDeliverySRCSVReader();
        List<MedicineDeliverySR> serviceRequests = csvReader.readFile("MedDeliveryReq.csv");
        if(serviceRequests != null){
            MedicineDeliverySRDAO serviceRequestDAO = new MedicineDeliverySRDAO();
            for(MedicineDeliverySR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
    
    protected void readSanitationSR() {
        SanitationSRCSVReader csvReader = new SanitationSRCSVReader();
        List<SanitationSR> serviceRequests = csvReader.readFile("SanitationReq.csv");
        if(serviceRequests != null){
            MedicineDeliverySRDAO serviceRequestDAO = new MedicineDeliverySRDAO();
            for(SanitationSR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
    
    protected void readSecuritySR() {
        SecuritySRCSVReader csvReader = new SecuritySRCSVReader();
        List<SecuritySR> serviceRequests = csvReader.readFile("SecurityReq.csv");
        if(serviceRequests != null){
            MedicineDeliverySRDAO serviceRequestDAO = new MedicineDeliverySRDAO();
            for(SecuritySR sr : serviceRequests){
                serviceRequestDAO.insert(sr);
            }
        }
    }
}
