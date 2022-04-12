package edu.wpi.cs3733.D22.teamC.controller.location.map;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class CSVComponent {

    @FXML private TextField exportText;
    @FXML private TextField importText;

    File savedFile;
    private BaseMapViewController parentController;

    public void setup(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }

    @FXML
    void chooseExportCSV(ActionEvent event) {
        chooseCSV(exportText);
    }

    @FXML
    void chooseImportCSV(ActionEvent event) {
        chooseCSV(importText);
    }

    @FXML
    void exportFiles(ActionEvent event) {

//        if (file != null) {
//            // Export CSV Data - Location
//            LocationCSVWriter csvWriter = new LocationCSVWriter();
//            LocationDAO locationDAO = new LocationDAO();
//            List<Location> locations = locationDAO.getAll();
//            if (locations != null) {
//                System.out.println(csvWriter.writeFile(file, locations));
//            }
//        }
    }

    @FXML
    void importFiles(ActionEvent event) {

        //        Import stuff from editMapControls
//        if (file != null) {
//        parentController.resetLocationChanges();
//
//        LocationDAO locationDAO = new LocationDAO();
//        locationDAO.getAll().forEach(parentController::deleteLocation);
//
//        // Load CSV Data - Location
//        LocationCSVReader csvReader = new LocationCSVReader();
//        List<Location> locations = csvReader.readFile(file);
//        if (locations != null) {
//            locations.forEach(parentController::addLocation);
//        }
//
//        parentController.setCurrentFloor(parentController.getCurrentFloor());
    }


    void chooseCSV(TextField csvName) {
        // Create a file chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Export CSV File");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        //directoryChooser.getExtensionFilters().add(extFilter);
        //directoryChooser.getInitialDirectory();
        File file = directoryChooser.showDialog(App.instance.getStage());
        csvName.setText(":\\ ...\\" + file.getName());
        savedFile = file;

        //Path to the files
        for (int i = 0; i < savedFile.list().length; i++) {
            System.out.println(savedFile.getPath() + "\\" + savedFile.list()[i]);
        }




    }


}
