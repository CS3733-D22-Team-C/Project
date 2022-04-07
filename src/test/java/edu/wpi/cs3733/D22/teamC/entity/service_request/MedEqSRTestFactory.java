package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

public class MedEqSRTestFactory extends ServiceRequestTestFactory<MedicalEquipmentSR> {
    public MedicalEquipmentSR create() {

        MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String equipID = String.valueOf(getGenerator().nextInt(200000));

        serviceRequest.setRequestType(requestType);
        serviceRequest.setEquipmentID(equipID);
        return super.create(serviceRequest);
    }
}
