package edu.wpi.cs3733.D22.teamC.entity.service_request.translator;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name = "TRANSLATOR_SR")
public class TranslatorSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    protected Language language;
    
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "ID",
            foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY fk_patient REFERENCES PATIENT (ID) ON DELETE SET NULL"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Patient patient;


    public TranslatorSR(){}

    public TranslatorSR(ServiceRequest serviceRequest){super(serviceRequest);}

    public enum Language{
        English,
        Hindi,
        German,
        Spanish,
        French,
        MorseCode,
        SignLanguage
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        TranslatorSR that = (TranslatorSR) o;
        return language == (that.language) && patient.getID().equals(that.patient.getID());
    }
}
