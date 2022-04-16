package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;

import java.util.List;

public class MedicalEquipmentCounter {
    // General Components
    @FXML public Group root;
    @FXML private SVGPath icon;
    @FXML private Label counter;

    // Edit Components
    @FXML private Group editGroup;
    @FXML private SVGPath upArrow;
    @FXML private SVGPath downArrow;

    // Variables
    private MedicalEquipment.EquipmentType equipmentType;
    private List<MedicalEquipment> medicalEquipments;


    public void setPosition(int x, int y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }

    public void setType(MedicalEquipment.EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public void setMedicalEquipments(List<MedicalEquipment> medicalEquipments) {
        this.medicalEquipments = medicalEquipments;

        updateCounter();
    }

    public void setEditable(boolean editable) {
        editGroup.setVisible(editable);
    }

    public void updateCounter() {
        counter.setText(Integer.toString(medicalEquipments.size()));
    }

    //#region FXML Events
        @FXML
        public void onUpArrowClicked(ActionEvent event) {

        }

        @FXML
        public void onDownArrowClicked(ActionEvent event) {

        }
    //#endregion
}
