package edu.wpi.cs3733.D22.teamC.controller.location.map.map_view;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class MedicalEquipmentCounterNode {
    // General Components
    @FXML public Group group;
    @FXML private SVGPath icon;
    @FXML private Label counter;

    // Edit Components
    @FXML private Group editGroup;
    @FXML private SVGPath upArrow;
    @FXML private SVGPath downArrow;

    // References
    LocationNode locationNode;

    // Variables
    MedicalEquipment.EquipmentType equipmentType;
    List<MedicalEquipment> medicalEquipments;
    boolean editMode;

    public void setLocationNode(LocationNode locationNode) {
        this.locationNode = locationNode;
        this.medicalEquipments = ((BaseMapViewController) locationNode.mapController.parentController).medicalEquipmentManager.getPerLocationPerType(locationNode.location, equipmentType);
        resetCounter();
        resetDisabled();
    }

    //#region State Update
        public void render(Group pane, Pair<Integer, Integer> offsets) {
            pane.getChildren().add(group);
            group.setLayoutX(offsets.getKey());
            group.setLayoutY(offsets.getValue());
        }

        public void render(Pane pane, Pair<Integer, Integer> offsets) {
            pane.getChildren().add(group);
            group.setLayoutX(offsets.getKey());
            group.setLayoutY(offsets.getValue());
        }

        private void resetDisabled() {
            setIncreaseDisabled(((BaseMapViewController) locationNode.mapController.parentController).medicalEquipmentManager.getDisabledIncrease(equipmentType));
            setDecreaseDisabled(medicalEquipments.size() == 0);
        }

        public void resetCounter() {
            counter.setText(Integer.toString(medicalEquipments.size()));
        }
    //#endregion

    //#region Mouse Events
        @FXML
        void onDownArrowClicked(MouseEvent event) {
            MedicalEquipment medicalEquipment = medicalEquipments.get(0);
            medicalEquipments.remove(0);
            ((BaseMapViewController) locationNode.mapController.parentController).medicalEquipmentManager.releaseMedicalEquipment(medicalEquipment);
            ((BaseMapViewController) locationNode.mapController.parentController).infoPaneController.resetMedicalEquipment(locationNode.location);
            resetCounter();
            resetDisabled();

            setDecreaseDisabled(medicalEquipments.size() == 0);

            ((BaseMapViewController) locationNode.mapController.parentController).setSaveStatus();

            event.consume();
        }

        @FXML
        void onUpArrowClicked(MouseEvent event) {
            MedicalEquipment medicalEquipment = ((BaseMapViewController) locationNode.mapController.parentController).medicalEquipmentManager.reclaimMedicalEquipment(equipmentType, locationNode.location);
            ((BaseMapViewController) locationNode.mapController.parentController).infoPaneController.resetMedicalEquipment(locationNode.location);
            medicalEquipments.add(medicalEquipment);
            resetCounter();
            resetDisabled();

            ((BaseMapViewController) locationNode.mapController.parentController).setSaveStatus();

            event.consume();
        }
    //#endregion

    //#region External Interaction
        public void releaseAllMedicalEquipment() {
            for (MedicalEquipment medicalEquipment : medicalEquipments) {
                ((BaseMapViewController) locationNode.mapController.parentController).medicalEquipmentManager.releaseMedicalEquipment(medicalEquipment);
            }
        }

        public Group getGroup() {
            return group;
        }

        public void setVisible(boolean visible) {
            group.setVisible(visible);
            if (editMode) resetDisabled();
        }

        public void setEditable(boolean editable) {
            editMode = editable;
            editGroup.setVisible(editable);
            if (editMode) resetDisabled();
        }

        public void setMedicalEquipments(List<MedicalEquipment> medicalEquipments) {
            this.medicalEquipments = medicalEquipments;
            this.resetCounter();
        }

        public List<MedicalEquipment> getMedicalEquipments() {
            return medicalEquipments;
        }

        public void setIncreaseDisabled(boolean increaseDisabled) {
            upArrow.setDisable(increaseDisabled);
        }

        public void setDecreaseDisabled(boolean decreaseDisabled) {
            downArrow.setDisable(decreaseDisabled);
        }
    //#endregion

    //#region Load Component
    public static MedicalEquipmentCounterNode loadNewMedicalEquipmentCounterNode(MedicalEquipment.EquipmentType equipmentType) {
        String fileName = "";

        switch (equipmentType) {
            case Bed:
                fileName = "bed.fxml";
                break;
            case Portable_X_Ray:
                fileName = "xray.fxml";
                break;
            case Recliner:
                fileName = "recliner.fxml";
                break;
            case Infusion_Pump:
                fileName = "pump.fxml";
                break;
        }
        
        App.View<MedicalEquipmentCounterNode> view = App.instance.loadView("view/location/map/map_view/medical_equipment/" + fileName);
        MedicalEquipmentCounterNode medicalEquipmentCounterNode = view.getController();
        medicalEquipmentCounterNode.equipmentType = equipmentType;
        return medicalEquipmentCounterNode;
    }
    //#endregion
}
