package edu.wpi.cs3733.D22.teamC.controller.location.map.controls;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class EditMapControlsController extends MapControlsController {
    @FXML private MFXButton exitButton;
    @FXML private MFXButton importButton;
    @FXML private MFXButton exportButton;
    @FXML private MFXButton saveButton;

    public void setSaveStatus(boolean savable) {
        saveButton.setDisable(!savable);
    }

    //#region FXML Events
        @FXML
        void onSaveButtonPressed(ActionEvent event) {
            parentController.saveLocationChanges();
            setSaveStatus(false);
        }

        @FXML
        void onExportButtonPressed(ActionEvent event) {
            // Create a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export CSV File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(App.instance.getStage());

            if (file != null) {
                // Export CSV Data - Location
                LocationCSVWriter csvWriter = new LocationCSVWriter();
                LocationDAO locationDAO = new LocationDAO();
                List<Location> locations = locationDAO.getAll();
                if (locations != null) {
                    System.out.println(csvWriter.writeFile(file, locations));
                }
            }
        }

        @FXML
        void onImportButtonPressed(ActionEvent event) {
            // Create a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import CSV File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(App.instance.getStage());

            if (file != null) {
                parentController.resetLocationChanges();

                LocationDAO locationDAO = new LocationDAO();
                locationDAO.getAll().forEach(parentController::deleteLocation);

                // Load CSV Data - Location
                LocationCSVReader csvReader = new LocationCSVReader();
                List<Location> locations = csvReader.readFile(file);
                if (locations != null) {
                    locations.forEach(parentController::addLocation);
                }

                parentController.setCurrentFloor(parentController.getCurrentFloor());
            }
        }

        @FXML
        void onExitButtonPressed(ActionEvent event) {
            parentController.resetLocationChanges();
            parentController.swapToViewMode();
        }
    //#endregion
}
