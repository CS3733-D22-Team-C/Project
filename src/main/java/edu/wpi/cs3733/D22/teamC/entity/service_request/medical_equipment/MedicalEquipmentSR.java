package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class MedicalEquipmentSR extends ServiceRequest {

    protected String equipmentID;       // TODO: Link to Medical Equipment

    public MedicalEquipmentSR() {}
    
    public MedicalEquipmentSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }


    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }
}
