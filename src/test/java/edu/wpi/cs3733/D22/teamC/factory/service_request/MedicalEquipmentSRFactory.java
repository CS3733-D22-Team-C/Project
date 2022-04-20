package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

public class MedicalEquipmentSRFactory extends ServiceRequestFactory<MedicalEquipmentSR>  {
    public MedicalEquipmentSR create() {

        MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String equipID = String.valueOf(generator.nextInt(200000));

        serviceRequest.setRequestType(requestType);
        serviceRequest.setEquipmentID(equipID);
        //this code is here to prevent medical equipment SR tests from breaking, we'll be removing this code once medical equipment is fully finished
        serviceRequest.setEquipmentType(MedicalEquipment.EquipmentType.values()[generator.nextInt(MedicalEquipment.EquipmentType.values().length)]);
        return super.create(serviceRequest);
    }
}
