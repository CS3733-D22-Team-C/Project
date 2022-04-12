package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.EquipmentTableDisplay;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
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
    @FXML private Text floorDescription;

    // Buttons
    @FXML private MFXButton deleteButton;
    @FXML private MFXButton goToButton;

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

        tableDisplay = new EquipmentTableDisplay(table);

    }

    public class Equipment extends RecursiveTreeObject<Equipment> {
        public int numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;
    }


    @FXML
    void onFloorClicked(MouseEvent event, FloorNode floorNode) {
        this.selectedFloor = floorNode.getFloor();

        goToButton.setDisable(false);
        //deleteButton.setDisable(false);

        floorTitle.setText(selectedFloor.getLongName());
        //floorDescription.setText(selectedFloor.getDescription());
        floorDescription.setText("OOOhhh you want to give us an A in this class, you want to give it to us so bad.");

        loadEquipment();

        tableDisplay.emptyTable();

        //tableDisplay.addObject(dirtyEquip);
        //tableDisplay.addObject(inUseEquip);
        //tableDisplay.addObject(readyEquip);

    }

    @FXML
    void onGoToClicked(ActionEvent event) {
        // TODO: Place map function from nick (Thanks Nick!)
        App.instance.setView(App.MAP_PATH);
    }

    @FXML
    void onDeleteClicked(ActionEvent event){

    }

    private void loadEquipment() {
        MedicalEquipmentDAO MEL = new MedicalEquipmentDAO();
        List<MedicalEquipment> medicalEquipmentPerFloor = MEL.getEquipmentByFloor(selectedFloor.getFloorID());


        List<MedicalEquipment> floorXDirty = new ArrayList<>();
        List<MedicalEquipment> floorXClean = new ArrayList<>();
        List<MedicalEquipment> floorXPod = new ArrayList<>();
        for (MedicalEquipment medicalEquipmentFloorX : medicalEquipmentPerFloor) {
            if (medicalEquipmentFloorX.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty))
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
            System.out.println(medicalEquipment.getEquipmentType().toString());
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) dirtyRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) dirtyXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) dirtyBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) dirtyPumps.add(medicalEquipment);
        }
        for(MedicalEquipment medicalEquipment : floorXClean){
            System.out.println(medicalEquipment.getEquipmentType().toString());
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) cleanRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) cleanXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) cleanBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) cleanPumps.add(medicalEquipment);
        }
        for(MedicalEquipment medicalEquipment : floorXPod){
            System.out.println(medicalEquipment.getEquipmentType().toString());
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Recliner)) inUseRecliners.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Portable_X_Ray)) inUseXRays.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)) inUseBeds.add(medicalEquipment);
            if(medicalEquipment.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)) inUsePumps.add(medicalEquipment);
        }
        dirtyEquip.numOfRecliners   = dirtyRecliners.size();
        dirtyEquip.numOfBeds        = dirtyBeds.size();
        dirtyEquip.numOfPumps       = dirtyPumps.size();
        dirtyEquip.numOfXRays       = dirtyXRays.size();

        readyEquip.numOfXRays       = cleanXRays.size();
        readyEquip.numOfPumps       = cleanPumps.size();
        readyEquip.numOfRecliners   = cleanRecliners.size();
        readyEquip.numOfBeds        = cleanBeds.size();

        inUseEquip.numOfBeds        = inUseBeds.size();
        inUseEquip.numOfXRays       = inUseXRays.size();
        inUseEquip.numOfPumps       = inUsePumps.size();
        inUseEquip.numOfRecliners   = inUseRecliners.size();

        System.out.println(Integer.toString(dirtyEquip.numOfBeds));
        System.out.println(Integer.toString(readyEquip.numOfBeds));
        System.out.println(Integer.toString(inUseEquip.numOfBeds));
    }
}