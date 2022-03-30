package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class App extends Application {
    // Declare singleton instance
    public static App instance;

    // Constants
    public static final String BASE_VIEW_PATH = "view/general/base-view.fxml";
    private static final String MENU_BAR_COMPONENT_PATH = "component/menu-bar.fxml";
    private static final String MEDICAL_EQUIPMENT = "view/service_request/medical-equipment-view.fxml";

    // Variables
    private Stage stage;

    @Override
    public void init() {
        // Initialize Database Manager
        DBManager.startup();

        log.info("Starting Up");
    }

    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;
        // Store window as stage
        stage = primaryStage;
      
        setView("view/general/demo.fxml");

        // TODO: Setup JUnit Tests !!!
        LocationDAO locationDAO = new LocationDAOImpl();
        {
            Location insertTest = new Location();
            insertTest.setNodeID("Test000");
            insertTest.setBuilding("Tower");
            System.out.println(locationDAO.insertLocation(insertTest)); // true
            System.out.println(locationDAO.getLocation(insertTest.getNodeID()).getNodeID());    // Test000
            System.out.println(locationDAO.getLocation(insertTest.getNodeID()).getBuilding());  // Tower
            System.out.println(locationDAO.getAllLocations().size());   // 1
        }
        {
            Location deleteTest = new Location();
            deleteTest.setNodeID("Test001");
            System.out.println(locationDAO.insertLocation(deleteTest)); // true
            System.out.println(locationDAO.getLocation(deleteTest.getNodeID()).getNodeID());    // Test001
            System.out.println(locationDAO.getAllLocations().size());   // 2
            System.out.println(locationDAO.deleteLocation(deleteTest)); // true
        }
        {
            Location updateTest = locationDAO.getLocation("Test000");
            updateTest.setBuilding("Entrance");
            updateTest.setFloor("F1");
            System.out.println(locationDAO.insertLocation(updateTest)); // false
            System.out.println(locationDAO.updateLocation(updateTest)); // true
            System.out.println(locationDAO.getLocation(updateTest.getNodeID()).getBuilding());  // Entrance
            System.out.println(locationDAO.getLocation(updateTest.getNodeID()).getFloor()); // F1
        }

        //setView(MEDICAL_EQUIPMENT);

    }

    @Override
    public void stop() {
        // Shutdown Database Manager
        DBManager.shutdown();

        log.info("Shutting Down");
    }

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
