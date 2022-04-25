package edu.wpi.cs3733.D22.teamC.controller.component.sidebar;

import edu.wpi.cs3733.D22.teamC.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

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
    private MFXButton myTasksButton;

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

    // list to store all buttons
    MFXButton[] allButtons;

    protected void setParentController(SidebarMenuController parentController) {
        this.sidebarMenuController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allButtons = new MFXButton[]{dashboardButton, exitButton, logOutButton, databaseButton, viewProfileButton, myTasksButton, serviceRequestsButton};
        disableButton(dashboardButton);

        // Hide the database button if the user is not an admin
        databaseButton.setVisible(App.instance.getUserAccount().getAdmin());

        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }
    }

    private void disableButton(MFXButton disableButton) {
        for (MFXButton button : allButtons) {
            button.setDisable(false);
        }
        disableButton.setDisable(true);
    }

    //#region Button Events
    @FXML
    void dashboardButtonPress(ActionEvent event) {
        App.instance.setView(App.DASHBOARD_PATH);
        disableButton(dashboardButton);
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
    void myTasksButtonPress(ActionEvent event) {
        App.instance.setView(App.MY_TASKS_PATH);
        disableButton(myTasksButton);
    }

    @FXML
    void serviceRequestButtonPress(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
        disableButton(serviceRequestsButton);
    }

    @FXML
    void databaseButtonPress(ActionEvent event) {
        App.instance.setView(App.DATABASE_PAGE_PATH);
        disableButton(databaseButton);
    }

    @FXML
    void userProfileButtonPress(ActionEvent event) {
        App.instance.setView(App.USER_PROFILE);
        disableButton(viewProfileButton);
    }
    //#endregion

    public SVGPath getExpandedView() {
        return expandedView;
    }

    public VBox getMiniView() {
        return miniView;
    }
}

