package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import javafx.fxml.FXML;

import java.util.List;

public class ViewMapControlsController {
    // Fields
    @FXML JFXComboBox<String> floorField;

    // References
    private BaseMapViewController parentController;

    // Variables
    private List<Floor> floors;

    public void setup(BaseMapViewController baseMapViewController, Floor floor) {
        setParentController(baseMapViewController);

        FloorDAO floorDAO = new FloorDAOImpl();
        floors = floorDAO.getAllFloors();

        if (floors != null) {
            for (Floor i : floors) {
                floorField.getItems().add(i.getShortName());
            }
        }


        if (floor != null) {
            floorField.setValue(floor.getShortName());
        } else {
            floorField.setValue(floors.get(0).getShortName());
            onFloorChanged();
        }
    }

    private Floor getFloorByShortName(String value) {
        // TODO: Fix hardcoded mess !!!
        Floor floor = null;
        for (Floor i : floors) {
            if (i.getShortName() == value) {
                floor = i;
                break;
            }
        }

        try {
            if (floor == null) {
                throw new Exception();
            }
            return floor;
        } catch (Exception e) {
            System.out.println("Floor not set.");
            e.printStackTrace();
        }

        return null;
    }

    @FXML
    public void onEnterEditModeButtonPress() {
        parentController.swapToEditMode();
    }

    @FXML
    public void onFloorChanged() {
        parentController.setFloor((getFloorByShortName(floorField.getValue())));
    }

    public void setParentController(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }
}
