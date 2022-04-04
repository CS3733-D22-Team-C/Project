package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

public class MedicalEquipment {
    protected int equipID;
    protected int locationID;
    protected EquipmentType equipmentType;
    protected EquipmentStatus equipmentStatus;

    public enum EquipmentType {
        Bed,
        Recliner,
        Portable_X_Ray,
        Infusion_Pump
    }

    public enum EquipmentStatus {
        Available,
        Unavailable,
        Dirty
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public MedicalEquipment(int equipID) { this.equipID = equipID}

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EquipmentStatus getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getEquipID() {
        return equipID;
    }

    public void setEquipID(int equipID) {
        this.equipID = equipID;
    }
}
