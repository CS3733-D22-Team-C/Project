package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    //#region
    @FXML
    public void onEnterEditModeButtonPress() {
        parentController.swapToEditMode();
    }

    @FXML
    public void onFloorChanged() {
        parentController.setFloor((getFloorByShortName(floorField.getValue())));
    }

    @FXML
    public void onImportCSVButtonPress() {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import CSV File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.instance.getStage());

        if (file != null) {
            DBManager.getInstance().initializeLocationTable(true);

            // Load CSV Data - Location
            LocationCSVReader csvReader = new LocationCSVReader();
            List<Location> locations = csvReader.readFile(file);
            if (locations != null) {
                LocationDAO locationDAO = new LocationDAOImpl();
                for (Location location : locations) {
                    locationDAO.insertLocation(location);
                }
            }

            parentController.setFloor(parentController.getFloor());
        }
    }

    @FXML
    public void onExportCSVButtonPress() {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export CSV File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(App.instance.getStage());

        if (file != null) {
            // Export CSV Data - Location
            LocationCSVWriter csvWriter = new LocationCSVWriter();
            LocationDAO locationDAO = new LocationDAOImpl();
            List<Location> locations = locationDAO.getAllLocations();
            if (locations != null) {
                System.out.println(csvWriter.writeFile(file, locations));
            }
        }
    }
    //#endregion

    public void setParentController(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }
}
