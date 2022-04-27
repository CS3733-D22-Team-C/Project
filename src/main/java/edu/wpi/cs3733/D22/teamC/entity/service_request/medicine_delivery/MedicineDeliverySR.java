package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "MEDICINE_DELIVERY_SR")
public class MedicineDeliverySR extends ServiceRequest {

    @Column (name = "Medicine")
    protected String medicine;

    @Column (name = "Dosage")
    protected String dosage;
    
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Patient patient;
    
    public MedicineDeliverySR() {}
    
    public MedicineDeliverySR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MedicineDeliverySR that = (MedicineDeliverySR) o;
        return medicine.equals(that.medicine)
                && dosage.equals(that.dosage)
                && patient.getID().equals(that.patient.getID());
    }

}
