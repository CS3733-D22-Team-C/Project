package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(name = "MEDICAL_EQUIPMENT")
public class MedicalEquipment {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int equipID;

    @Column (name = "LocationID")
    private int locationID;

    @Column(name = "TypeNumber")
    private int typeNumber;

    @Enumerated(EnumType.STRING)
    @Column (name = "equipType")
    private EquipmentType equipmentType;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private EquipmentStatus equipmentStatus;

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

    public MedicalEquipment(int locationID, EquipmentType equipmentType, EquipmentStatus status) {
        this.locationID = locationID;
//        this.typeNumber = typeNumber;
        this.equipmentType = equipmentType;
        this.equipmentStatus = status;
    }

    @Deprecated
    public MedicalEquipment(int equipID, int locationID, int typeNumber, EquipmentType equipmentType, EquipmentStatus status) {
        this.equipID = equipID;
        this.locationID = locationID;
        this.typeNumber = typeNumber;
        this.equipmentType = equipmentType;
        this.equipmentStatus = status;
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
        return equipmentStatus;
    }

    public void setStatus(EquipmentStatus status) {
        this.equipmentStatus = status;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalEquipment equipment = (MedicalEquipment) o;
        return equipID == equipment.equipID && locationID == equipment.locationID && equipmentType == equipment.equipmentType && equipmentStatus == equipment.equipmentStatus && typeNumber == equipment.typeNumber;
    }
}


