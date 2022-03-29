package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class MedicalEquipmentServiceRequest extends ServiceRequest {
    protected String equipmentType;     // TODO: Make Enum
    protected String equipmentID;       // TODO: Link to Medical Equipment

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }
}
