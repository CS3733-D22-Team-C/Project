package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InserTableViewController implements Initializable {

    @FXML private JFXButton submit;

    @FXML
    private TextField row1;

    @FXML
    private TextField row2;

    @FXML
    private TextField row3;
    @FXML
    private TextField row4;

    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    @FXML
    Label label4;



    public void initialize(URL location, ResourceBundle resources) {

        row1.setText("TEST TEST");

    }

    public String getPath(){

        //TODO write funciton to grab path of insert
         return "view/Table/Locations/table_insert.fxml";

    }

    public void setRows(String r1,String r2, String r3, String r4) {
        System.out.println("HERE");
        row1.setText(r1);
        row2.setText(r2);
        row3.setText(r3);
        row4.setText(r4);
    }


    public void setup()
    {
    }

    public void getRowInfo(Location location)
    {
        label1.setText("Location Name");
        label2.setText("X Value");
        label3.setText("Y Value");
        label4.setText("Buiilding");

        setRows(location.getLongName(), Integer.toString(location.getX()), Integer.toString(location.getY()), location.getBuilding());
    }

    public void resetValues()
    {
        row1.setText("");
        row2.setText("");
        row3.setText("");
        row4.setText("");
    }


}
