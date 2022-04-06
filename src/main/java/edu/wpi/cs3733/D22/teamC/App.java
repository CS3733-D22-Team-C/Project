package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.fileio.csv.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class App extends Application {
    public static class View<T> {
        Node node;
        T controller;

        public View(Node node, T controller) {
            this.node = node;
            this.controller = controller;
        }

        public Node getNode() {
            return node;
        }

        public T getController() {
            return controller;
        }
    }

    // Constants
    public static final String BASE_COMPONENT_PATH = "view/component/base.fxml";
    public static final String MENU_BAR_COMPONENT_PATH = "view/component/menu_bar.fxml";

    public static final String HOME_PATH = "view/general/home.fxml";
    public static final String VIEW_LOCATIONS_PATH = "view/location/view_locations.fxml";
    public static final String VIEW_SERVICE_REQUESTS_PATH = "view/service_request/view_service_requests.fxml";

    // Singleton Instance
    public static App instance;

    // Variables
    private Stage stage;
    private Scene scene;

    @Override
    public void init() {
        // Initialize Database Manager
        DBManager.startup(DBManager.DEVELOPMENT_DATABASE_NAME).initializeTables(true);

        // Load CSV Data - Floor
        {
            FloorCSVReader csvReader = new FloorCSVReader();
            List<Floor> floors = csvReader.readFile("TowerFloors.csv");
            if (floors != null) {
                FloorDAO floorDAO = new FloorDAOImpl();
                for (Floor floor : floors) {
                    floorDAO.insertFloor(floor);
                }
            }
        }

        // Load CSV Data - Location
        {
            LocationCSVReader csvReader = new LocationCSVReader();
            List<Location> locations = csvReader.readFile("TowerLocations.csv");
            if (locations != null) {
                LocationDAO locationDAO = new LocationDAOImpl();
                for (Location location : locations) {
                    locationDAO.insertLocation(location);
                }
            }
        }

        // Load CSV Data - Medical Equipment Service Request
        {
            MedicalEquipmentSRCSVReader csvReader = new MedicalEquipmentSRCSVReader();
            List<MedicalEquipmentSR> MedicalEquipmentSRs = csvReader.readFile("MedEquipReq.csv");
            if(MedicalEquipmentSRs != null){
                MedicalEquipmentSRDAOImpl serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
                for(MedicalEquipmentSR medEquipSR : MedicalEquipmentSRs){
                    serviceRequestDAO.insertServiceRequest(medEquipSR);
                }
            }
        }

        log.info("Starting Up");
    }

    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;

        // Store window as stage
        stage = primaryStage;
        stage.setFullScreen(true);

        setView(HOME_PATH);
    }

    @Override
    public void stop() {
        // Export CSV Data - Floor
        {
            FloorCSVWriter csvWriter = new FloorCSVWriter();
            FloorDAO floorDAO = new FloorDAOImpl();
            List<Floor> floors = floorDAO.getAllFloors();
            if (floors != null) {
                csvWriter.writeFile("TowerFloors.csv", floors);
            }
        }

        // Export CSV Data - Location
        {
            LocationCSVWriter csvWriter = new LocationCSVWriter();
            LocationDAO locationDAO = new LocationDAOImpl();
            List<Location> locations = locationDAO.getAllLocations();
            if (locations != null) {
                csvWriter.writeFile("TowerLocations.csv", locations);
            }
        }

        // Export CSV Data - Medical Equipment Service Requests
        {
            MedicalEquipmentSRCSVWriter csvWriter = new MedicalEquipmentSRCSVWriter();
            MedicalEquipmentSRDAOImpl serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
            List<MedicalEquipmentSR> serviceRequests = serviceRequestDAO.getAllServiceRequests();
            if (serviceRequests != null){
                csvWriter.writeFile("MedEquipReq.csv", serviceRequests);
            }
        }

        // Shutdown Database Manager
        DBManager.shutdown();

        log.info("Shutting Down");
    }

    /**
     * Set view for window from a file.
     * @param viewFile Path to the FXML file to be displayed.
     */
    public void setView(String viewFile){
        Node node = loadView(viewFile).getNode();
        setView(node);
    }

    /**
     * Set view for window from a node.
     * @param viewNode Node to be displayed.
     */
    public void setView(Node viewNode) {
        // Load Base Node
        BorderPane baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // Load Menu Bar
        Node menuBarNode = loadView(MENU_BAR_COMPONENT_PATH).getNode();

        // Embed views and components
        baseNode.setTop(menuBarNode);
        baseNode.setCenter(viewNode);
        baseNode.autosize();

        if (scene != null) scene.setRoot(baseNode);
        else scene = new Scene(baseNode);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load a view from a file.
     * @param viewFile Path to the FXML file to be loaded.
     * @return Loaded FXML file wrapped in a View as a Node and Controller.
     */
    public View loadView(String viewFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(viewFile));
            return new View(loader.load(), loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load a view from a file, setting its controller.
     * @param viewFile Path to the FXML file to be loaded.
     * @param controller Controller to be attached to the FXML file.
     * @return Loaded FXML file wrapped in a View as a Node and Controller.
     */
    public View loadView(String viewFile, Object controller) {
        View view = loadView(viewFile);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(viewFile));
            loader.setController(controller);
            return new View(loader.load(), loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Stage getStage() {
        return stage;
    }
}
