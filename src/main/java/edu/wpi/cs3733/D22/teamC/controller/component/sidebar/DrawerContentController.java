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
    public MFXButton aboutButton;

    @FXML
    public MFXButton dashboardButton;

    @FXML
    public MFXButton exitButton;

    @FXML
    public MFXButton logOutButton;

    @FXML
    public MFXButton databaseButton;

    @FXML
    public MFXButton serviceRequestsButton;

    @FXML
    public MFXButton viewProfileButton;

    @FXML
    public VBox miniView;

    @FXML
    public SVGPath expandedView;

    @FXML
    public MFXButton creditButton;
    //#endregion

    // list to store all buttons
    MFXButton[] allButtons;

    protected void setParentController(SidebarMenuController parentController) {
        this.sidebarMenuController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allButtons = new MFXButton[]{dashboardButton, exitButton, logOutButton, databaseButton, viewProfileButton, serviceRequestsButton, creditButton, aboutButton};

        // Hide the database button if the user is not an admin
        databaseButton.setVisible(App.instance.getUserAccount().getAdmin());

        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }

        // Set selected button to initial page
        selectedButton(dashboardButton);
        App.instance.setDrawerContentController(this);
    }

    public void selectedButton(MFXButton selectedButton) {
        for (MFXButton button : allButtons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("button");
        }
        selectedButton.getStyleClass().add("active-button");
    }

    //#region Button Events
    @FXML
    void dashboardButtonPress(ActionEvent event) {
        App.instance.setView(App.DASHBOARD_PATH);
        selectedButton(dashboardButton);
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
    void serviceRequestButtonPress(ActionEvent event) {
        App.instance.setView(App.SERVICE_REQUEST_DASHBOARD);
        selectedButton(serviceRequestsButton);
    }

    @FXML
    void databaseButtonPress(ActionEvent event) {
        App.instance.setView(App.DATABASE_PAGE_PATH);
        selectedButton(databaseButton);
    }

    @FXML
    void userProfileButtonPress(ActionEvent event) {
        App.instance.setView(App.USER_PROFILE);
        selectedButton(viewProfileButton);
    }

    @FXML
    void creditButtonPress(ActionEvent event) {
        App.instance.setView(App.CREDIT_PAGE);
        selectedButton(creditButton);
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
        selectedButton(aboutButton);
    }

    public MFXButton getDashboardButton() {
        return dashboardButton;
    }
}

