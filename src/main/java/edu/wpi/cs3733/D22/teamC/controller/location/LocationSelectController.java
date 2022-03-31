package edu.wpi.cs3733.D22.teamC.controller.location;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.LocationTable;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LocationSelectController implements Initializable {

    @FXML private JFXTreeTableView<LocationTable> table;

    ObservableList<LocationTable> LOCList = FXCollections.observableArrayList();
    final TreeItem<LocationTable> root = new RecursiveTreeItem<LocationTable>(LOCList, RecursiveTreeObject::getChildren);

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Constrain column sizes to the size of the table

        //Columns for table
        LocationTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        // Query Database

        List<Location> locations = new LocationDAOImpl().getAllLocations();

        for(Location location:locations){
            LocationTable loc = new LocationTable(location);
            LOCList.add(loc);
        }
    }
}
