package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

public class MedicalEquipmentSRFactory extends ServiceRequestFactory<MedicalEquipmentSR>  {
    public MedicalEquipmentSR create() {

        MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;

        MedicalEquipment testMedEquip = new MedicalEquipment();
        MedicalEquipmentDAO medEquipDAO = new MedicalEquipmentDAO();
        medEquipDAO.insert(testMedEquip);
        
        serviceRequest.setRequestType(requestType);
        serviceRequest.setEquipment(testMedEquip);
        //this code is here to prevent medical equipment SR tests from breaking, we'll be removing this code once medical equipment is fully finished
        serviceRequest.setEquipmentType(MedicalEquipment.EquipmentType.values()[generator.nextInt(MedicalEquipment.EquipmentType.values().length)]);
        return super.create(serviceRequest);
    }
}
