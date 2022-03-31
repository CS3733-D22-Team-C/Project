package edu.wpi.cs3733.D22.teamC;

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
    private final String MENU_BAR_COMPONENT_PATH = "component/menu-bar.fxml";
    private final String MEDICAL_EQUIPMENT = "view/service_request/medical-equipment-view.fxml";
    private final String SANITARY_SERVICES_PATH = "view/service_request/sanitation-view.fxml";
    private final String SERVICE_REQUEST_SELECT = "view/general/view-service.fxml";
    private final String FACILITY_MAINTENANCE_PATH = "view/service_request/facility-maintenance.fxml";

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
      

        //setView(SERVICE_REQUEST_SELECT);
        setView(FACILITY_MAINTENANCE_PATH);
        //setView(MEDICAL_EQUIPMENT);

        // Initialize Database Manager
        DBManager.startup();
    }

    @Override
    public void stop() {
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
