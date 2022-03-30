package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class MedicalEquipmentServiceRequest extends ServiceRequest {
    protected String equipmentType;     // TODO: Make Enum
    protected String equipmentID;       // TODO: Link to Medical Equipment

    //For the table
    enum equipEnum {
        BED, //1
        RECLINER, //2
        PORTABLE_XRAY, //3
        INFUSION_PUMP; //4
    }

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

    public int getEquipEnum(String type)
    {
        equipEnum number;
        if(type.contains("Bed"))
            number = equipEnum.BED;
        else if(type.contains("Recliner"))
            number = equipEnum.RECLINER;
        else if(type.contains("Portable X-Ray"))
            number = equipEnum.PORTABLE_XRAY;
        else
            number = equipEnum.INFUSION_PUMP;

        switch (number) {
            case BED:
                return 1;
            case RECLINER:
                return 2;
            case PORTABLE_XRAY:
                return 3;
            case INFUSION_PUMP:
                return 4;
            default:
                return -1;
        }
    }
}
