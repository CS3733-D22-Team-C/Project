package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InserTableViewController implements Initializable {

    @FXML private JFXButton confirm;

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

    BaseTableViewController2 parentController;
    Location currentLocation;



    public void initialize(URL location, ResourceBundle resources) {
        label1.setText("Long Name:");
        label2.setText("Short Name:");
        label3.setText("NodeID:");
        label4.setText("Node Type:");
        label5.setText("Building");
        label6.setText("Floor");
        label7.setText("X Coordinate:");
        label8.setText("Y Coordinate:");

    }

    public String getPath(){

        //TODO write funciton to grab path of insert
         return "view/Table/Locations/table_insert.fxml";

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


    public void setup(BaseTableViewController2 parentController)
    {
        this.parentController = parentController;
    }

    public void getRowInfo(Location location)
    {
        this.currentLocation = location;
        setFields(location.getLongName(), location.getShortName(), Integer.toString(location.getNodeID()), location.getNodeType().toString(), location.getBuilding(), Integer.toString(location.getFloor()), Integer.toString(location.getX()), Integer.toString(location.getY()));
    }

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
    }

    @FXML
    void clickConfirm(ActionEvent event) {
        currentLocation.setLongName(field1.getText());
        currentLocation.setShortName(field2.getText());
        currentLocation.setNodeID(Integer.valueOf(field3.getText()));
        currentLocation.setNodeType(Location.NodeType.valueOf(field4.getText()));
        currentLocation.setBuilding(field5.getText());
        currentLocation.setFloor(Integer.valueOf(field6.getText()));
        currentLocation.setX(Integer.valueOf(field7.getText()));
        currentLocation.setY(Integer.valueOf(field8.getText()));

        LocationDAO locationDAO = new LocationDAOImpl();
        locationDAO.updateLocation(currentLocation);

        App.instance.setView("view/Table/base_view2.fxml");



    }

    private boolean fieldsFilled(){
        return !(field1.equals("") || field2.equals("") || field3.equals("") ||
                field4.equals("") || field5.equals("") || field6.equals("") ||
                field7.equals("") || field8.equals(""));
    }
    @FXML
    void keyPressed() {
        if(fieldsFilled())
            confirm.setDisable(false);
        else
            confirm.setDisable(true);
    }

}
