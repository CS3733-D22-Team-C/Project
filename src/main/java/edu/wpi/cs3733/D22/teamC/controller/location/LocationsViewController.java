package edu.wpi.cs3733.D22.teamC.controller.location;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LocationsViewController implements Initializable {

    @FXML
    private JFXTreeTableView table;

    private LocationTableDisplay tableDisplay;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Constrain column sizes to the size of the table

        //Columns for table
        tableDisplay = new LocationTableDisplay(table);

        // Query Database
        LocationDAO locationsDAO = new LocationDAOImpl();
        List<Location> locations = locationsDAO.getAllLocations();

        for(Location location : locations){
            tableDisplay.addObject(location);
        }
    }
}
