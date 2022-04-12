package edu.wpi.cs3733.D22.teamC.controller.component.sidebar;

import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.D22.teamC.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarMenuController implements Initializable {

    // Child Drawers
    private DrawerContentController childController;

    @FXML
    JFXDrawer sidebarDrawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.View view = App.instance.loadView(App.DRAWER_CONTENT_PATH);
        sidebarDrawer.setSidePane(view.getNode());
        DrawerContentController drawerContentController = (DrawerContentController) view.getController();
        drawerContentController.setParentController(this);
        childController = (DrawerContentController) view.getController();
    }

    @FXML
    public void drawerOnMouseEnter() {
        sidebarDrawer.open();
    }

    @FXML
    public void drawerOnMouseExit() {
        sidebarDrawer.close();
    }


    @FXML
    void drawerClosed() {
        for (MFXButton button : childController.allButtons) {
            button.setAlignment(Pos.CENTER);
        }
    }
}
