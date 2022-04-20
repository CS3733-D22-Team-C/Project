package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVWriter;
import edu.wpi.cs3733.D22.teamC.models.location.EquipmentTableDisplay;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.textfield.CustomTextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BaseMapSideViewController implements Initializable {

    // Containers
    @FXML private VBox floorVBox;
    @FXML private HBox imageBox;

    // Equipment Holders
    private Equipment readyEquip;
    private Equipment dirtyEquip;
    private Equipment inUseEquip;

    // Controllers
    List<FloorNode> floorNodeControllerList;

    // Current Floor
    private Floor selectedFloor;

    // Table
    @FXML private JFXTreeTableView<Equipment> table;
    private EquipmentTableDisplay tableDisplay;

    // Floor Descriptors
    @FXML private Label floorTitle;
    @FXML private InfoOverlay floorDescription;

    // Buttons
    @FXML private MFXButton deleteButton;
    @FXML private MFXButton editButton;
    @FXML private MFXButton addFloorButton;
    @FXML private MFXButton cancelButton;
    @FXML private MFXButton confirmButton;
    @FXML private MFXButton addImageButton;


    // ???
    @FXML private ImageView floorImage;
    private byte[] bFile;
    private String imagePath;

    // Custom Text Fields
    @FXML private CustomTextField longName;
    @FXML private CustomTextField shortName;
    @FXML private TextArea description;
    @FXML private CustomTextField image;

    private boolean addFloorClicked;

    @Override // Load floors and buttons
    public void initialize(URL location, ResourceBundle resources) {
        this.readyEquip = new Equipment();
        this.dirtyEquip = new Equipment();
        this.inUseEquip = new Equipment();

        this.addFloorClicked = false;
        imagePath = "";
        bFile = null;

        this.floorDescription = new InfoOverlay(floorImage, "Description");

        floorNodeControllerList = new ArrayList<>();
        FloorDAO floorDAO = new FloorDAO();
        List<Floor> lof = floorDAO.getAll();
        for(Floor floor : lof){
            FloorNode floorNode = FloorNode.loadNewFloorNode();
            floorNode.setup(floor);
            floorVBox.getChildren().add(floorNode.getGroup());
            floorNodeControllerList.add(floorNode);
            floorNode.getGroup().getChildren().get(0).setOnMouseClicked(e -> {
                try {
                    onFloorClicked(e, floorNode);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        tableDisplay = new EquipmentTableDisplay(table);

    }

    public void onConfirmClicked(ActionEvent actionEvent) {
        if(selectedFloor == null){
            if(!requiredFieldsPresent()) return;
            selectedFloor = new Floor();
            selectedFloor.setLongName(longName.getText());
            selectedFloor.setDescription(description.getText());
            selectedFloor.setShortName(shortName.getText());
            selectedFloor.setImage(bFile);
            selectedFloor.setImageSrc(imagePath);
            selectedFloor.setOrder(100);

            FloorDAO floorDAO = new FloorDAO();
            floorDAO.insert(selectedFloor);

            imagePath = "";
            bFile = null;

            cancelButton.setDisable(true);
            confirmButton.setDisable(true);
            addImageButton.setDisable(true);
            shortName.setDisable(true);
            longName.setDisable(true);
            description.setDisable(true);
            image.setDisable(true);

            App.instance.setView("view/location/map/base_side_map_view.fxml");
            return;
        }
        addFloorClicked = false;
        selectedFloor.setLongName(longName.getText());
        selectedFloor.setDescription(description.getText());
        selectedFloor.setShortName(shortName.getText());
        selectedFloor.setImage(bFile);
        selectedFloor.setImageSrc(imagePath);

        FloorDAO floorDAO = new FloorDAO();
        floorDAO.update(selectedFloor);

        imagePath = "";
        bFile = null;

        cancelButton.setDisable(true);
        confirmButton.setDisable(true);
        addImageButton.setDisable(true);
        shortName.setDisable(true);
        longName.setDisable(true);
        description.setDisable(true);
        image.setDisable(true);
    }

    private boolean requiredFieldsPresent(){
        if(shortName.getText().equals("") || longName.getText().equals("")
        || description.getText().equals("") || imagePath.equals("") || bFile == null){
            return false;
        }
        return true;
    }

    public void onAddFloorClicked(ActionEvent actionEvent) {
        selectedFloor = null;
        addFloorClicked = true;

        shortName.setText("");
        longName.setText("");
        description.setText("");
        image.setText("");

        cancelButton.setDisable(false);
        confirmButton.setDisable(false);
        addImageButton.setDisable(false);
        shortName.setDisable(false);
        longName.setDisable(false);
        description.setDisable(false);
        image.setDisable(false);
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
    void onAddImageClicked(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Image File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)", "*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.instance.getStage());

        if (file != null) {
            // Load image
            try {
                BufferedImage bImg = ImageIO.read(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bImg, "png", byteArrayOutputStream);
                bFile = byteArrayOutputStream.toByteArray();

                imagePath = file.getName();
                this.image.setText(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void onFloorClicked(MouseEvent event, FloorNode floorNode) throws IOException {
        if(addFloorClicked) return;
        this.selectedFloor = floorNode.getFloor();

        System.out.println(selectedFloor.getImageSrc());

        //goToButton.setDisable(false);
        deleteButton.setDisable(false);
        editButton.setDisable(false);


        floorTitle.setText(selectedFloor.getLongName());

        ByteArrayInputStream bis = new ByteArrayInputStream(selectedFloor.getImage());
        BufferedImage img = ImageIO.read(bis);
        Image image = SwingFXUtils.toFXImage(img, null);

        floorImage.fitWidthProperty().bind(imageBox.widthProperty());
        floorImage.fitHeightProperty().bind(imageBox.heightProperty());
        floorImage.setImage(image);

        InfoOverlay overlay = new InfoOverlay(floorImage, selectedFloor.getDescription());

        floorDescription.setContent(floorImage);
        floorDescription.setShowOnHover(true);
        floorDescription.setText(selectedFloor.getDescription());

        loadEquipment();

        tableDisplay.emptyTable();

        tableDisplay.addObject(dirtyEquip);
        tableDisplay.addObject(inUseEquip);
        tableDisplay.addObject(readyEquip);

        this.shortName.setText(floorNode.getFloor().getShortName());
        this.longName.setText(floorNode.getFloor().getLongName());
        this.description.setText(floorNode.getFloor().getDescription());
        this.image.setText(floorNode.getFloor().getImageSrc());

    }

    @FXML
    void onEditClicked(ActionEvent event){
        shortName.setDisable(false);
        longName.setDisable(false);
        addImageButton.setDisable(false);
        description.setDisable(false);
        image.setDisable(false);
        cancelButton.setDisable(false);
        confirmButton.setDisable(false);

        bFile = selectedFloor.getImage();
        imagePath = selectedFloor.getImageSrc();
    }

    @FXML
    void onGoToClicked() {
        App.View<MapViewController> view = App.instance.loadView(App.MAP_PATH);
        view.getController().getFloorManager().changeCurrent(selectedFloor);
        App.instance.setView(view.getNode());
    }

    @FXML
    void onCancelClicked(ActionEvent event){
        addFloorClicked = false;
        cancelButton.setDisable(true);
        confirmButton.setDisable(true);
        addImageButton.setDisable(true);
        shortName.setDisable(true);
        longName.setDisable(true);
        description.setDisable(true);
        image.setDisable(true);

    }

    @FXML // TODO: Add confirm delete later. I'm too fucking lazy bro holy shit
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