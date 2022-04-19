package edu.wpi.cs3733.D22.teamC.controller.component.sidebar;

import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.D22.teamC.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarMenuController implements Initializable {

    // Child Drawers
    private DrawerContentController childController;

    @FXML JFXDrawer sidebarDrawer;

    @FXML MFXButton lockButton;

    @FXML SVGPath lockArrow;

    // 0 is unlocked, 1 is locked
    boolean drawerLock = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Move the button to the content side
        sidebarDrawer.setContent(lockButton);

        // Load the drawer
        App.View view = App.instance.loadView(App.DRAWER_CONTENT_PATH);

        // Set the drawer to the side pane (part of the drawer that moves)
        sidebarDrawer.setSidePane(view.getNode());
        DrawerContentController drawerContentController = (DrawerContentController) view.getController();
        drawerContentController.setParentController(this);
        childController = (DrawerContentController) view.getController();

        // Open the drawer
        sidebarDrawer.open();

        // Make the lock arrow look locked
        lockArrow.setRotate(-90);
    }

    @FXML
    public void onLockButtonPressed() {
        drawerLock = !drawerLock;

        if(drawerLock) {
            lockArrow.setRotate(-90);
        }
        else {
            lockArrow.setRotate(90);
        }
    }

    @FXML
    public void drawerOnMouseEnter() {
        if(!drawerLock) {
            fadeTransition(childController.getExpandedView(), .25, childController.getMiniView(), .5, false);
            sidebarDrawer.open();
            for (MFXButton button : childController.allButtons) {
                button.setContentDisplay(ContentDisplay.LEFT);
                button.setAlignment(Pos.CENTER_LEFT);
            }
        }
    }

    @FXML
    public void drawerOnMouseExit() {
        if(!drawerLock) {
            fadeTransition(childController.getMiniView(), .8, childController.getExpandedView(), .25, true);
            sidebarDrawer.close();
        }
    }

    @FXML
    private void drawerOpened() {
        for (MFXButton button : childController.allButtons) {
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setAlignment(Pos.CENTER_LEFT);
        }
        childController.getExpandedView().setVisible(true);
        childController.getMiniView().setVisible(false);
        }

    @FXML
    private void drawerClosed() {
        for (MFXButton button : childController.allButtons) {
            button.setAlignment(Pos.CENTER);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
        childController.getExpandedView().setVisible(false);
        childController.getMiniView().setVisible(true);
    }

    /**
     * Function to control the animation to fade in and fade out a node
     * @param fadeInNode The node you want to fade in
     * @param fadeInTime The amount of time the node takes to fade in seconds
     * @param fadeOutNode The node you want to fade out
     * @param fadeOutTime The amount of time the node takes to fade out in seconds
     * @param parallel If we run these animations in parallel or not
     */
    private void fadeTransition(Node fadeInNode, double fadeInTime, Node fadeOutNode, double fadeOutTime, boolean parallel) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeOutTime), fadeOutNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(fadeInTime), fadeInNode);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        if(parallel) {
            ParallelTransition parallelTransition = new ParallelTransition(fadeOut, fadeIn);
            parallelTransition.play();
        }
        else {
            SequentialTransition sequentialTransition = new SequentialTransition(fadeOut, fadeIn);
            sequentialTransition.play();
        }
    }
}
