package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.utils.DoughnutChart;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class TempController implements Initializable {

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
    @FXML private MFXButton cancelButton;
    @FXML private MFXButton confirmButton;
    @FXML private MFXButton incrementButton;
    @FXML private MFXButton decrementButton;

    // Text Areas
    @FXML private JFXTextArea description;
    @FXML private MFXTextField image;
    @FXML private MFXTextField longName;
    @FXML private MFXTextField shortName;
    //#endregion

    // Buttons
    @FXML private MFXButton addFloorButton;
    @FXML private MFXButton editButton;
    @FXML private MFXButton deleteButton;

    @FXML private ImageView floorImage;
    @FXML private Label floorTitle;
    @FXML private HBox imageBox;

    // Pie Chart Panes
    @FXML private StackPane bedPane;
    @FXML private StackPane reclinerPane;
    @FXML private StackPane pumpPane;
    @FXML private StackPane xRayPane;
    @FXML private HBox topBox; // might be unneeded
    @FXML private HBox bottomBox; // might be unneeded

    @FXML private Text descriptionText;

    @FXML HBox container; // might not be needed

    // Local Variables
    private Floor selectedFloor;
    private int order;
        // Equipments for Pie Charts
        private Equipment readyEquip;
        private Equipment dirtyEquip;
        private Equipment inUseEquip;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedFloor = null;
        order = 1;
        FloorDAO floorDAO = new FloorDAO();
        selectedFloor = floorDAO.getAll().get(0);
        loadEquipment();
    }

    //#region base add/edit/delete floor
    @FXML
    void onAddFloorClicked(ActionEvent event) {
        floorEditPanel.setVisible(true);
        container.setVisible(false);
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {

    }

    @FXML
    void onEditClicked(ActionEvent event) {

    }
    //#endregion

    //#region add/edit floor panel
    @FXML
    void onCancelClicked(ActionEvent event) {
        floorEditPanel.setVisible(false);
        container.setVisible(true);
    }

    @FXML
    void onConfirmClicked(ActionEvent event) {
        floorEditPanel.setVisible(false);
        container.setVisible(true);
    }

    @FXML
    void onDecrementClicked(ActionEvent event) {
        order--;
        if(order < 1) order = 1;
        System.out.println(order);
    }

    @FXML
    void onIncrementClicked(ActionEvent event) {
        order++;
        System.out.println(order);
    }
    //#endregion

    private void loadEquipment(){
        dirtyEquip = new Equipment();
        inUseEquip = new Equipment();
        readyEquip = new Equipment();

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
            bedIcon.setSize(30);
            setUpToolTipIcon(bedIcon, "Beds");

            String reclinerContent = svgParser.getPath("static/icons/couch-solid.svg");
            SVGGlyph reclinerIcon = new SVGGlyph(reclinerContent);
            reclinerIcon.setSize(30);
            setUpToolTipIcon(reclinerIcon, "Recliners");

            String pumpContent = svgParser.getPath("static/icons/pump-medical-solid.svg");
            SVGGlyph pumpIcon = new SVGGlyph(pumpContent);
            pumpIcon.setSize(30);
            setUpToolTipIcon(pumpIcon, "Infusion Pumps");

            String xRayContent = svgParser.getPath("static/icons/x-ray-solid.svg");
            SVGGlyph xRayIcon = new SVGGlyph(xRayContent);
            xRayIcon.setSize(30);
            setUpToolTipIcon(xRayIcon, "X-Rays");

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
            bedList.addAll(bedChart, bedIcon);
            reclinerList.addAll(reclinerChart, reclinerIcon);
            pumpList.addAll(pumpChart, pumpIcon);
            xRayList.addAll(xRayChart, xRayIcon);
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
        bedList.addAll(bedChart, bedIcon);
        reclinerList.addAll(reclinerChart, reclinerIcon);
        pumpList.addAll(pumpChart, pumpIcon);
        xRayList.addAll(xRayChart, xRayIcon);
    }



    private ObservableList<PieChart.Data> createData(int dirty, int available, int unavailable) {
        ObservableList<PieChart.Data> list;
        if(dirty == 0 && available == 0 && unavailable == 0){
            list = FXCollections.observableArrayList(
                    new PieChart.Data("No Equipment Available", -1));
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
        tooltip.setStyle("-fx-font-size: 15;");
        tooltip.setShowDelay(new Duration(0));
        tooltip.setText(name);
        Tooltip.install(icon, tooltip);
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
}



