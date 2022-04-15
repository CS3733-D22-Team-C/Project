package edu.wpi.cs3733.D22.teamC.entity.service_request.translator;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;

@Entity
@Table(name = "TRANSLATOR_SR")
public class TranslatorSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "Language")
    private Language language;

    @Column(name ="PatientID") //TODO: change this to a patient object
    private String patientID;


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

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        TranslatorSR that = (TranslatorSR) o;
        return language == (that.language) && patientID.equals(that.patientID);
    }
}
