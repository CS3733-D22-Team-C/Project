package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.utils.DoughnutChart;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BaseMapSideViewController implements Initializable {

    public class Equipment {
        public int numOfBeds;
        public int numOfRecliners;
        public int numOfXRays;
        public int numOfPumps;

        Equipment(){
            this.numOfBeds = 0;
            this.numOfRecliners = 0;
            this.numOfXRays = 0;
            this.numOfPumps = 0;
        }
    }

    //#region EditFloorPanel
    @FXML private VBox floorEditPanel;
    // Buttons
    @FXML private MFXButton fileSelectButton;

    // Text Areas
    @FXML private MFXTextField longName;
    @FXML private MFXTextField shortName;
    @FXML private JFXTextArea description;
    @FXML private MFXTextField image;
    //#endregion

    // Buttons
    @FXML private MFXButton editButton;
    @FXML private MFXButton deleteButton;

    @FXML private ImageView floorImage;
    @FXML private Label floorTitle;
    @FXML private HBox imageBox;
    @FXML private VBox floorVBox;
    @FXML private Text descriptionText;


    // Pie Chart Panes
    @FXML private StackPane bedPane;
    @FXML private StackPane reclinerPane;
    @FXML private StackPane pumpPane;
    @FXML private StackPane xRayPane;


    @FXML HBox container;

    // Local Variables
    private Floor selectedFloor;
    private int order;
    private byte[] bFile;
    private String imagePath;
    private static final String TAB_DRAG_KEY = "node";
    private ObjectProperty<Node> draggingTab;
    private List<FloorNode> floorNodeControllerList;
    private boolean isEditMode;
    private int editOriginalOrder;
        // Equipments for Pie Charts
        private Equipment readyEquip;
        private Equipment dirtyEquip;
        private Equipment inUseEquip;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dirtyEquip = new Equipment();
        inUseEquip = new Equipment();
        readyEquip = new Equipment();
        selectedFloor = null;
        imagePath = "";
        bFile = null;
        order = 1;
        isEditMode = false;
        loadEquipment();
        SVGParser svgParser = new SVGParser();
        String folderContent = svgParser.getPath("static/icons/folder_icon.svg");
        SVGGlyph folderIcon = new SVGGlyph(folderContent);
        folderIcon.setSize(20);
        fileSelectButton.setGraphic(folderIcon);
        floorNodeControllerList = new ArrayList<>();
        loadFloors();
        try {
            onFloorClicked(null, floorNodeControllerList.get(floorNodeControllerList.size()-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onGoToClicked() {
        App.View<MapViewController> view = App.instance.loadView(App.MAP_PATH);
        view.getController().getFloorManager().changeCurrent(selectedFloor);
        App.instance.setView(view.getNode());
    }

    //#region base add/edit/delete floor
    @FXML
    void onAddFloorClicked(ActionEvent event) {
        floorTitle.setText("New Floor");
        descriptionText.setText("New Floor");
        floorImage.setImage(null);
        floorVBox.setDisable(true);
        this.isEditMode = false;
        floorEditPanel.setVisible(true);
        container.setVisible(false);

        FloorDAO floorDAO = new FloorDAO();
        selectedFloor = new Floor(1, "New Floor", "");
        selectedFloor.setDescription("");
        order = 1; // sets floor to bottom by default
        selectedFloor.setOrder(order);
        for(Floor floor : floorDAO.getAll()){ // increases all by 1
            floor.setOrder(floor.getOrder()+1);
            floorDAO.update(floor);
        }
        floorDAO.insert(selectedFloor);
        shortName.setText("");
        longName.setText("");
        description.setText("");
        image.setText("");
        bFile = null;
        imagePath = "";
        loadFloors();
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        FloorDAO floorDAO = new FloorDAO();
        floorDAO.delete(selectedFloor);

        for(FloorNode node : floorNodeControllerList){
            if(node.getFloor() == selectedFloor){
                int deletedOrder = selectedFloor.getOrder();
                for(Floor floor : floorDAO.getAll()){
                    if(floor.getOrder() > deletedOrder){
                        floor.setOrder(floor.getOrder()-1);
                        floorDAO.update(floor);
                    }
                }
            }
        }
        floorTitle.setText("Select A Floor");
        floorImage.setImage(null);
        descriptionText.setText("Select A Floor");

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        loadFloors();
    }

    @FXML
    void onEditClicked(ActionEvent event) {
        isEditMode = true;
        floorEditPanel.setVisible(true);
        container.setVisible(false);

        shortName.setText(selectedFloor.getShortName());
        longName.setText(selectedFloor.getLongName());
        descriptionText.setText(selectedFloor.getDescription());
        description.setText(selectedFloor.getDescription());
        image.setText(selectedFloor.getImageSrc());
        editOriginalOrder = selectedFloor.getOrder();
        bFile = selectedFloor.getImage();
        imagePath = selectedFloor.getImageSrc();
        floorVBox.setDisable(true);
    }
    //#endregion

    //#region add/edit floor panel
    @FXML
    void onCancelClicked(ActionEvent event) {
        if(isEditMode){
            FloorDAO floorDAO = new FloorDAO();
            List<Floor> lof = floorDAO.getAll();
            int oldIndex = lof.size() - editOriginalOrder; // need to put selected floor here
            lof.remove(selectedFloor);
            lof.add(oldIndex, selectedFloor);
            int j = 0;
            for(Floor floor : lof){
                floor.setOrder(lof.size()-j);
                floorDAO.update(floor);
                j++;
            }
            loadFloors();
        }
        else{
            // must reset all orders and delete the current selectedFloor
            FloorDAO floorDAO = new FloorDAO();
            floorDAO.delete(selectedFloor);
            List<Floor> lof = floorDAO.getAll();
            Collections.sort(lof, new Comparator<Floor>() {
                @Override
                public int compare(Floor o1, Floor o2) {
                    return Integer.compare(o1.getOrder(), o2.getOrder());
                }
            });
            Collections.reverse(lof);
            int i = 0;
            for(Floor floor : lof){
                floor.setOrder(lof.size()-i);
                floorDAO.update(floor);
                i++;
            }
            deleteButton.setDisable(true);
            editButton.setDisable(true);
        }
        if(!isEditMode){
            floorTitle.setText("Select A Floor");
            descriptionText.setText("Select A Floor");
            floorImage.setImage(null);

            shortName.setText("");
            longName.setText("");
            description.setText("");
            image.setText("");
        }
        floorEditPanel.setVisible(false);
        container.setVisible(true);
        floorVBox.setDisable(false);
        loadFloors();
        if(isEditMode) {
            for(FloorNode floorNode : floorNodeControllerList){
                if(Objects.equals(floorNode.getFloor().getID(), selectedFloor.getID())){
                    floorNode.getGroup().getChildren().get(0).setStyle("-fx-background-color: #B3EDF0");
                }
            }
        }
    }

    @FXML
    void onConfirmClicked(ActionEvent event) throws IOException {
        if(!requiredFieldsPresent()) return; // todo insert validation here or in function

        selectedFloor.setLongName(longName.getText());
        selectedFloor.setDescription(description.getText());
        selectedFloor.setShortName(shortName.getText());
        selectedFloor.setImage(bFile);
        selectedFloor.setImageSrc(imagePath);
        FloorDAO floorDAO = new FloorDAO();
        boolean floorFound = floorDAO.update(selectedFloor);
        if(!floorFound){
            floorDAO.insert(selectedFloor);
        }

        shortName.setText("");
        longName.setText("");
        description.setText("");
        image.setText("");

        floorTitle.setText(selectedFloor.getLongName());
        descriptionText.setText(selectedFloor.getDescription());

        ByteArrayInputStream bis = new ByteArrayInputStream(selectedFloor.getImage());
        BufferedImage img = ImageIO.read(bis);
        Image image = SwingFXUtils.toFXImage(img, null);

        floorImage.setImage(image);

        floorEditPanel.setVisible(false);
        container.setVisible(true);
        floorVBox.setDisable(false);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        loadFloors();
        for(FloorNode floorNode : floorNodeControllerList){
            if(Objects.equals(floorNode.getFloor().getID(), selectedFloor.getID())){
                floorNode.getGroup().getChildren().get(0).setStyle("-fx-background-color: #B3EDF0");
            }
        }
    }

    private boolean requiredFieldsPresent(){
        if(shortName.getText().equals("") || longName.getText().equals("")
                || imagePath.equals("") || bFile == null) return false;
        return true;
    }

    private void editOrder(int incrementOrDecrement, int newOrder){
        FloorDAO floorDAO = new FloorDAO();
        List<Floor> lof = floorDAO.getAll();
        Collections.sort(lof, new Comparator<Floor>() {
            @Override
            public int compare(Floor o1, Floor o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });
        Collections.reverse(lof);
        int i = 0;
        for(Floor floor : lof){
            floor.setOrder(lof.size()-i);
            floorDAO.update(floor);
            i++;
        }
        int selectedFloorIndex = lof.indexOf(selectedFloor);
        Floor temp = null;
        try{
            temp = lof.get(selectedFloorIndex - incrementOrDecrement); // if increment, grab item above, if decrement, grab below
        } // if item doesn't exist,
        catch (Exception e){
            temp = selectedFloor;
        }
        int replacedIndex = lof.indexOf(temp);
        lof.set(replacedIndex, selectedFloor); // set floor in list floor above or below and reset order
        lof.set(selectedFloorIndex, temp);
        int j = 0;
        for(Floor floor : lof){
            floor.setOrder(lof.size()-j);
            floorDAO.update(floor);
            j++;
        }
        loadFloors();
    }

    @FXML
    void onDecrementClicked(ActionEvent event) {
        order--;
        if(order < 1) order = 1;
        editOrder(-1, order);
    }

    @FXML
    void onIncrementClicked(ActionEvent event) {
        order = order+1;
        if(order > floorNodeControllerList.size()) order = floorNodeControllerList.size();
        editOrder(+1, order);
    }

    @FXML
    void onFileSelectClicked(ActionEvent event){
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
    //#endregion

    private void loadEquipment(){
        if(selectedFloor == null){
            ObservableList<PieChart.Data> bedPieChartData = createData(0,0,0);
            ObservableList<PieChart.Data> reclinerPieChartData = createData(0,0,0);
            ObservableList<PieChart.Data> pumpPieChartData = createData(0,0,0);
            ObservableList<PieChart.Data> xRayPieChartData = createData(0,0,0);
            DoughnutChart bedChart = new DoughnutChart(bedPieChartData);
            bedChart.setLabelsVisible(false);
            bedChart.setLegendVisible(false);
            DoughnutChart reclinerChart = new DoughnutChart(reclinerPieChartData);
            reclinerChart.setLabelsVisible(false);
            reclinerChart.setLegendVisible(false);
            DoughnutChart pumpChart = new DoughnutChart(pumpPieChartData);
            pumpChart.setLabelsVisible(false);
            pumpChart.setLegendVisible(false);
            DoughnutChart xRayChart = new DoughnutChart(xRayPieChartData);
            xRayChart.setLabelsVisible(false);
            xRayChart.setLegendVisible(false);

            initializeToolTip(bedPieChartData);
            initializeToolTip(reclinerPieChartData);
            initializeToolTip(pumpPieChartData);
            initializeToolTip(xRayPieChartData);

            SVGParser svgParser = new SVGParser();
            String bedContent = svgParser.getPath("static/icons/bed-solid.svg");
            SVGGlyph bedIcon = new SVGGlyph(bedContent);
            bedIcon.setSize(50);
            setUpToolTipIcon(bedIcon, "Beds");

            String reclinerContent = svgParser.getPath("static/icons/couch-solid.svg");
            SVGGlyph reclinerIcon = new SVGGlyph(reclinerContent);
            reclinerIcon.setSize(50);
            setUpToolTipIcon(reclinerIcon, "Recliners");

            String pumpContent = svgParser.getPath("static/icons/pump-medical-solid.svg");
            SVGGlyph pumpIcon = new SVGGlyph(pumpContent);
            pumpIcon.setSize(50);
            setUpToolTipIcon(pumpIcon, "Infusion Pumps");

            String xRayContent = svgParser.getPath("static/icons/x-ray-solid.svg");
            SVGGlyph xRayIcon = new SVGGlyph(xRayContent);
            xRayIcon.setSize(50);
            setUpToolTipIcon(xRayIcon, "X-Rays");

            Circle bedPickup = new Circle();
            bedPickup.setFill(Color.AQUA);
            bedPickup.setStroke(Color.DARKBLUE);
            bedPickup.setRadius(25.0);

            Circle reclinerPickup = new Circle();
            reclinerPickup.setFill(Color.AQUA);
            reclinerPickup.setStroke(Color.DARKBLUE);
            reclinerPickup.setRadius(25.0);

            Circle pumpPickup = new Circle();
            pumpPickup.setFill(Color.AQUA);
            pumpPickup.setStroke(Color.DARKBLUE);
            pumpPickup.setRadius(20.0);

            Circle xRayPickup = new Circle();
            xRayPickup.setFill(Color.AQUA);
            xRayPickup.setStroke(Color.DARKBLUE);
            xRayPickup.setRadius(25.0);

            Text bedPickupNum = new Text("N/A"); //
            Text reclinerPickupNum = new Text("N/A");
            Text pumpPickupNum = new Text("N/A");
            Text xRayPickupNum = new Text("N/A");

            setUpToolTipText(bedPickupNum, bedPickup);
            setUpToolTipText(reclinerPickupNum, reclinerPickup);
            setUpToolTipText(pumpPickupNum, pumpPickup);
            setUpToolTipText(xRayPickupNum, xRayPickup);

            bedPane.getChildren().clear();
            reclinerPane.getChildren().clear();
            pumpPane.getChildren().clear();
            xRayPane.getChildren().clear();

            //Retrieving the observable list of the Stack Pane
            ObservableList<Node> bedList = bedPane.getChildren();
            ObservableList<Node> reclinerList = reclinerPane.getChildren();
            ObservableList<Node> pumpList = pumpPane.getChildren();
            ObservableList<Node> xRayList = xRayPane.getChildren();

            //Adding all the nodes to the pane bottom to top
            bedList.addAll(bedChart, bedIcon, bedPickup, bedPickupNum);
            reclinerList.addAll(reclinerChart, reclinerIcon, reclinerPickup, reclinerPickupNum);
            pumpList.addAll(pumpChart, pumpIcon, pumpPickup, pumpPickupNum);
            xRayList.addAll(xRayChart, xRayIcon, xRayPickup, xRayPickupNum);

            bedList.get(2).setTranslateX(60);
            bedList.get(2).setTranslateY(-60);
            bedList.get(3).setTranslateX(60);
            bedList.get(3).setTranslateY(-60);

            reclinerList.get(2).setTranslateX(60);
            reclinerList.get(2).setTranslateY(-60);
            reclinerList.get(3).setTranslateX(60);
            reclinerList.get(3).setTranslateY(-60);

            pumpList.get(2).setTranslateX(60);
            pumpList.get(2).setTranslateY(-60);
            pumpList.get(3).setTranslateX(60);
            pumpList.get(3).setTranslateY(-60);

            xRayList.get(2).setTranslateX(60);
            xRayList.get(2).setTranslateY(-60);
            xRayList.get(3).setTranslateX(60);
            xRayList.get(3).setTranslateY(-60);
            return;
        }

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
        dirtyEquip.numOfBeds        = dirtyBeds.size();
        dirtyEquip.numOfPumps       = dirtyPumps.size();
        dirtyEquip.numOfXRays       = dirtyXRays.size();

        inUseEquip.numOfBeds        = inUseBeds.size();
        inUseEquip.numOfXRays       = inUseXRays.size();
        inUseEquip.numOfPumps       = inUsePumps.size();
        inUseEquip.numOfRecliners   = inUseRecliners.size();

        readyEquip.numOfXRays       = cleanXRays.size();
        readyEquip.numOfPumps       = cleanPumps.size();
        readyEquip.numOfRecliners   = cleanRecliners.size();
        readyEquip.numOfBeds        = cleanBeds.size();


        bedPane.getChildren().clear();
        reclinerPane.getChildren().clear();
        pumpPane.getChildren().clear();
        xRayPane.getChildren().clear();

        ObservableList<PieChart.Data> bedPieChartData = createData(dirtyEquip.numOfBeds, readyEquip.numOfBeds, inUseEquip.numOfBeds);
        ObservableList<PieChart.Data> reclinerPieChartData = createData(dirtyEquip.numOfRecliners, readyEquip.numOfRecliners, inUseEquip.numOfRecliners);
        ObservableList<PieChart.Data> pumpPieChartData = createData(dirtyEquip.numOfPumps, readyEquip.numOfPumps, inUseEquip.numOfPumps);
        ObservableList<PieChart.Data> xRayPieChartData = createData(dirtyEquip.numOfXRays, readyEquip.numOfXRays, inUseEquip.numOfXRays);

        DoughnutChart bedChart = new DoughnutChart(bedPieChartData);
        bedChart.setLabelsVisible(false);
        bedChart.setLegendVisible(false);
        DoughnutChart reclinerChart = new DoughnutChart(reclinerPieChartData);
        reclinerChart.setLabelsVisible(false);
        reclinerChart.setLegendVisible(false);
        DoughnutChart pumpChart = new DoughnutChart(pumpPieChartData);
        pumpChart.setLabelsVisible(false);
        pumpChart.setLegendVisible(false);
        DoughnutChart xRayChart = new DoughnutChart(xRayPieChartData);
        xRayChart.setLabelsVisible(false);
        xRayChart.setLegendVisible(false);

        setUpToolTip(bedChart.getData());
        setUpToolTip(reclinerChart.getData());
        setUpToolTip(pumpChart.getData());
        setUpToolTip(xRayChart.getData());

        SVGParser svgParser = new SVGParser();
        String bedContent = svgParser.getPath("static/icons/bed-solid.svg");
        SVGGlyph bedIcon = new SVGGlyph(bedContent);
        bedIcon.setSize(50);
        setUpToolTipIcon(bedIcon, "Beds");

        String reclinerContent = svgParser.getPath("static/icons/couch-solid.svg");
        SVGGlyph reclinerIcon = new SVGGlyph(reclinerContent);
        reclinerIcon.setSize(50);
        setUpToolTipIcon(reclinerIcon, "Recliners");

        String pumpContent = svgParser.getPath("static/icons/pump-medical-solid.svg");
        SVGGlyph pumpIcon = new SVGGlyph(pumpContent);
        pumpIcon.setSize(50);
        setUpToolTipIcon(pumpIcon, "Infusion Pumps");

        String xRayContent = svgParser.getPath("static/icons/x-ray-solid.svg");
        SVGGlyph xRayIcon = new SVGGlyph(xRayContent);
        xRayIcon.setSize(50);
        setUpToolTipIcon(xRayIcon, "X-Rays");

        //Retrieving the observable list of the Stack Pane
        ObservableList<Node> bedList = bedPane.getChildren();
        ObservableList<Node> reclinerList = reclinerPane.getChildren();
        ObservableList<Node> pumpList = pumpPane.getChildren();
        ObservableList<Node> xRayList = xRayPane.getChildren();

        Circle bedPickup = new Circle();
        bedPickup.setFill(Color.AQUA);
        bedPickup.setStroke(Color.DARKBLUE);
        bedPickup.setRadius(20.0);

        Circle reclinerPickup = new Circle();
        reclinerPickup.setFill(Color.AQUA);
        reclinerPickup.setStroke(Color.DARKBLUE);
        reclinerPickup.setRadius(20.0);

        Circle pumpPickup = new Circle();
        pumpPickup.setFill(Color.AQUA);
        pumpPickup.setStroke(Color.DARKBLUE);
        pumpPickup.setRadius(20.0);

        Circle xRayPickup = new Circle();
        xRayPickup.setFill(Color.AQUA);
        xRayPickup.setStroke(Color.DARKBLUE);
        xRayPickup.setRadius(20.0);

        Text bedPickupNum = new Text("0"); // todo Set with Query
        Text reclinerPickupNum = new Text("0");
        Text pumpPickupNum = new Text("0");
        Text xRayPickupNum = new Text("0");

        setUpToolTipText(bedPickupNum, bedPickup);
        setUpToolTipText(reclinerPickupNum, reclinerPickup);
        setUpToolTipText(pumpPickupNum, pumpPickup);
        setUpToolTipText(xRayPickupNum, xRayPickup);

        //Adding all the nodes to the pane bottom to top
        bedList.addAll(bedChart, bedIcon, bedPickup, bedPickupNum);
        reclinerList.addAll(reclinerChart, reclinerIcon, reclinerPickup, reclinerPickupNum);
        pumpList.addAll(pumpChart, pumpIcon, pumpPickup, pumpPickupNum);
        xRayList.addAll(xRayChart, xRayIcon, xRayPickup, xRayPickupNum);

        bedList.get(2).setTranslateX(60);
        bedList.get(2).setTranslateY(-60);
        bedList.get(3).setTranslateX(60);
        bedList.get(3).setTranslateY(-60);

        reclinerList.get(2).setTranslateX(60);
        reclinerList.get(2).setTranslateY(-60);
        reclinerList.get(3).setTranslateX(60);
        reclinerList.get(3).setTranslateY(-60);

        pumpList.get(2).setTranslateX(60);
        pumpList.get(2).setTranslateY(-60);
        pumpList.get(3).setTranslateX(60);
        pumpList.get(3).setTranslateY(-60);

        xRayList.get(2).setTranslateX(60);
        xRayList.get(2).setTranslateY(-60);
        xRayList.get(3).setTranslateX(60);
        xRayList.get(3).setTranslateY(-60);
    }

    private void loadFloors(){
        FloorDAO floorDAO = new FloorDAO();
        List<Floor> lof = floorDAO.getAll();

        floorNodeControllerList.clear();
        floorVBox.getChildren().clear();

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
            floorNode.getGroup().getChildren().get(0).setOnMouseClicked(e -> {
                try {
                    onFloorClicked(e, floorNode);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private ObservableList<PieChart.Data> createData(int dirty, int available, int unavailable) {
        ObservableList<PieChart.Data> list;
        if(dirty == 0 && available == 0 && unavailable == 0){
            list = FXCollections.observableArrayList(
                    new PieChart.Data("No Equipment Available", 0));
        }
        else{
            list = FXCollections.observableArrayList(
                    new PieChart.Data("Dirty", dirty),
                    new PieChart.Data("Available", available),
                    new PieChart.Data("Unavailable", unavailable));
        }
        return list;
    }

    private void setUpToolTip(ObservableList<PieChart.Data> datas){
        datas.forEach(data -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setStyle("-fx-font-size: 15;");
            tooltip.setShowDelay(new Duration(0));
            tooltip.setText(data.getName() + ": " + (int) data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
                    tooltip.setText(String.valueOf(newValue)));
        });
        int colorCounter = 0;
        for(PieChart.Data data : datas){
            if(colorCounter == 0) datas.get(0).getNode().setStyle("-fx-pie-color: red;");
            if(colorCounter == 1) datas.get(1).getNode().setStyle("-fx-pie-color: #069420;");
            if(colorCounter == 2) datas.get(2).getNode().setStyle("-fx-pie-color: #ebb420;");
            colorCounter++;
        }
    }

    private void setUpToolTipIcon(SVGGlyph icon, String name){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: white;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-opacity: 80%;\n" +
                "    -fx-stroke: -color-primary;\n" +
                "    -fx-stroke-width: 1;\n" +
                "    -fx-font-size: 15;");
        tooltip.setShowDelay(new Duration(0));
        tooltip.setText(name);
        Tooltip.install(icon, tooltip);
    }

    private void setUpToolTipText(Text text, Circle circle){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: white;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-opacity: 80%;\n" +
                "    -fx-stroke: -color-primary;\n" +
                "    -fx-stroke-width: 1;\n" +
                "    -fx-font-size: 15;");
        tooltip.setShowDelay(new Duration(0));
        tooltip.setText("Ready For Pickup: " + text.getText());
        Tooltip.install(text, tooltip);
        Tooltip.install(circle, tooltip);
    }

    // This should only be called in initialize
    private void initializeToolTip(ObservableList<PieChart.Data> datas){
        datas.forEach(data -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setStyle("-fx-background-color: white;\n" +
                    "    -fx-text-fill: black;\n" +
                    "    -fx-opacity: 80%;\n" +
                    "    -fx-stroke: -color-primary;\n" +
                    "    -fx-stroke-width: 1;\n" +
                    "    -fx-font-size: 15;");
            tooltip.setShowDelay(new Duration(0));
            tooltip.setText(data.getName() + ": " + (int) data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
                    tooltip.setText(String.valueOf(newValue)));
        });
        datas.get(0).getNode().setStyle("-fx-pie-color: #333232;");
    }

    @FXML
    void onFloorClicked(MouseEvent event, FloorNode floorNode) throws IOException {
        //if(addFloorClicked) return;
        this.selectedFloor = floorNode.getFloor();
        editButton.setDisable(false);
        deleteButton.setDisable(false);

        for(FloorNode floorNodes : floorNodeControllerList){
            floorNodes.getGroup().getChildren().get(0).setStyle("-fx-background-color: #FFFFFF");
        }

        floorNode.getGroup().getChildren().get(0).setStyle("-fx-background-color: #B3EDF0");

        floorTitle.setText(selectedFloor.getLongName());
        descriptionText.setText(floorNode.getFloor().getDescription());


        ByteArrayInputStream bis = new ByteArrayInputStream(selectedFloor.getImage());
        BufferedImage img = ImageIO.read(bis);
        Image image = SwingFXUtils.toFXImage(img, null);

        floorImage.fitWidthProperty().bind(imageBox.widthProperty());
        floorImage.fitHeightProperty().bind(imageBox.heightProperty());
        floorImage.setImage(image);

        order = selectedFloor.getOrder();

        longName.setText(selectedFloor.getLongName());
        shortName.setText(selectedFloor.getShortName());
        description.setText(selectedFloor.getDescription());
        this.image.setText(selectedFloor.getImageSrc());

        loadEquipment();

    }
}



