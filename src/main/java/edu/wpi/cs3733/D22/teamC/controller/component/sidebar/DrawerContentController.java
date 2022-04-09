package edu.wpi.cs3733.D22.teamC.controller.component.sidebar;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private MFXButton mapsButton;

    @FXML
    private MFXButton myTasksButton;

    @FXML
    private MFXButton serviceRequestsButton;

    @FXML
    private MFXButton viewProfileButton;
    //#endregion

    // use a list to store all buttons
    MFXButton[] allButtons;
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
     *
     */
    @FXML
    protected void contentPaneOnMouseEntered() {
        for (MFXButton button : allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }
    }

    /**
     *
     */
    @FXML
    protected void contentPaneOnMouseExited() {
        for (MFXButton button : allButtons) {
            // Make it so the button only displays the graphic
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            // Set alignment of text to the center of the button
            button.setAlignment(Pos.CENTER);
        }
    }

    protected void setParentController(SidebarMenuController parentController) {
        this.sidebarMenuController = parentController;
    }
}
