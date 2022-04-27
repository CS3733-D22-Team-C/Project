package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.controller.SkeletonController;
import edu.wpi.cs3733.D22.teamC.controller.component.sidebar.DrawerContentController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Stack;

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
        public void setController(T controller){
            this.controller = controller;
        }
    }

    // Constants
    public static final String BASE_COMPONENT_PATH = "view/component/base.fxml";
    public static final String TITLE_BAR_COMPONENT_PATH = "view/component/title_bar.fxml";
    public static final String DRAWER_PATH = "view/component/drawer.fxml";
    public static final String DRAWER_CONTENT_PATH = "view/component/drawer_content.fxml";
    public static final String LOGIN_PATH = "view/general/login.fxml";
    public static final String SERVICE_REQUEST_DASHBOARD = "view/service_request/landing_page/service_request_landing_page.fxml";
    public static final String DASHBOARD_PATH = "view/location/map/base_side_map_view.fxml";
    public static final String DATABASE_PAGE_PATH = "view/general/edit_databases_page.fxml";
    public static final String MAP_PATH = "view/map/floor_map.fxml";

    public static final String ABOUT_PAGE = "view/general/about_page/about_app_page.fxml";
    public static final String USER_PROFILE = "view/general/profile_page/user_dashboard.fxml";

    public static final String MAP_SIDEBAR = "view/map/floor_map.fxml";
    public static final String SHORTCUT_EDIT = "view/service_request/skeleton/sr_shortcut.fxml";
    public static final String CREDIT_PAGE = "view/general/credit.fxml";

    // Singleton Instance
    public static App instance;

    //Employee
    private Employee userAccount;

    // Variables
    private Stage stage;
    private Stack<Stage> activeStages = new Stack<>();
    private Scene scene;
    public BorderPane baseNode;
    public DrawerContentController drawerContentController;

    @Override
    public void init() {
        SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        log.info("Starting Up");
    }
    //Test comment
    @Override
    public void start(Stage primaryStage) {
        // Create singleton instance
        instance = this;

        // On the first run of the app set up the stage, load to login page
        stage = primaryStage;
        activeStages.add(stage);
        stage.setFullScreen(true);
        baseNode = (BorderPane) loadView(BASE_COMPONENT_PATH).getNode();

        // Set the base node to the root
        scene = new Scene(baseNode);
        scene.setRoot(baseNode);

        scene.setOnKeyPressed((KeyEvent event) -> {
            /*
               The left node is null when we are not logged in.
               if this changes, this will break
             */
            if (this.baseNode.getLeft() != null)
            {
                if (event.isControlDown() && event.isShiftDown())
                {
                    switch (event.getCode().getChar())
                    {
                        case "D" :
                            setView(DASHBOARD_PATH);
                            break;
                        case "X" :
                            if (userAccount.getAdmin()) {
                                setView(DATABASE_PAGE_PATH);
                            }
                            break;
                        case "M" :
                            setView(USER_PROFILE);
                            break;
                        case "S" :
                            setView(SERVICE_REQUEST_DASHBOARD);
                            break;
                        case "L" :
                            setView(App.LOGIN_PATH);
                            showMenuBar(false);
                            break;
                        case "Q" :
                            getStage().close();
                            break;
                        default:
                            break;
                    }
                }
            }
        });


        // Set the content of the borderpane to the login page
        baseNode.setCenter(loadView(LOGIN_PATH).getNode());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        log.info("Shutting Down");
    }

    /**
     * Set a skeleton file for a view.
     * @param skeletonFile The path to the skeleton view.
     * @param insertFile The path to the insert view.
     */
    @SuppressWarnings("unchecked")
    public void setSkeletonView(String skeletonFile, String insertFile) {
        View view = loadView(skeletonFile);
        SkeletonController skeletonController = (SkeletonController) view.getController();
        skeletonController.setUp(insertFile);
        setView(view.getNode());
    }

    /**
     * Set view for window from a file.
     * @param viewFile Path to the FXML file to be displayed.
     */
    public void setView(String viewFile){
        baseNode.setCenter(loadView(viewFile).getNode());
    }

    /**
     * Set view for window from a node.
     * @param viewNode Node to be displayed.
     */
    public void setView(Node viewNode) {
        baseNode.setCenter(viewNode);
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
    public <T> View<T> loadView(String viewFile, T controller) {
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

    /**
     * Function to show and kill drawer
     * @param show true if the drawer should show, false if drawer should be killed
     */
    public void showMenuBar(boolean show) {
        if(show) {
            this.baseNode.setLeft(loadView(DRAWER_PATH).getNode());
        }
        else {
            this.baseNode.setLeft(null);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void addActiveStage(Stage activeStage) {
        activeStages.add(activeStage);
    }

    public void removeActiveStage(Stage activeStage) {
        activeStages.remove(activeStage);
    }

    public Stage getActiveStage() {
        return activeStages.peek();
    }

    public Employee getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Employee userAccount) {
        this.userAccount = userAccount;
    }

    public void setDrawerContentController(DrawerContentController drawerContentController) {
        this.drawerContentController = drawerContentController;
    }
}
