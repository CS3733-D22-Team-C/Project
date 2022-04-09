package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Medical_Equipment")
public class MedicalEquipment {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int equipID;

    @Column (name = "LocationID")
    private int locationID;

    @Column(name = "Type Number")
    private int typeNumber;

    @Column (name = "equipType")
    private EquipmentType equipmentType;

    @Column (name = "status")
    private EquipmentStatus status;

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

    public MedicalEquipment() {}

    public MedicalEquipment(int locationID, int typeNumber, EquipmentType equipmentType, EquipmentStatus status) {
        this.locationID = locationID;
        this.typeNumber = typeNumber;
        this.equipmentType = equipmentType;
        this.status = status;
    }

    public MedicalEquipment(int equipID, int typeNumber, EquipmentType equipmentType, EquipmentStatus status, int locationID) {
        this.equipID = equipID;
        this.typeNumber = typeNumber;
        this.equipmentType = equipmentType;
        this.status = status;
        this.locationID = locationID;
    }

    public int getEquipID() {
        return equipID;
    }

    public void setEquipID(int equipID) {
        this.equipID = equipID;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}
