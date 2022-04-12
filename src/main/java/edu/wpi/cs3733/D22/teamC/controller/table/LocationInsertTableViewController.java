package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
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
    @FXML private JFXComboBox<String> field4;
    @FXML private TextField field5;
    @FXML private JFXComboBox<Floor> floorBox;
    @FXML private TextField field7;
    @FXML private TextField field8;

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;
    @FXML Label title;


    public void initialize(URL location, ResourceBundle resources) {
        label1.setText("Long Name:");
        label2.setText("Short Name:");
        label4.setText("Node Type:");
        label5.setText("Building");
        label6.setText("Floor");
        label7.setText("X Coordinate:");
        label8.setText("Y Coordinate:");
        title.setText("");

        field4.setEditable(false);
        //field4.getItems().setAll(Location.NodeType.values().toString());
        for (Location.NodeType nodeType : Location.NodeType.values()) {
            field4.getItems().add(nodeType.toString());
        }

        ComponentWrapper.InitializeComboBox(floorBox, Floor::getShortName);

        floorBox.getItems().setAll(new FloorDAO().getAll());
//        floorBox.setValue(null);

        fieldsEditable(false);
    }

    public void setup(BaseTableViewController parentController)
    {
        super.setup(parentController);
    }


    public void getRowInfo(Location location)
    {
        super.getRowInfo(location);
        title.setText("Edit Location");
        setFields(location.getLongName(), location.getShortName(), location.getNodeType().toString(), location.getBuilding(), new FloorDAO().getByID(location.getFloor()), Integer.toString(location.getX()), Integer.toString(location.getY()));
    }

    public void setFields(String r1,String r2, String r4, String r5,Floor r6, String r7, String r8) {
        field1.setText(r1);
        field2.setText(r2);
        field4.setValue(r4);
        field5.setText(r5);
        floorBox.setValue(r6);
        field7.setText(r7);
        field8.setText(r8);
    }

    @Override
    public void resetValues()
    {
        field1.setText("");
        field2.setText("");
        field4.setValue(null);
        field5.setText("");
//        floorBox.setValue(null);
        field7.setText("");
        field8.setText("");
        title.setText("");
    }

    @FXML
    void clickConfirm(ActionEvent event) {

        if (!addMode){

            currentRow.setLongName(field1.getText());
            currentRow.setShortName(field2.getText());
            currentRow.setNodeType(Location.NodeType.valueOf(field4.getValue()));
            currentRow.setBuilding(field5.getText());
            currentRow.setFloor(floorBox.getValue().getFloorID());
            currentRow.setX(Integer.valueOf(field7.getText()));
            currentRow.setY(Integer.valueOf(field8.getText()));

            LocationDAO locationDAO = new LocationDAO();
            locationDAO.update(currentRow);

            parentController.tableDisplay.updateObject(currentRow);
            addMode = false;
            resetValues();
            fieldsEditable(false);

        } else {

            Location newLocal = new Location();

            newLocal.setLongName(field1.getText());
            newLocal.setShortName(field2.getText());
            newLocal.setNodeType(Location.NodeType.valueOf(field4.getValue()));
            newLocal.setBuilding(field5.getText());
            newLocal.setFloor(floorBox.getValue().getFloorID());
            newLocal.setX(Integer.valueOf(field7.getText()));
            newLocal.setY(Integer.valueOf(field8.getText()));

            LocationDAO locationDAO = new LocationDAO();
            locationDAO.insert(newLocal);

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
        return field1.getText().equals("") || field2.getText().equals("") || field4.getValue() == null
                || field5.getText().equals("") || floorBox.getValue() == null ||
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
        field4.setEditable(edit);
        field4.setDisable(!edit);
        field5.setEditable(edit);
        floorBox.setEditable(edit);
        field7.setEditable(edit);
        field8.setEditable(edit);
    }

    public TableDisplay<Location> createTableDisplay(JFXTreeTableView table){

        TableDisplay<Location> tempTableDisplay = new LocationTableDisplay(table);
        // Query Database
        LocationDAO locationsDAO = new LocationDAO();
        List<Location> locations = locationsDAO.getAll();

        for(Location location2 : locations){
            tempTableDisplay.addObject(location2);
        }
        return tempTableDisplay;
    }
    public void deleteValue(Location currentRow) {
        LocationDAO locationDAO = new LocationDAO();
        locationDAO.delete(currentRow);
    }
}
