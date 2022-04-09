package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.LocationsViewController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseTableViewController2<T extends Object> implements Initializable {

    @FXML private VBox insertBox;
    InserTableViewController insertController;

    @FXML private TextField row5;

    private Location currentLocation;



    @FXML private JFXTreeTableView table;
    private LocationTableDisplay tableDisplay;

    //TODO abstract that this (same as above setUp)
    private LocationsViewController viewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //currentLocation = new Location();
        System.out.println("hello1");

        setUp("view/Table/Locations/table_insert.fxml");
        rowInteraction();
    }

    //TODO make the controller abstrsct (more than just location)
    public void setUp(String insertPath){
        //Will be a parameter
        //LocationsViewController viewController
        //insertController = new InserTableViewController();
        insertController = new InserTableViewController();
        setInsert(insertPath);
        //step 1 get path of the specified insert, put it into place
        //this.viewController  =  LocationsViewController();
        insertController.setup(this);

        //TODO add setUP function to viewController
        //this.viewController.setUp(table);

        //TODO to be abstracred l8ter
        resetTableView();




    }

    public void setInsert(String path){
        App.View<InserTableViewController> view = App.instance.loadView(path);
        insertController = view.getController();
        insertBox.getChildren().add(view.getNode());
    }

    //TODO
    public void rowInteraction(){

        //TODO figure out how to interact with certina row
        table.setRowFactory(tv -> {
            TreeTableRow<LocationTableDisplay.LocationTableEntry> row = new TreeTableRow<LocationTableDisplay.LocationTableEntry>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    currentLocation = row.getItem().object;
                    insertController.getRowInfo(currentLocation);
//                    if (event.getClickCount() == 2) {
//                        System.out.println(row.getText());
//                    }
                }
            });
            return row ;
        });
    }

    public void backButton(){ App.instance.setView(App.HOME_PATH);}

    public void removeButton(){
        if (tableDisplay != null)
        {
            tableDisplay.removeObject(currentLocation);
            LocationDAO locationDAO = new LocationDAOImpl();
            locationDAO.deleteLocation(currentLocation);
            insertController.resetValues();
            tableDisplay = null;
        }
    }

    private void resetTableView(){
        tableDisplay = new LocationTableDisplay(table);

        // Query Database
        LocationDAO locationsDAO = new LocationDAOImpl();
        List<Location> locations = locationsDAO.getAllLocations();

        for(Location location2 : locations){
            tableDisplay.addObject(location2);
        }
    }

    public void updateEntry(Location location){

    }

    public void upDateTable(){
        table = null;

    }







}
