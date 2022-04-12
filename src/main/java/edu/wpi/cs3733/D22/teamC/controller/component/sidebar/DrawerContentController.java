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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private MFXButton myTasksButton;

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
        allButtons = new MFXButton[]{dashboardButton, exitButton, logOutButton, myTasksButton, viewProfileButton, mapsButton, serviceRequestsButton};
        initializeSVG();
    }

    /**
     * Initialize all drawer buttons to their respective SVGs
     */
    // TODO: Figure out a better way to store SVGs without needing to put the entire path, I assume we just use CSS
    // TODO: If possible I want to find a way to abstract this all so all buttons can be changed at once
    protected void initializeSVG() {
        // For all buttons we use setContentDisplay to hide the text, keeping only the SVG for the mini-drawer
        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    /**
     * When the mouse enters the drawer, start an animation then align the content within the button
     */
    @FXML
    protected void contentPaneOnMouseEntered() {
        fadeTransition(expandedView, 1, miniView, .5);
        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }
        expandedView.setOpacity(1);
        miniView.setOpacity(0);
    }

    /**
     * When the mouse enters the drawer, start an animation then align the content within the button
     */
    @FXML
    protected void contentPaneOnMouseExited() throws InterruptedException {
        fadeTransition(miniView, .8, expandedView, .25);
        for (MFXButton button : allButtons) {
            // Make it so the button only displays the graphic
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
        TimeUnit.SECONDS.sleep(1);
        for (MFXButton button : allButtons) {
            // Make it so the button only displays the graphic
            button.setAlignment(Pos.CENTER);
            }
        expandedView.setOpacity(0);
        miniView.setOpacity(1);
        }

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
        App.instance.setView("");
    }

    @FXML
    void mapButtonPress(ActionEvent event) {
        App.instance.setView(App.MAP_PATH);
    }

    @FXML
    void serviceRequestButtonPress(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    void taskButtonPress(ActionEvent event) {
        // TODO: Path to view tasks related to user
        App.instance.setView("");
    }

    @FXML
    void userProfileButtonPress(ActionEvent event) {
        // TODO: Path to user profile page
        App.instance.setView("");
    }

    // TODO: Bug when entering and exiting the drawer too fast, will overlay both mini and expanded onto the scene
    // Use semaphores/mutexes? 
    /**
     * Function to control the animation to fade in and fade out a node
     * @param fadeInNode The node you want to fade in
     * @param fadeInTime The amount of time the node takes to fade in seconds
     * @param fadeOutNode The node you want to fade out
     * @param fadeOutTime The amount of time the node takes to fade out in seconds
     */
    private void fadeTransition(Node fadeInNode, double fadeInTime, Node fadeOutNode, double fadeOutTime) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeOutTime), fadeOutNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(fadeInTime), fadeInNode);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeOut.play();
        fadeIn.play();
        fadeInNode.setVisible(true);
        fadeOutNode.setVisible(false);
    }
}

