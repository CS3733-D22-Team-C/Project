package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.location.EquipmentTableDisplay;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    @FXML private MFXButton info;


    // ???
    @FXML private ImageView floorImage;
    private byte[] bFile;
    private String imagePath;

    // Custom Text Fields
    @FXML private CustomTextField longName;
    @FXML private CustomTextField shortName;
    @FXML private TextArea description;
    @FXML private CustomTextField image;
    @FXML private CustomTextField order;

    private boolean addFloorClicked;

    private static final String TAB_DRAG_KEY = "node";
    private ObjectProperty<Node> draggingTab;

    @Override // Load floors and buttons
    public void initialize(URL location, ResourceBundle resources) {
        this.readyEquip = new Equipment();
        this.dirtyEquip = new Equipment();
        this.inUseEquip = new Equipment();

        this.addFloorClicked = false;
        imagePath = "";
        bFile = null;

        this.floorDescription = new InfoOverlay(floorImage, "Description");

        SVGParser svgParser = new SVGParser();
        String infoIcon = svgParser.getPath("static/icons/info_icon.svg");
        SVGGlyph infoContent = new SVGGlyph(infoIcon);
        infoContent.setSize(20);
        info.setGraphic(infoContent);

        floorNodeControllerList = new ArrayList<>();
        FloorDAO floorDAO = new FloorDAO();
        List<Floor> lof = floorDAO.getAll();

        tableDisplay = new EquipmentTableDisplay(table);
        draggingTab = new SimpleObjectProperty<Node>();

        Collections.sort(lof, new Comparator<Floor>() {
            @Override
            public int compare(Floor o1, Floor o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });
        Collections.reverse(lof);

        for(Floor floor : lof){
            FloorNode floorNode = FloorNode.loadNewFloorNode();
            floorNode.setup(floor);
            floorVBox.getChildren().add(floorNode.getGroup());
            floorNodeControllerList.add(floorNode);
            floorNode.getGroup().getChildren().get(1).setOnMouseClicked(e -> {
                try {
                    onFloorClicked(e, floorNode);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            /*floorNode.getGroup().getChildren().get(0).setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    final Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasString()
                            && TAB_DRAG_KEY.equals(dragboard.getString())
                            && draggingTab.get() != null) {
                        event.acceptTransferModes(TransferMode.MOVE);
                        event.consume();
                    }
                }
            });
            floorNode.getGroup().getChildren().get(0).setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(final DragEvent event) {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        HBox parent = floorNode.getGroup();
                        Object source = event.getGestureSource();
                        int sourceIndex = floorVBox.getChildren().indexOf(source);
                        int targetIndex = floorVBox.getChildren().indexOf(floorNode.getGroup());
                        List<Node> nodes = new ArrayList<Node>(floorVBox.getChildren());
                        if (sourceIndex < targetIndex) {
                            Collections.rotate( // todo look into using buttons that do this instead
                                    nodes.subList(sourceIndex, targetIndex + 1), -1);
                        } else {
                            Collections.rotate(
                                    nodes.subList(targetIndex, sourceIndex + 1), 1);
                        }
                        floorVBox.getChildren().clear();
                        floorVBox.getChildren().addAll(nodes);
                        success = true;
                        System.out.println(floorNode.getFloor()); // gives the floor that gets placed over (i.e, if 4 is dragged over 5, 5 is given)
                        floorNode.getFloor().setOrder(Integer.parseInt(floorNode.getFloor().toString()));
                        App.instance.setView("view/location/map/base_side_map_view.fxml");
                        updateFloor(); // todo: Write code to save order to csv
                        // todo maybe try working with only the node list using the target and selected index and updating manually?
                        // might need two functions (one for up and one for down for this to work)
                    }
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
            floorNode.getGroup().getChildren().get(0).setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dragboard dragboard = floorNode.getGroup().startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(TAB_DRAG_KEY);
                    dragboard.setContent(clipboardContent);
                    draggingTab.set(floorNode.getGroup());
                    event.consume();
                }
            });*/
        }

        for(FloorNode floorNodes : floorNodeControllerList){
            floorNodes.getGroup().getChildren().get(1).setStyle("-fx-background-color: #FFFFFF");
        }

        ScrollBar sc = new ScrollBar();
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        //set other properties
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                floorVBox.setLayoutY(-new_val.doubleValue());
            }
        });

    }

    public void onInfoClicked(ActionEvent event){
        Text text = new Text("Type the order you wish the floor to appear on. " +
                             "For Example, if there are 5 floors, enter \"3\" and the new floor " +
                             "will appear in the third from the bottom position");
        text.setWrappingWidth(200);
        PopOver popOver = new PopOver(text);
        popOver.arrowLocationProperty().setValue(PopOver.ArrowLocation.LEFT_CENTER);
        popOver.setTitle("Instructions");
        popOver.show(info);
    }

    public void updateFloor(){
        // TODO: Place holder for updating floors in csv
    }

    public void onConfirmClicked(ActionEvent actionEvent) {
        if(selectedFloor == null){
            if(!requiredFieldsPresent()) return;
            // adding new floor
            selectedFloor = new Floor();
            selectedFloor.setLongName(longName.getText());
            selectedFloor.setDescription(description.getText());
            selectedFloor.setShortName(shortName.getText());
            selectedFloor.setImage(bFile);
            selectedFloor.setImageSrc(imagePath);

            selectedFloor.setOrder(Integer.parseInt(order.getText())+1);


            FloorDAO floorDAO = new FloorDAO();

            boolean found = false;
            System.out.println("Attempting to update Floors in DAO");
            for(Floor floor : floorDAO.getAll()){
                if(found) break;
                if(floor.getOrder() != selectedFloor.getOrder()){
                    floor.setOrder(floor.getOrder()+1);
                    System.out.println(floor.getLongName() + " set new order to " + floor.getOrder());
                    floorDAO.update(floor);
                    System.out.println("Floor updated in DAO");
                }
                else {
                    floor.setOrder(floor.getOrder()+1);
                    System.out.println(floor.getLongName() + " set new order to " + floor.getOrder());
                    floorDAO.update(floor);
                    System.out.println("Floor updated in DAO");
                    floorDAO.insert(selectedFloor);
                    System.out.println(selectedFloor.getLongName() + " added to DAO");
                    found = true;
                }
            }
            if(!requiredFieldsPresent()) return;

            imagePath = "";
            bFile = null;

            cancelButton.setDisable(true);
            confirmButton.setDisable(true);
            addImageButton.setDisable(true);
            shortName.setDisable(true);
            longName.setDisable(true);
            description.setDisable(true);
            image.setDisable(true);
            order.setDisable(true);

            App.instance.setView("view/location/map/base_side_map_view.fxml");
            return;
        }
        // editing
        addFloorClicked = false;
        selectedFloor.setLongName(longName.getText());
        selectedFloor.setDescription(description.getText());
        selectedFloor.setShortName(shortName.getText());
        selectedFloor.setImage(bFile);
        selectedFloor.setImageSrc(imagePath);
        int oldOrder = selectedFloor.getOrder();
        System.out.println("Old order: " + oldOrder);
        selectedFloor.setOrder(Integer.parseInt(order.getText()));
        int newOrder = selectedFloor.getOrder();
        System.out.println("New order: " + newOrder);

        boolean incorrectOrder = false;
        if(newOrder < 1) {selectedFloor.setOrder(1); newOrder = 1; incorrectOrder = true;} // less than check

        FloorDAO floorDAO = new FloorDAO();
        floorDAO.update(selectedFloor);

        int greatestOrder = 0; // greater than check
        for(Floor floor : floorDAO.getAll()){
            if(floor.getOrder() > greatestOrder && floor.getOrder() != newOrder) greatestOrder = floor.getOrder();
        }

        System.out.println("Greatest Order: " + greatestOrder);
        if(!(oldOrder < greatestOrder) && newOrder > oldOrder) newOrder = greatestOrder+1; // makes it 7
        if(newOrder > greatestOrder+1 && greatestOrder != newOrder) {newOrder = greatestOrder; selectedFloor.setOrder(greatestOrder); incorrectOrder = true;}

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("New order: " + newOrder);
        System.out.println("Old order: " + oldOrder);
        System.out.println("Greatest order: " + greatestOrder);

        if(newOrder > oldOrder) {
            System.out.println("New order is greater than old order: " + newOrder + ", " + oldOrder);
            if(oldOrder > greatestOrder) selectedFloor.setOrder(oldOrder);

            // Works for updating floors by moving up.
            for (Floor floor : floorDAO.getAll()) {
                System.out.println("Currently on: " + floor.getLongName());
                if (floor.getOrder() <= oldOrder - 1) System.out.println("Floor is less than old order, did nothing");
                else if (floor.getOrder() <= selectedFloor.getOrder()) {
                    System.out.println("Floor order updated. Was " + floor.getOrder() + ". Is now " + (floor.getOrder() - 1));
                    floor.setOrder(floor.getOrder() - 1);
                    floorDAO.update(floor);
                }
            }
        }
        else if(newOrder < oldOrder){
            for (Floor floor : floorDAO.getAll()) {
                System.out.println("Currently on: " + floor.getLongName());
                if (floor.getOrder() >= oldOrder) System.out.println("Floor is more than old order, did nothing");
                else if(floor.getOrder() < newOrder) System.out.println("Floor is less than new order, did nothing");
                else if (floor.getOrder() < oldOrder) {
                    System.out.println("Floor order updated. Was " + floor.getOrder() + ". Is now " + (floor.getOrder() + 1));
                    floor.setOrder(floor.getOrder() + 1);
                    floorDAO.update(floor);
                }
            }
        }
        selectedFloor.setOrder(newOrder);
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
        order.setDisable(true);

        App.instance.setView("view/location/map/base_side_map_view.fxml");
    }

    private boolean requiredFieldsPresent(){
        if(shortName.getText().equals("") || longName.getText().equals("")
        || description.getText().equals("") || imagePath.equals("") || bFile == null ||
        order.getText().equals("")){
            return false;
        }
        try{
            Integer.parseInt(order.getText());
        }
        catch (Exception e){
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
        order.setText("");

        cancelButton.setDisable(false);
        confirmButton.setDisable(false);
        addImageButton.setDisable(false);
        shortName.setDisable(false);
        longName.setDisable(false);
        description.setDisable(false);
        image.setDisable(false);
        order.setDisable(false);
    }

    public class Equipment extends RecursiveTreeObject<Equipment> implements IDEntity {
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

        @Override
        public String getID() {
            return null;
        }

        @Override
        public void setID(String id) {

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

        deleteButton.setDisable(false);
        editButton.setDisable(false);

        for(FloorNode floorNodes : floorNodeControllerList){
            floorNodes.getGroup().getChildren().get(1).setStyle("-fx-background-color: #FFFFFF");
        }

        floorNode.getGroup().getChildren().get(1).setStyle("-fx-background-color: #D7E5EF");

        floorTitle.setText(selectedFloor.getLongName());

        ByteArrayInputStream bis = new ByteArrayInputStream(selectedFloor.getImage());
        BufferedImage img = ImageIO.read(bis);
        Image image = SwingFXUtils.toFXImage(img, null);

        floorImage.fitWidthProperty().bind(imageBox.widthProperty());
        floorImage.fitHeightProperty().bind(imageBox.heightProperty());
        floorImage.setImage(image);

        //does not work fucking bitch
        InfoOverlay overlay = new InfoOverlay(floorImage, selectedFloor.getDescription());
        overlay.setContent(floorImage);

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
        this.order.setText(Integer.toString(floorNode.getFloor().getOrder()));
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
        order.setDisable(false);

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
        order.setDisable(true);

    }

    @FXML // TODO: Add confirm delete later. I'm too fucking lazy bro holy shit
    void onDeleteClicked(ActionEvent event){
        FloorDAO floorDAO = new FloorDAO();
        floorDAO.delete(selectedFloor);
        for(FloorNode node : floorNodeControllerList){
            if(node.getFloor() == selectedFloor){
                int deletedOrder = selectedFloor.getOrder();
                floorNodeControllerList.remove(node);
                for(Floor floor : floorDAO.getAll()){
                    if(floor.getOrder() > deletedOrder){
                        floor.setOrder(floor.getOrder()-1);
                        floorDAO.update(floor);
                    }
                }
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