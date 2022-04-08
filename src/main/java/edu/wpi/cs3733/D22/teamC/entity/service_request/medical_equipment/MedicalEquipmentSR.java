package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;
@Entity
@Table(name = " Medical_Equipment_SR")
public class MedicalEquipmentSR extends ServiceRequest {
    @Enumerated(EnumType.STRING)
    protected EquipmentType equipmentType;

    protected String equipmentID;       // TODO: Link to Medical Equipment

    @Enumerated(EnumType.STRING)
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

    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public EquipmentStatus getEquipmentStatus(){ return equipmentStatus; }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }
}
