package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

public class MedicalEquipment {
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

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EquipmentStatus getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public MedicalEquipment(EquipmentType equipmentType, EquipmentStatus equipmentStatus) {
        this.equipmentType = equipmentType;
        this.equipmentStatus = equipmentStatus;
    }
}
