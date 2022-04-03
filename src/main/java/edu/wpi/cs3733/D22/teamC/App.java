package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.MedicalEquipmentSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.MedicalEquipmentSRCSVWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class App extends Application {
    // Constants
    public static final String BASE_COMPONENT_PATH = "view/component/base.fxml";
    public static final String MENU_BAR_COMPONENT_PATH = "view/component/menu_bar.fxml";

    public static final String HOME_PATH = "view/general/home.fxml";
    public static final String VIEW_LOCATIONS_PATH = "view/location/view_locations.fxml";
    public static final String VIEW_SERVICE_REQUESTS_PATH = "view/service_request/view_service_requests.fxml";

    // Declare Singleton Instance
    public static App instance;

    // Variables
    private Stage stage;

    @Override
    public void init() {
        // Initialize Database Manager
        DBManager.startup(DBManager.DEVELOPMENT_DATABASE_NAME).initializeTables(true);

        // Load CSV Data
        LocationCSVReader csvReader = new LocationCSVReader();
        List<Location> locations = csvReader.readFile("TowerLocations.csv");
        if (locations != null) {
            LocationDAO locationDAO = new LocationDAOImpl();
            for (Location location : locations) {
                locationDAO.insertLocation(location);
            }
        }

        // loading CSV for medical equipment service request
        MedicalEquipmentSRCSVReader mECSVReader = new MedicalEquipmentSRCSVReader();
        List<MedicalEquipmentSR> MedicalEquipmentSRs = mECSVReader.readFile("MedEquipReq.csv");
        if(MedicalEquipmentSRs != null){
            MedicalEquipmentSRDAOImpl serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
            for(MedicalEquipmentSR medEquipSR : MedicalEquipmentSRs){
                serviceRequestDAO.insertServiceRequest(medEquipSR);
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
      
        setView("view/component/map.fxml");
    }

    @Override
    public void stop() {
        // Export CSV Data
        LocationCSVWriter csvWriter = new LocationCSVWriter();
        LocationDAO locationDAO = new LocationDAOImpl();
        List<Location> locations = locationDAO.getAllLocations();
        if (locations != null) {
            csvWriter.writeFile("TowerLocations.csv", locations);
        }
        MedicalEquipmentSRCSVWriter mECSVWriter = new MedicalEquipmentSRCSVWriter();
        MedicalEquipmentSRDAOImpl serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        List<MedicalEquipmentSR> serviceRequests = serviceRequestDAO.getAllServiceRequests();
        
        if(serviceRequests != null){
            mECSVWriter.writeFile("MedEquipReq.csv", serviceRequests);
        }
        // Shutdown Database Manager
        DBManager.shutdown();

        log.info("Shutting Down");
    }

    /**
     * Allows us to change the view of the window
     * @param viewFile path to the .fxml file to be displayed
     */
    public void setView(String viewFile) {
        try {
            // Load Base Page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(BASE_COMPONENT_PATH));
            BorderPane baseNode = loader.load();

            // Load View
            loader = new FXMLLoader(); // might not need this
            loader.setLocation(getClass().getResource(viewFile));
            Node viewNode = loader.load();

            // Load Menu Bar
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(MENU_BAR_COMPONENT_PATH));
            Node menuBarNode = loader.load();

            // Embed views and components
            baseNode.setCenter(viewNode);
//            baseNode.setTop(menuBarNode);

            Scene scene = new Scene(baseNode);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Could not load file " + viewFile);
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
