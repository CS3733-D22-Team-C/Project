package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableRow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BaseMapSideViewController implements Initializable {

    // Containers
    @FXML private VBox floorVBox;

    // Controllers
    List<FloorNode> floorNodeControllerList;

    //TODO: not sure if needed. Potentially delete.
    private Floor selectedFloor;

    @Override // Load floors and buttons
    public void initialize(URL location, ResourceBundle resources) {
        floorNodeControllerList = new ArrayList<>();
        FloorDAO floorDAO = new FloorDAO();
        List<Floor> lof = floorDAO.getAll();
        for(Floor floor : lof){
            FloorNode floorNode = FloorNode.loadNewFloorNode();
            floorNode.setup(floor);
            floorVBox.getChildren().add(floorNode.getGroup());
            floorNodeControllerList.add(floorNode);
            floorNode.getGroup().getChildren().get(0).setOnMouseClicked(e -> onFloorClicked(e, floorNode));
        }
    }

    public class ready{
        public String numOfBeds;
        public String numOfRecliners;
        public String numOfXRays;
        public String numOfPumps;
    }

    private class inUse{
        public String numOfBeds;
        public String numOfRecliners;
        public String numOfXRays;
        public String numOfPumps;
    }

    private class dirty{
        public String numOfBeds;
        public String numOfRecliners;
        public String numOfXRays;
        public String numOfPumps;
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
    void onFloorClicked(MouseEvent event, FloorNode floorNode) {
        this.selectedFloor = floorNode.getFloor();

        goToButton.setDisable(false);
        deleteButton.setDisable(false);

        floorTitle.setText(selectedFloor.getLongName());
        //floorDescription.setText(selectedFloor.getDescription());
        floorDescription.setText("OOOhhh you want a description, you want it so bad");
    }

    @FXML
    void onGoToClicked(ActionEvent event) {
        // TODO: Place map function from nick (Thanks Nick!)
        App.instance.setView(App.MAP_PATH);
    }

    @FXML
    void onDeleteClicked(ActionEvent event){

    }
}

//    void loadEquipment(){
//        // Sort medical equipment into floor in each
//        MedicalEquipmentDAO MEL = new MedicalEquipmentDAO();
//        List<MedicalEquipment> medicalEquipmentPerFloor =
//                MEL.getAll().stream().filter(medicalEquipment -> medicalEquipment.getFloor()); //TODO: Filter by floor (Thanks Brian!)
//        MEL.getAll().stream().filter(medicalEquipment -> medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty));
//
//        List<MedicalEquipment> floorXDirty;
//        List<MedicalEquipment> floorXClean;
//        List<MedicalEquipment> floorXPod;
//        for(MedicalEquipment medicalEquipmentFloorX : medicalEquipmentPerFloor){
//            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) floorXDirty.add(medicalEquipmentFloorX);
//            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)) floorXClean.add(medicalEquipmentFloorX);
//            if(medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable)) floorXPod.add(medicalEquipmentFloorX);
//        }
//    }