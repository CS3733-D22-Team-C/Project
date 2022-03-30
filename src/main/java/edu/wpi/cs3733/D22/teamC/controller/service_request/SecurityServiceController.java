package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecurityServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.awt.event.InputMethodEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SecurityServiceController implements Initializable {

    private final String BASE_VIEW_PATH = "view/general/base-view.fxml";

    //Fields
    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField location;

    //Dropdowns

    @FXML private JFXComboBox<String> priority;
    @FXML private JFXComboBox<String> status;
    @FXML private JFXComboBox<String> securityType;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> Atable;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //For equipment type drop down
        securityType.getItems().add("security type 1");
        securityType.getItems().add("security type 2");
        securityType.getItems().add("security type 3");
        securityType.getItems().add("security type 4");

        //For priority dropdown
        priority.getItems().add("Low");
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //For status dropdown
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");

        //Colums for table
        TreeTableColumn ID = new TreeTableColumn("ID");
        ID.setPrefWidth(80);
        TreeTableColumn Asignee = new TreeTableColumn("Asignee");
        Asignee.setPrefWidth(80);
        TreeTableColumn Status = new TreeTableColumn("Status");
        Status.setPrefWidth(80);
        TreeTableColumn Location = new TreeTableColumn("Location");
        Location.setPrefWidth(80);
        TreeTableColumn Type = new TreeTableColumn("Type");
        Type.setPrefWidth(80);
        TreeTableColumn TypeNum = new TreeTableColumn("Type #");
        TypeNum.setPrefWidth(80);

        Atable.getColumns().addAll(ID, Asignee, Status, Location, Type, TypeNum);

    }
    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(BASE_VIEW_PATH);
    }

    @FXML
    void clickReset(ActionEvent event){clearFields();}

    @FXML
    void clickSubmit(ActionEvent event)
    {
        //only create a request if none of the necessary frames are empty
        if (!emptyField())
        {
            SecurityServiceRequest securityRequest = new SecurityServiceRequest();
            securityRequest.setSecurityType(securityType.getValue());
            //TODO add stuff for other atributes
            //once the request has been made, the fields clear
            clearFields();
        } else
        {
            System.out.println("no object made");
            //TODO add prompt to ask user fill in all fields
        }
    }

    //function to clear all the input fields
    void clearFields()
    {
        //clear text fields
        System.out.println("testing testing\n");
        assigneeID.clear();
        description.clear();
        location.clear();

        //clear the drop-downs :D
        securityType.setValue(null);
        priority.setValue(null);
        status.setValue(null);
    }


    //returns true if a necessary field is empty
    boolean emptyField()
    {
        return (assigneeID.getText().isEmpty() || location.getText().isEmpty() ||
                securityType.getValue().isEmpty() || priority.getValue().isEmpty() ||
                status.getValue().isEmpty());
    }




}