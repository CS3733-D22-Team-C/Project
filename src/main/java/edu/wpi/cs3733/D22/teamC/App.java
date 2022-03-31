package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;

@Slf4j
public class App extends Application {
    // Declare singleton instance
    public static App instance;

    // Constants
    public static final String BASE_VIEW_PATH = "view/general/base-view.fxml";
    private static final String MENU_BAR_COMPONENT_PATH = "component/menu-bar.fxml";
    private static final String MEDICAL_EQUIPMENT = "view/service_request/medical-equipment-view.fxml";
    public static final String MEDICINE_DELIVERY = "view/service_request/medicine-delivery-view.fxml";
    private final String SANITARY_SERVICES_PATH = "view/service_request/sanitation-view.fxml";
    private final String SERVICE_REQUEST_SELECT = "view/general/view-service.fxml";
    private final String SECURITY_REQUEST_SELECT = "view/service_request/security-service-view.fxml";

    // Variables
    private Stage stage;

    @Override
    public void init() {
        // Initialize Database Manager
        DBManager.startup();

        // Load CSV Data
        LocationCSVReader csvReader = new LocationCSVReader();
        List<Location> locations = csvReader.readFile("TowerLocations.csv");
        if (locations != null) {
            LocationDAO locationDAO = new LocationDAOImpl();
            for (Location location : locations) {
                locationDAO.insertLocation(location);
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

        setView(SECURITY_REQUEST_SELECT);


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
            loader.setLocation(getClass().getResource(BASE_VIEW_PATH));
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
            baseNode.setTop(menuBarNode);

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
