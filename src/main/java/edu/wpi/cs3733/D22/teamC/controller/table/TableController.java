package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableController {

    @FXML private JFXButton add;
    @FXML private JFXButton remove;
    @FXML private JFXButton back;
    @FXML private JFXTreeTableView table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {}

    //function for when the add button is clicked
    @FXML
    void onAddButton(ActionEvent event){}
    //function for when the remove button is clicked
    @FXML
    void onRemoveButton(ActionEvent event){}

    @FXML
    void onBackButton(ActionEvent event){}




}
