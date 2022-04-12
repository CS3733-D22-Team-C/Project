package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
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
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.facility_maintenance.FacilityMaintenanceSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.lab_system.LabSystemSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medical_equipment.MedicalEquipmentSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medicine_delivery.MedicineDeliverySRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.sanitation.SanitationSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.security.SecuritySRCSVWriter;

import java.util.List;

/**
 * Facade wrapper to handle all CSV writing operations for Service Requests.
 */
public class ServiceRequestWriter {
    
    // TODO: write all in one file
    
    /**
     * Write a CSV of the data for one particular type of Service Request
     * @param serviceRequestType The type of service request.
     */
    public void writeOne(ServiceRequest.RequestType serviceRequestType) {
        switch (serviceRequestType) {
            case Facility_Maintenance:
                writeFacilitySR();
                break;
            case Lab_System:
                writeLabSystemSR();
                break;
            case Medical_Equipment:
                writeMedicalEquipmentSR();
                break;
            case Medicine_Delivery:
                writeMedicineDeliverySR();
                break;
            case Sanitation:
                writeSanitationSR();
                break;
            case Security:
                writeSecuritySR();
                break;
        }
    }
    
    /**
     * Write all ServiceRequest database information into separate files.
     */
    public void writeAll() {
        writeFacilitySR();
        writeLabSystemSR();
        writeMedicalEquipmentSR();
        writeMedicineDeliverySR();
        writeSanitationSR();
        writeSecuritySR();
    }
    
    protected void writeFacilitySR() {
        FacilityMaintenanceSRCSVWriter csvWriter = new FacilityMaintenanceSRCSVWriter();
        FacilityMaintenanceSRDAO serviceRequestDAO = new FacilityMaintenanceSRDAO();
        List<FacilityMaintenanceSR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("FacilityReq.csv", serviceRequests);
        }
    }
    
    protected void writeLabSystemSR() {
        LabSystemSRCSVWriter csvWriter = new LabSystemSRCSVWriter();
        LabSystemSRDAO serviceRequestDAO = new LabSystemSRDAO();
        List<LabSystemSR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("LabSystemReq.csv", serviceRequests);
        }
    }
    
    protected void writeMedicalEquipmentSR() {
        MedicalEquipmentSRCSVWriter csvWriter = new MedicalEquipmentSRCSVWriter();
        MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
        List<MedicalEquipmentSR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("MedEquipReq.csv", serviceRequests);
        }
    }
    
    protected void writeMedicineDeliverySR() {
        MedicineDeliverySRCSVWriter csvWriter = new MedicineDeliverySRCSVWriter();
        MedicineDeliverySRDAO serviceRequestDAO = new MedicineDeliverySRDAO();
        List<MedicineDeliverySR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("MedDeliveryReq.csv", serviceRequests);
        }
    }
    
    protected void writeSanitationSR() {
        SanitationSRCSVWriter csvWriter = new SanitationSRCSVWriter();
        SanitationSRDAO serviceRequestDAO = new SanitationSRDAO();
        List<SanitationSR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("SanitationReq.csv", serviceRequests);
        }
    }
    
    protected void writeSecuritySR() {
        SecuritySRCSVWriter csvWriter = new SecuritySRCSVWriter();
        SecuritySRDAO serviceRequestDAO = new SecuritySRDAO();
        List<SecuritySR> serviceRequests = serviceRequestDAO.getAll();
        if (serviceRequests != null){
            csvWriter.writeFile("SecurityReq.csv", serviceRequests);
        }
    }
    
}
