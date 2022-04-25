package edu.wpi.cs3733.D22.teamC.controller.component.sidebar;

import edu.wpi.cs3733.D22.teamC.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class DrawerContentController implements Initializable {

    SidebarMenuController sidebarMenuController;

    //#region FXML
    @FXML
    private MFXButton dashboardButton;

    @FXML
    private MFXButton exitButton;

    @FXML
    private MFXButton logOutButton;

    @FXML
    private MFXButton mapsButton;

    @FXML
    private MFXButton databaseButton;

    @FXML
    private MFXButton serviceRequestsButton;

    @FXML
    private MFXButton viewProfileButton;

    @FXML
    private VBox miniView;

    @FXML
    private SVGPath expandedView;
    //#endregion

    // use a list to store all buttons
    MFXButton[] allButtons;

    protected void setParentController(SidebarMenuController parentController) {
        this.sidebarMenuController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allButtons = new MFXButton[]{dashboardButton, exitButton, logOutButton, databaseButton, viewProfileButton, mapsButton, serviceRequestsButton};
        //initializeSVG();

        // Hide the database button if the user is not an admin
//        databaseButton.setVisible(App.instance.getUserAccount().getAdmin());

        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }
    }

    //#region Button Events
    @FXML
    void dashboardButtonPress(ActionEvent event) {
        App.instance.setView(App.DASHBOARD_PATH);
    }

    @FXML
    void exitButtonPress(ActionEvent event) {
        App.instance.getStage().close();
    }

    @FXML
    void logOutButtonPress(ActionEvent event) {
        // TODO: Logout functionality, path to login page
        App.instance.setView(App.LOGIN_PATH);
        App.instance.showMenuBar(false);
    }

    @FXML
    void mapButtonPress(ActionEvent event) {
        App.instance.setView(App.MAP_DASHBOARD_PATH);
    }

    @FXML
    void serviceRequestButtonPress(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    void databaseButtonPress(ActionEvent event) {
        App.instance.setView(App.DATABASE_PAGE_PATH);
    }

    @FXML
    void userProfileButtonPress(ActionEvent event) {
        App.instance.setView(App.USER_PROFILE);
    }
    //#endregion

    public SVGPath getExpandedView() {
        return expandedView;
    }

    public VBox getMiniView() {
        return miniView;
    }

    @FXML
    public void aboutButtonPress(ActionEvent actionEvent) {
        App.instance.setView(App.ABOUT_PAGE);
    }
}

