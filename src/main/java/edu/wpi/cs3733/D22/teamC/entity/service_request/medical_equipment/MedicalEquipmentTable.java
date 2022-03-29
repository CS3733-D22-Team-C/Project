package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class MedicalEquipmentTable extends RecursiveTreeObject<MedicalEquipmentTable> {
    StringProperty equipmentType;
    StringProperty equipmentID;
    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority; //filler for right now

    public MedicalEquipmentTable(String equipmentType, String equipmentID, String assigneeID,
                                 String location, String status, String priority){
        this.equipmentID = new SimpleStringProperty(equipmentID);
        this.equipmentType =  new SimpleStringProperty(equipmentType);
        this.assigneeID = new SimpleStringProperty(assigneeID);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
    }

    public String getEquipmentType() {
        return equipmentType.get();
    }

    public StringProperty equipmentTypeProperty() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType.set(equipmentType);
    }

    public String getEquipmentID() {
        return equipmentID.get();
    }

    public StringProperty equipmentIDProperty() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID.set(equipmentID);
    }

    public String getAssigneeID() {
        return assigneeID.get();
    }

    public StringProperty assigneeIDProperty() {
        return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
        this.assigneeID.set(assigneeID);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }



}
