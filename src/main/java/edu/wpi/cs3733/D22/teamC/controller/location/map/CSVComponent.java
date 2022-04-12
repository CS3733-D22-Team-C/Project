package edu.wpi.cs3733.D22.teamC.controller.location.map;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.fileio.csv.LocationCSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class CSVComponent {


    @FXML
    private TextField csvName;

    File savedFile;

    @FXML
    void chooseCSV(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export CSV File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.instance.getStage());
        csvName.setText(file.getName());
        savedFile = file;

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

}
