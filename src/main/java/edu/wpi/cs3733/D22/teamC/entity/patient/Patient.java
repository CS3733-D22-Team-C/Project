package edu.wpi.cs3733.D22.teamC.entity.patient;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "PATIENT")
public class Patient implements IDEntity {

    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name ="FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB")
    private Date DOB;

    @Column(name = "Location")
    private String locationID;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "EmergencyContact")
    private String emergencyContact;

    public Patient(){ ID = UUID.randomUUID().toString();}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return ID.equals(patient.ID)
                && firstName.equals(patient.firstName)
                && lastName.equals(patient.lastName)
                && emergencyContact.equals(patient.emergencyContact)
                && phone.equals(patient.phone)
                && DOB.equals(patient.DOB)
                && locationID.equals(patient.locationID);
    }
}
