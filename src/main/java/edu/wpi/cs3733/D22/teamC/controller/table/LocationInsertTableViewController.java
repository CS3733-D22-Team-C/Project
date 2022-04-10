package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LocationInsertTableViewController extends  InsertTableViewController<Location> implements Initializable {

    @FXML protected JFXButton confirm;

    @FXML private TextField field1;
    @FXML private TextField field2;
    @FXML private TextField field3;
    @FXML private TextField field4;
    @FXML private TextField field5;
    @FXML private TextField field6;
    @FXML private TextField field7;
    @FXML private TextField field8;

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;
    @FXML Label title;


    public void initialize(URL location, ResourceBundle resources) {
        label1.setText("Long Name:");
        label2.setText("Short Name:");
        label3.setText("NodeID:");
        label4.setText("Node Type:");
        label5.setText("Building");
        label6.setText("Floor");
        label7.setText("X Coordinate:");
        label8.setText("Y Coordinate:");
        title.setText("");

        fieldsEditable(false);


    }

    public String getPath(){

        //TODO write funciton to grab path of insert
        return "view/Table/Locations/table_insert.fxml";

    }

    public void setup(BaseTableViewController2 parentController)
    {
        super.setup(parentController);
    }


    public void getRowInfo(Location location)
    {
        super.getRowInfo(location);
        title.setText("Edit Location");
        setFields(location.getLongName(), location.getShortName(), Integer.toString(location.getNodeID()), location.getNodeType().toString(), location.getBuilding(), Integer.toString(location.getFloor()), Integer.toString(location.getX()), Integer.toString(location.getY()));
    }

    public void setFields(String r1,String r2, String r3, String r4, String r5,String r6, String r7, String r8) {
        System.out.println("HERE");
        field1.setText(r1);
        field2.setText(r2);
        field3.setText(r3);
        field4.setText(r4);
        field5.setText(r5);
        field6.setText(r6);
        field7.setText(r7);
        field8.setText(r8);
    }

    @Override
    public void resetValues()
    {
        field1.setText("");
        field2.setText("");
        field3.setText("");
        field4.setText("");
        field5.setText("");
        field6.setText("");
        field7.setText("");
        field8.setText("");
        title.setText("");
    }

    @FXML
    void clickConfirm(ActionEvent event) {

        if (!addMode){
            currentRow.setLongName(field1.getText());
            currentRow.setShortName(field2.getText());
            currentRow.setNodeID(Integer.valueOf(field3.getText()));
            currentRow.setNodeType(Location.NodeType.valueOf(field4.getText()));
            currentRow.setBuilding(field5.getText());
            currentRow.setFloor(Integer.valueOf(field6.getText()));
            currentRow.setX(Integer.valueOf(field7.getText()));
            currentRow.setY(Integer.valueOf(field8.getText()));

            LocationDAO locationDAO = new LocationDAOImpl();
            locationDAO.updateLocation(currentRow);

            parentController.tableDisplay.updateObject(currentRow);
            addMode = false;

        } else {

            Location newLocal = new Location();

            newLocal.setLongName(field1.getText());
            newLocal.setShortName(field2.getText());
            newLocal.setNodeID(Integer.valueOf(field3.getText()));
            newLocal.setNodeType(Location.NodeType.valueOf(field4.getText()));
            newLocal.setBuilding(field5.getText());
            newLocal.setFloor(Integer.valueOf(field6.getText()));
            newLocal.setX(Integer.valueOf(field7.getText()));
            newLocal.setY(Integer.valueOf(field8.getText()));

            LocationDAO locationDAO = new LocationDAOImpl();
            locationDAO.insertLocation(newLocal);

            parentController.tableDisplay.addObject(newLocal);
            resetValues();
            addMode = false;
            fieldsEditable(false);


        }
    }

    @FXML
    void keyPressed() {
        if(fieldsNotFilled())
            confirm.setDisable(true);
        else
            confirm.setDisable(false);
    }

    public boolean fieldsNotFilled(){
        return field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") ||
                field4.getText().equals("") || field5.getText().equals("") || field6.getText().equals("") ||
                field7.getText().equals("") || field8.getText().equals("");
    }

    void addClicked(){
        super.addClicked();
        title.setText("Add Location");
    }

    @Override
    public void fieldsEditable(boolean edit){
        field1.setEditable(edit);
        field2.setEditable(edit);
        field3.setEditable(edit);
        field4.setEditable(edit);
        field5.setEditable(edit);
        field6.setEditable(edit);
        field7.setEditable(edit);
        field8.setEditable(edit);
        System.out.println(field8.isEditable());
    }

    public TableDisplay<Location> createTableDisplay(JFXTreeTableView table){

        TableDisplay<Location> tempTableDisplay = new LocationTableDisplay(table);
        // Query Database
        LocationDAO locationsDAO = new LocationDAOImpl();
        List<Location> locations = locationsDAO.getAllLocations();

        for(Location location2 : locations){
            tempTableDisplay.addObject(location2);
        }
        return tempTableDisplay;
    }
    public void deleteValue(Location currentRow) {
        LocationDAO locationDAO = new LocationDAOImpl();
        locationDAO.deleteLocation(currentRow);
    }
}
