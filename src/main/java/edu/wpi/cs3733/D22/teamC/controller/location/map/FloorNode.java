package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class FloorNode {

    @FXML private MFXButton floorButton;
    @FXML private HBox group;
    @FXML private Text floorOrder;
    private Floor floor;

    private boolean isSelected;

//    FloorNode(String floorUUID, String floorName){
//        this.floorUUID = floorUUID;
//        this.floorButton.setText(floorName);
//    }

    public boolean getIsSelected(){
        return isSelected;
    }

    @FXML
    void setup(Floor floor){
        this.floor = floor;
        this.floorButton.setText(floor.getLongName());
        this.isSelected = false;
        this.floorOrder.setText(Integer.toString(floor.getOrder()));
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public HBox getGroup() {
        return group;
    }

    public void setGroup(HBox group) {
        this.group = group;
    }

    public void setName(String floorName){
        this.floorButton.setText(floorName);
    }

    public static FloorNode loadNewFloorNode() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/location/map/map_floor_node.fxml"));
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
