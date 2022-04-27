package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "MEDICAL_EQUIPMENT")
public class MedicalEquipment implements IDEntity {
    @Id
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne
    @JoinColumn(name = "LocationID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "fk_EquipLocation",
            foreignKeyDefinition = "FOREIGN KEY (LocationID) REFERENCES LOCATION (ID) ON DELETE SET NULL"))
    private Location location;
    
    @Column(name = "TypeNumber")
    private int typeNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "EquipType")
    private EquipmentType equipmentType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
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
        ID = UUID.randomUUID().toString();

    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String equipID) {
        this.ID = equipID;
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
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalEquipment equipment = (MedicalEquipment) o;
        return ID.equals(equipment.ID) 
                && location.getID().equals(equipment.location.getID()) 
                && equipmentType == equipment.equipmentType 
                && equipmentStatus == equipment.equipmentStatus 
                && typeNumber == equipment.typeNumber;
    }

    @Override
    public String toString() {
        return equipmentType.toString() + " #" + typeNumber;
    }
}


