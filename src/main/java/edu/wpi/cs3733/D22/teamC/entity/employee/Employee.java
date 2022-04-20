package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements IDEntity {

    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "EmailID")
    private String emailID;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role;

    @Column(name = "IsAdmin")
    private boolean isAdmin;

    @Column(name = "Username", unique = true)
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "ImageSrc")
    private String imageSrc;

    @Lob
    @Column(name = "Image")
    private byte[] image;

    public enum Role{
        Doctor,
        Nurse
    }

    public Employee(){
        ID = UUID.randomUUID().toString();
    }

    public Employee(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String employeeID) {
        this.ID = employeeID;
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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID.equals(employee.ID)
                && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName)
                && emailID.equals(employee.emailID)
                && phone.equals(employee.phone)
                && address.equals(employee.address)
                && role == employee.role
                && isAdmin == employee.isAdmin
                && username.equals(employee.username)
                && password.equals(employee.password);
    }

    @Override
    public String toString(){
        return this.firstName + " " + this.lastName;
    }

}
