package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class MedicalEquipmentSR extends ServiceRequest {
    protected EquipmentType equipmentType;
    protected String equipmentID;       // TODO: Link to Medical Equipment

    public MedicalEquipmentSR() {}
    
    public MedicalEquipmentSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }
    
    //For the table
    public enum EquipmentType {
        Bed,            //0
        Recliner,       //1
        Portable_X_Ray, //3
        Infusion_Pump;  //4
    }


    public enum EquipmentStatus {
        Available,    //0
        Unavailable,  //1
        Dirty;        //2
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    //Not useful function, still implemented in controllers
//    public int getEquipEnum(String type)
//    {
//        EquipmentType number;
//        if(type.contains("Bed"))
//            number = EquipmentType.Bed;
//        else if(type.contains("Recliner"))
//            number = EquipmentType.Recliner;
//        else if(type.contains("Portable X-Ray"))
//            number = EquipmentType.Portable_X_Ray;
//        else
//            number = EquipmentType.Infusion_Pump;
//
//        switch (number) {
//            case Bed:
//                return 1;
//            case Recliner:
//                return 2;
//            case Portable_X_Ray:
//                return 3;
//            case Infusion_Pump:
//                return 4;
//            default:
//                return -1;
//        }
//    }
}
