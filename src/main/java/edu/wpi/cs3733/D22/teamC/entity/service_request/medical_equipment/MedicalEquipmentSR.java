package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class MedicalEquipmentSR extends ServiceRequest {
    protected String equipmentType;     // TODO: Make Enum
    protected String equipmentID;       // TODO: Link to Medical Equipment

    public MedicalEquipmentSR() {}
    
    public MedicalEquipmentSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }
    
    //For the table
    enum EquipmentType {
        Bed,            //1
        Recliner,       //2
        Portable_X_Ray, //3
        Infusion_Pump;  //4
    }


    enum EquipmentStatus {
        Available,    //0
        Unavailable,  //1
        Dirty;        //2
    }

        public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        if (equipmentType == null) return;

        if(equipmentType.contains("Bed"))
            this.equipmentType = "Bed";
        else if(equipmentType.contains("Recliner"))
            this.equipmentType = "Recliner";
        else if(equipmentType.contains("Portable X-Ray"))
            this.equipmentType = "Portable X-Ray";
        else
            this.equipmentType = "Infusion Pump";
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    //Not useful function, still implemented in controllers
    public int getEquipEnum(String type)
    {
        EquipmentType number;
        if(type.contains("Bed"))
            number = EquipmentType.Bed;
        else if(type.contains("Recliner"))
            number = EquipmentType.Recliner;
        else if(type.contains("Portable X-Ray"))
            number = EquipmentType.Portable_X_Ray;
        else
            number = EquipmentType.Infusion_Pump;

        switch (number) {
            case Bed:
                return 1;
            case Recliner:
                return 2;
            case Portable_X_Ray:
                return 3;
            case Infusion_Pump:
                return 4;
            default:
                return -1;
        }
    }
}
