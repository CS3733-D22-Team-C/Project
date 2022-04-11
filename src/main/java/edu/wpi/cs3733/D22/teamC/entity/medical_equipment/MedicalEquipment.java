package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "MEDICAL_EQUIPMENT")
public class MedicalEquipment {
    @Id
    @Column(name = "ID")
    private String equipID;
    
    @Column(name = "LocationID")
    private String locationID;
    
    @Column(name = "TypeNumber")
    private int typeNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "equipType")
    private EquipmentType equipmentType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
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
    
    public MedicalEquipment() {
        equipID = UUID.randomUUID().toString();
    }
    
    public String getEquipID() {
        return equipID;
    }
    
    public void setEquipID(String equipID) {
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
    
    public String getLocationID() {
        return locationID;
    }
    
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalEquipment equipment = (MedicalEquipment) o;
        return equipID.equals(equipment.equipID) 
                && locationID == equipment.locationID 
                && equipmentType == equipment.equipmentType 
                && equipmentStatus == equipment.equipmentStatus 
                && typeNumber == equipment.typeNumber;
    }
}


