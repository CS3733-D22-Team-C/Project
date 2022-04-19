package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.medical_equipment.MedicalEquipmentTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationInfoController implements Initializable {
    // Constants
    private final String LOCATION_ICON = "M168.3 499.2C116.1 435 0 279.4 0 192C0 85.96 85.96 0 192 0C298 0 384 85.96 384 192C384 279.4 267 435 215.7 499.2C203.4 514.5 180.6 514.5 168.3 499.2H168.3zM192 256C227.3 256 256 227.3 256 192C256 156.7 227.3 128 192 128C156.7 128 128 156.7 128 192C128 227.3 156.7 256 192 256";
    private final String MEDICAL_EQUIPMENT_ICON = "M480 112c-44.18 0-80 35.82-80 80c0 32.84 19.81 60.98 48.11 73.31v78.7c0 57.25-50.25 104-112 104c-60 0-109.3-44.1-111.9-99.23C296.1 333.8 352 269.3 352 191.1V36.59c0-11.38-8.15-21.38-19.28-23.5L269.8 .4775c-13-2.625-25.54 5.766-28.16 18.77L238.4 34.99c-2.625 13 5.812 25.59 18.81 28.22l30.69 6.059L287.9 190.7c0 52.88-42.13 96.63-95.13 97.13c-53.38 .5-96.81-42.56-96.81-95.93L95.89 69.37l30.72-6.112c13-2.5 21.41-15.15 18.78-28.15L142.3 19.37c-2.5-13-15.15-21.41-28.15-18.78L51.28 12.99C40.15 15.24 32 25.09 32 36.59v155.4c0 77.25 55.11 142 128.1 156.8C162.7 439.3 240.6 512 336 512c97 0 176-75.37 176-168V265.3c28.23-12.36 48-40.46 48-73.25C560 147.8 524.2 112 480 112zM480 216c-13.25 0-24-10.75-24-24S466.7 168 480 168S504 178.7 504 192S493.3 216 480 216z";
    private final String SERVICE_REQUEST_ICON =  "M336 64h-53.88C268.9 26.8 233.7 0 192 0S115.1 26.8 101.9 64H48C21.5 64 0 85.48 0 112v352C0 490.5 21.5 512 48 512h288c26.5 0 48-21.48 48-48v-352C384 85.48 362.5 64 336 64zM96 392c-13.25 0-24-10.75-24-24S82.75 344 96 344s24 10.75 24 24S109.3 392 96 392zM96 296c-13.25 0-24-10.75-24-24S82.75 248 96 248S120 258.8 120 272S109.3 296 96 296zM192 64c17.67 0 32 14.33 32 32c0 17.67-14.33 32-32 32S160 113.7 160 96C160 78.33 174.3 64 192 64zM304 384h-128C167.2 384 160 376.8 160 368C160 359.2 167.2 352 176 352h128c8.801 0 16 7.199 16 16C320 376.8 312.8 384 304 384zM304 288h-128C167.2 288 160 280.8 160 272C160 263.2 167.2 256 176 256h128C312.8 256 320 263.2 320 272C320 280.8 312.8 288 304 288z";

    // Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab locationTab;
    @FXML private Tab medicalEquipmentTab;
    @FXML private Tab serviceRequestTab;
    @FXML private Tab patientsTab;



    // Location Info - Form Fields
    @FXML private TextField shortNameField;
    @FXML private TextField buildingField;
    @FXML private TextField longNameField;
    @FXML private ComboBox<Floor> floorComboBox;
    @FXML private ComboBox<Location.NodeType> nodeComboBox;

    // Medical Equipment - Table
    @FXML JFXTreeTableView medicalEquipmentTable;
    MedicalEquipmentTableDisplay medicalEquipmentTableDisplay;

    // Service Requests - Table
    @FXML JFXTreeTableView serviceRequestTable;
    ServiceRequestTableDisplay<ServiceRequest> serviceRequestTableDisplay;

    //Patients - Table
    @FXML
    private JFXTreeTableView patientsTable;
    PatientTableDisplay patientTableDisplay;

    // Location Info - Form Buttons
    @FXML private Button deselectButton;
    @FXML private Button revertButton;
    @FXML private Button deleteButton;

    // References
    MapViewController mapViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SVGParser svgParser = new SVGParser();
        String patientIcon = svgParser.getPath("static/icons/employee_icon.svg");
        SVGGlyph patientContent = new SVGGlyph(patientIcon);
        patientContent.setSize(20);

        // Initialize Tabs
        setTabIcon(locationTab, LOCATION_ICON);
        setTabIcon(medicalEquipmentTab, MEDICAL_EQUIPMENT_ICON);
        setTabIcon(serviceRequestTab, SERVICE_REQUEST_ICON);
        //setTabIcon(patientsTab,); //TODO

        // Initialize Service Request Info
        serviceRequestTableDisplay = new ServiceRequestTableDisplay<>(serviceRequestTable);

        // Initialize Medical Equipment Info
        medicalEquipmentTableDisplay = new MedicalEquipmentTableDisplay(medicalEquipmentTable);

        //Initialize Patients Info
        patientTableDisplay = new PatientTableDisplay(patientsTable);
    }

    /**
     * Setup the LocationInfoController and view with external data and references.
     * @param mapViewController The parent controller to communicate with.
     */
    public void setup(MapViewController mapViewController) {
        this.mapViewController = mapViewController;

        // Select default tab
        tabPane.getSelectionModel().select(0);

        // Set Combo Box values
        floorComboBox.getItems().setAll(mapViewController.getFloorManager().getAll());
        nodeComboBox.getItems().setAll(Location.NodeType.values());

        // Hide when inactive
        setEditable(false);
        setVisible(false);
    }

    /**
     * Sets an SVG icon to a tab.
     * @param tab The tab to have an icon set for.
     * @param svgContent The SVG content which defines the icon.
     */
    public void setTabIcon(Tab tab, String svgContent) {
        SVGPath svg = new SVGPath();
        svg.setContent(svgContent);
        svg.getStyleClass().add("icon");
        tab.setGraphic(svg);
        tab.setText(null);
    }

    //#region Pane Interaction
        /**
         * Sets Location Info to editable and displays edit-only buttons.
         * @param editable Mode which the Location Info Pane is setting to.
         */
        public void setEditable(boolean editable) {
            shortNameField.setDisable(!editable);
            longNameField.setDisable(!editable);
            buildingField.setDisable(!editable);
            floorComboBox.setDisable(true);
            nodeComboBox.setDisable(!editable);

            revertButton.setVisible(editable);
            deleteButton.setVisible(editable);
        }

        /**
         * Sets the visibility of the InfoPane.
         * @param visible The visibility to set to.
         */
        public void setVisible(boolean visible) {
            tabPane.setVisible(visible);
        }
    //#endregion

    //#region Location Interaction
        /**
         * Set Location and initialize data fields for the given Location object.
         * @param location The Location object which this info splitPane will display info of.
         */
        public void setLocation(Location location) {
            setVisible(location != null);

            if (location == null) return;

            // Location Info
            shortNameField.setText(location.getShortName());
            longNameField.setText(location.getLongName());
            buildingField.setText(location.getBuilding());
            ComponentWrapper.setValueSilently(floorComboBox, mapViewController.getFloorManager().getByID(location.getFloor()));
            ComponentWrapper.setValueSilently(nodeComboBox, location.getNodeType());

            // Medical Equipment
            populateMedicalEquipmentTable(location);

            // Service Requests
            serviceRequestTableDisplay.emptyTable();
            new ServiceRequestDAO().getAllSRByLocation(location.getID()).forEach(serviceRequestTableDisplay::addObject);

            // Patient
            patientTableDisplay.emptyTable();
            new PatientDAO().getPatientByLocation(location.getID()).forEach(patientTableDisplay::addObject);

            revertButton.setDisable(location.equals(new LocationDAO().getByID(location.getID())));
        }

        /**
         * Save updates Location on LocationManager.
         */
        @FXML
        private void updateLocation() {
            Location location = mapViewController.getLocationManager().getCurrent();

            // Copy original for comparison
            Location original = new Location();
            original.Copy(location);

            location.setShortName(shortNameField.getText());
            location.setLongName(longNameField.getText());
            location.setBuilding(buildingField.getText());
            location.setFloor(floorComboBox.getValue().getID());
            location.setNodeType(nodeComboBox.getValue());

            if (!original.equals(location)) {
                revertButton.setDisable(false);
                mapViewController.getLocationManager().updatesOccured();

                if (!original.getNodeType().equals(location.getNodeType())) {
                    ((LocationMapNode) mapViewController.getLocationManager().getByLocation(location)).updateIcon();
                }
            }
        }

        public void populateMedicalEquipmentTable(Location location) {
            medicalEquipmentTableDisplay.emptyTable();
            new MedicalEquipmentDAO().getEquipmentByLocation(location.getID()).forEach(medicalEquipmentTableDisplay::addObject);
        }
    //#endregion

    //#region FXML Events
        @FXML
        void onDeselectButtonPressed(ActionEvent event) {
            mapViewController.getLocationManager().unfocus();
        }

        @FXML
        void onRevertButtonPressed(ActionEvent event) {
            mapViewController.getLocationManager().resetObject(mapViewController.getLocationManager().getCurrent());
            revertButton.setDisable(true);
        }

        @FXML
        void onDeleteButtonPressed(ActionEvent event) {
            mapViewController.getLocationManager().removeObject(mapViewController.getLocationManager().getCurrent());
            setVisible(false);
        }
    //#endregion
}
