package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableRow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MapSideViewController {

    // Floor Buttons
    private String selectedFloor;

    @Override // TODO: Get rid of initialize and move functionality into helper function or at beginning of each floor button
    public void initialize(URL location, ResourceBundle resources) {
        // Sort medical equipment into floor in each
        MedicalEquipmentDAO MEL = new MedicalEquipmentDAO();
        List<MedicalEquipment> medicalEquipmentPerFloor =
                MEL.getAll().stream().filter(medicalEquipment -> medicalEquipment.getFloor()); //TODO: Filter by floor (Thanks Brian!)
        MEL.getAll().stream().filter(medicalEquipment -> medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty));

        List<MedicalEquipment> floorXDirty;
        List<MedicalEquipment> floorXClean;
        List<MedicalEquipment> floorXPod;
        for(MedicalEquipment medicalEquipmentFloorX : medicalEquipmentPerFloor){
            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) floorXDirty.add(medicalEquipmentFloorX);
            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)) floorXClean.add(medicalEquipmentFloorX);
            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable)) floorXPod.add(medicalEquipmentFloorX);
        }
    }

    public class ready{
        public int numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;
    }

    private class inUse{
        public int numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;
    }

    private class dirty{
        public int numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;
    }

    // Table
    @FXML private MFXLegacyTableRow<?> table;

    // Floor Descriptors
    @FXML private Label floorTitle;
    @FXML private Text floorDescription;

    // Buttons
    @FXML private MFXButton deleteButton;
    @FXML private MFXButton goToButton;

    @FXML
    void onFloor1Click(ActionEvent event) {
        this.selectedFloor = "Floor 1";
        buttonActions();
    }

    @FXML
    void onFloor2Click(ActionEvent event) {
        this.selectedFloor = "Floor 2";
        buttonActions();
    }

    @FXML
    void onFloor3Click(ActionEvent event) {
        this.selectedFloor = "Floor 3";
        buttonActions();
    }

    @FXML
    void onFloor4Click(ActionEvent event) {
        this.selectedFloor = "Floor 4";
        buttonActions();
    }

    @FXML
    void onFloor5Click(ActionEvent event) {
        this.selectedFloor = "Floor 5";
        buttonActions();
    }

    @FXML
    void onFloorLL1Click(ActionEvent event) {
        this.selectedFloor = "Floor LL1";
        buttonActions();
    }

    @FXML
    void onFloorLL2Click(ActionEvent event) {
        this.selectedFloor = "Floor LL2";
        buttonActions();
    }

    @FXML
    void onGoToButtonClicked(ActionEvent event) {

    }

    void buttonActions(){
        deleteButton.setDisable(false);
        goToButton.setDisable(false);
        switch (selectedFloor){
            case "Floor 1":
                floorTitle.setText("Floor 1");
                floorDescription.setText("This is the first floor. A description should be written here.");
                break;
            case "Floor 2":
                floorTitle.setText("Floor 2");
                floorDescription.setText("This is the second floor. A description should be written here.");
                break;
            case "Floor 3":
                floorTitle.setText("Floor 3");
                floorDescription.setText("This is the third floor. A description should be written here.");
                break;
            case "Floor 4":
                floorTitle.setText("Floor 4");
                floorDescription.setText("This is the fourth floor. A description should be written here.");
                break;
            case "Floor 5":
                floorTitle.setText("Floor 5");
                floorDescription.setText("This is the fifth floor. A description should be written here.");
                break;
            case "Floor LL1":
                floorTitle.setText("Floor LL1");
                floorDescription.setText("This is the lower-level floor one. A description should be written here.");
                break;
            case "Floor LL2":
                floorTitle.setText("Floor LL2");
                floorDescription.setText("This is the lower-level floor two. A description should be written here.");
                break;
        }
    }

}

