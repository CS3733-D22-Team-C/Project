package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.models.location.EquipmentTableDisplay;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BaseMapSideViewController implements Initializable {

    // Containers
    @FXML private VBox floorVBox;

    // Equipment Holders
    private Equipment readyEquip;
    private Equipment dirtyEquip;
    private Equipment inUseEquip;

    // Controllers
    List<FloorNode> floorNodeControllerList;

    // Current Floor
    private Floor selectedFloor;

    // Table
    @FXML private JFXTreeTableView table;
    private EquipmentTableDisplay tableDisplay;

    // Floor Descriptors
    @FXML private Label floorTitle;
    @FXML private Label floorDescription;

    // Buttons
    @FXML private MFXButton deleteButton;
    @FXML private MFXButton goToButton;

    @Override // Load floors and buttons
    public void initialize(URL location, ResourceBundle resources) {
        this.readyEquip = new Equipment();
        this.dirtyEquip = new Equipment();
        this.inUseEquip = new Equipment();
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

        tableDisplay = new EquipmentTableDisplay(table);

    }

    public class Equipment extends RecursiveTreeObject<Equipment> {
        public String numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;

        Equipment(){
            this.numOfBeds = "";
            this.numOfRecliners = 0;
            this.numOfXRays = 0;
            this.numOfPumps = 0;
        }
    }


    @FXML
    void onFloorClicked(MouseEvent event, FloorNode floorNode) {
        this.selectedFloor = floorNode.getFloor();

        goToButton.setDisable(false);
        deleteButton.setDisable(false);

        floorTitle.setText(selectedFloor.getLongName());
        floorDescription.setText(selectedFloor.getDescription());

        loadEquipment();

        tableDisplay.emptyTable();

        tableDisplay.addObject(dirtyEquip);
        tableDisplay.addObject(inUseEquip);
        tableDisplay.addObject(readyEquip);

    }

    @FXML
    void onGoToClicked(ActionEvent event) {
        App.View<MapViewController> view = App.instance.loadView(App.MAP_PATH);
        view.getController().getFloorManager().changeCurrent(selectedFloor);
        App.instance.setView(view.getNode());
    }

    @FXML
    void onDeleteClicked(ActionEvent event){
        FloorDAO floorDAO = new FloorDAO();
        floorDAO.delete(selectedFloor);
        for(FloorNode node : floorNodeControllerList){
            if(node.getFloor() == selectedFloor){
                floorNodeControllerList.remove(node);
                App.instance.setView("view/location/map/base_side_map_view.fxml");
                return;
            }
        }

    }

    private void loadEquipment() {
        MedicalEquipmentDAO MEL = new MedicalEquipmentDAO();
        List<MedicalEquipment> medicalEquipmentPerFloor = MEL.getEquipmentByFloor(selectedFloor.getID());

        List<MedicalEquipment> floorXDirty = new ArrayList<>();
        List<MedicalEquipment> floorXClean = new ArrayList<>();
        List<MedicalEquipment> floorXPod = new ArrayList<>();
        for (MedicalEquipment medicalEquipmentFloorX : medicalEquipmentPerFloor) {
            medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty);
                floorXDirty.add(medicalEquipmentFloorX);
             if (medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Available))
                floorXClean.add(medicalEquipmentFloorX);
            if (medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable))
                floorXPod.add(medicalEquipmentFloorX);
        }

        // So i need to add the count of all equipments
        List<MedicalEquipment> dirtyRecliners   = new ArrayList<>();
        List<MedicalEquipment> dirtyBeds        = new ArrayList<>();
        List<MedicalEquipment> dirtyPumps       = new ArrayList<>();
        List<MedicalEquipment> dirtyXRays       = new ArrayList<>();

        List<MedicalEquipment> cleanXRays       = new ArrayList<>();
        List<MedicalEquipment> cleanPumps       = new ArrayList<>();
        List<MedicalEquipment> cleanRecliners   = new ArrayList<>();
        List<MedicalEquipment> cleanBeds        = new ArrayList<>();

        List<MedicalEquipment> inUseBeds        = new ArrayList<>();
        List<MedicalEquipment> inUseXRays       = new ArrayList<>();
        List<MedicalEquipment> inUsePumps       = new ArrayList<>();
        List<MedicalEquipment> inUseRecliners   = new ArrayList<>();

        for(MedicalEquipment medicalEquipment : floorXDirty){
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) dirtyRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) dirtyXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) dirtyBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) dirtyPumps.add(medicalEquipment);
        }
        for(MedicalEquipment medicalEquipment : floorXClean){
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) cleanRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) cleanXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) cleanBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) cleanPumps.add(medicalEquipment);
        }
        for(MedicalEquipment medicalEquipment : floorXPod){
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) inUseRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) inUseXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) inUseBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) inUsePumps.add(medicalEquipment);
        }
        dirtyEquip.numOfRecliners   = dirtyRecliners.size();
        dirtyEquip.numOfBeds        = "Dirty:   " + dirtyBeds.size(); //YE
        dirtyEquip.numOfPumps       = dirtyPumps.size();
        dirtyEquip.numOfXRays       = dirtyXRays.size();

        inUseEquip.numOfBeds        = "In Use: " + inUseBeds.size();
        inUseEquip.numOfXRays       = inUseXRays.size();
        inUseEquip.numOfPumps       = inUsePumps.size();
        inUseEquip.numOfRecliners   = inUseRecliners.size();

        readyEquip.numOfXRays       = cleanXRays.size();
        readyEquip.numOfPumps       = cleanPumps.size();
        readyEquip.numOfRecliners   = cleanRecliners.size();
        readyEquip.numOfBeds        = "Ready: " + cleanBeds.size();
    }
}