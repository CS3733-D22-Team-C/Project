package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class App extends Application {
    public static class View<T> {
        Node node;
        T controller;

        public View(Node node, T controller) {
            this.node = node;
            this.controller = (T) controller;
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
    public static final String SIDEBAR_PATH = "view/component/sidebar_menu.fxml";
    public static final String DRAWER_CONTENT_PATH = "view/component/drawer_content.fxml";

    public static final String LOGIN_PATH = "view/general/login.fxml";
    public static final String VIEW_LOCATIONS_PATH = "view/location/view_locations.fxml";

    public static final String VIEW_SERVICE_REQUESTS_PATH = "view/service_request/view_service_requests.fxml";
    public static final String SERVICE_REQUEST_LANDING_PAGE = "view/service_request/service_request_landing_page.fxml";

    public static final String MAP_PATH = "view/location/map/base_map_view.fxml";
    public static final String TOGGLE_BUTTON_PATH = "view/component/database_toggle.fxml";

    public static final String BASE_CSS_PATH = "css/base.css";
    //public static final String IMAGE_PATH = "static/images/BrighamAndWomensHospital.png";
    
    // Singleton Instance
    public static App instance;

    //Employee
    private Employee userAccount;

    // Variables
    private Stage stage;
    private Scene scene;

    @Override
    public void init() {
        SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        
        // Load CSV Data - Floor
        {
            FloorCSVReader csvReader = new FloorCSVReader();
            List<Floor> floors = csvReader.readFile("TowerFloors.csv");
            if (floors != null) {
                FloorDAO floorDAO = new FloorDAO();
                for (Floor floor : floors) {
                    floorDAO.insert(floor);
                }
            }
        }

        // Load CSV Data - Location
        {
            LocationCSVReader csvReader = new LocationCSVReader();
            List<Location> locations = csvReader.readFile("TowerLocations.csv");
            if (locations != null) {
                LocationDAO locationDAO = new LocationDAO();
                for (Location location : locations) {
                    locationDAO.insert(location);
                }
            }
        }

        // Load CSV Data = Employee
        {
            EmployeeCSVReader csvReader =  new EmployeeCSVReader();
            List<Employee> employees = csvReader.readFile("Employees.csv");
            if(employees !=null){
                EmployeeDAO employeeDAO = new EmployeeDAO();
                for(Employee employee : employees){
                    employeeDAO.insert(employee);
                }
            }
        }

        // Load CSV Data - Medical Equipment Service Request
        {
            MedicalEquipmentSRCSVReader csvReader = new MedicalEquipmentSRCSVReader();
            List<MedicalEquipmentSR> MedicalEquipmentSRs = csvReader.readFile("MedEquipReq.csv");
            if(MedicalEquipmentSRs != null){
                MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
                for(MedicalEquipmentSR medEquipSR : MedicalEquipmentSRs){
                    serviceRequestDAO.insert(medEquipSR);
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

        setViewStatic(LOGIN_PATH);
    }

    @Override
    public void stop() {
        // Export CSV Data - Floor
        {
            FloorCSVWriter csvWriter = new FloorCSVWriter();
            FloorDAO floorDAO = new FloorDAO();
            List<Floor> floors = floorDAO.getAll();
            if (floors != null) {
                csvWriter.writeFile("TowerFloors.csv", floors);
            }
        }

        // Export CSV Data - Location
        {
            LocationCSVWriter csvWriter = new LocationCSVWriter();
            LocationDAO locationDAO = new LocationDAO();
            List<Location> locations = locationDAO.getAll();
            if (locations != null) {
                csvWriter.writeFile("TowerLocations.csv", locations);
            }
        }

        //Export CSV Data - Employee
        {
            EmployeeCSVWriter csvWriter = new EmployeeCSVWriter();
            EmployeeDAO employeeDAO = new EmployeeDAO();
            List<Employee> employees = employeeDAO.getAll();
            if(employees!=null){
                csvWriter.writeFile("Employees.csv", employees);
            }
        }


        // Export CSV Data - Medical Equipment Service Requests
        {
            MedicalEquipmentSRCSVWriter csvWriter = new MedicalEquipmentSRCSVWriter();
            MedicalEquipmentSRDAO serviceRequestDAO = new MedicalEquipmentSRDAO();
            List<MedicalEquipmentSR> serviceRequests = serviceRequestDAO.getAll();
            if (serviceRequests != null){
                csvWriter.writeFile("MedEquipReq.csv", serviceRequests);
            }
        }


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

    public void setViewStatic(String viewFile)
    {
        Node node = loadView(viewFile).getNode();
        setViewStatic(node);
    }

    /**
     * Set view for window from a node.
     * @param viewNode Node to be displayed.
     */
    public void setView(Node viewNode) {
        // Load Base Node
        BorderPane baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // Load Menu Bar
        // TODO: Find a way to only change the center of the baseNode, nothing else
        Node menuBarNode = loadView(MENU_BAR_COMPONENT_PATH).getNode();

        // Load Sidebar Menu
        Node sidebarNode = loadView(SIDEBAR_PATH).getNode();

        //Load toggle button
        Node toggleButton = loadView(TOGGLE_BUTTON_PATH).getNode();

        // Embed views and components
        //baseNode.setTop(menuBarNode);
        baseNode.setCenter(viewNode);
        baseNode.setLeft(sidebarNode);
        baseNode.setLeft(toggleButton);
        baseNode.autosize();

        if (scene != null) scene.setRoot(baseNode);
        else scene = new Scene(baseNode);
        stage.setScene(scene);
        stage.show();
    }

    public void setViewStatic(Node viewNode)
    {
        // Load Base Node
        BorderPane baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // Load Menu Bar
        // TODO: Find a way to only change the center of the baseNode, nothing else
        Node menuBarNode = loadView(MENU_BAR_COMPONENT_PATH).getNode();

        //Load toggle button
        Node toggleButton = loadView(TOGGLE_BUTTON_PATH).getNode();

        // Embed views and components
        //baseNode.setTop(menuBarNode);
        baseNode.setCenter(viewNode);
        baseNode.setLeft(toggleButton);
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
    @SuppressWarnings("unchecked")
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

    public Employee getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Employee userAccount) {
        this.userAccount = userAccount;
    }


}
