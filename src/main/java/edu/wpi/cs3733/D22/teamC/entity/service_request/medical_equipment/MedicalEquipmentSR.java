package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "MEDICAL_EQUIPMENT_SR")
public class MedicalEquipmentSR extends ServiceRequest {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "EquipType")
    protected MedicalEquipment.EquipmentType equipmentType;
    
    @ManyToOne
    @JoinColumn(name = "EquipID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected MedicalEquipment equipment;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "EquipStatus")
    protected EquipmentStatus equipmentStatus;


    public enum EquipmentStatus {
        Available,
        Unavailable,
        Dirty
    }
    
    public MedicalEquipmentSR() {}
    
    public MedicalEquipmentSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }

    public MedicalEquipment.EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(MedicalEquipment.EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public MedicalEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(MedicalEquipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentStatus getEquipmentStatus(){ return equipmentStatus; }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MedicalEquipmentSR that = (MedicalEquipmentSR) o;
        return equipmentType == that.equipmentType
                && equipment.getID().equals(that.equipment.getID())
                && equipmentStatus == that.equipmentStatus;
    }
}
