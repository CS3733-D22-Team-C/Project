package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

public class MedicalEquipmentSRFactory extends ServiceRequestFactory<MedicalEquipmentSR> {
    public MedicalEquipmentSR create() {

        MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String equipID = String.valueOf(getGenerator().nextInt(200000));

        serviceRequest.setRequestType(requestType);
        serviceRequest.setEquipmentID(equipID);
        //this code is here to prevent medical equipment SR tests from breaking, we'll be removing this code once medical equipment is fully finished
        serviceRequest.setEquipmentType(MedicalEquipmentSR.EquipmentType.values()[getGenerator().nextInt(4)]);
        return super.create(serviceRequest);
    }
}
