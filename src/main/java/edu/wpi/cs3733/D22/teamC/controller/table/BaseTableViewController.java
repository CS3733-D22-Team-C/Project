package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseTableViewController {

    @FXML private JFXButton add;
    @FXML private JFXButton remove;
    @FXML private JFXButton back;
    @FXML private JFXTreeTableView table;
    @FXML private HBox insertBox;

    InserTableViewController insertController; //TODO what object type is this?

    TableDisplay tableDisplay; //TODO what object type is this?

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        setRowInteraction();
    }

//    public void setup(String insertPath)
//    {
//        //code to set table
//        setInsert(insertPath);
//        //TODO error because table display is just an Object
//        tableDisplay  = insertController.setupTable(table);
//
//    }
//
//    public void setInsert(String insertPath) {
//
//        App.View view = App.instance.loadView(insertPath);
//        insertBox.getChildren().add(view.getNode());
//        insertController = view.getController();
//    }

    //funciton for dealing with the row
    protected void setRowInteraction()
    {

    }

    //function for when the add button is clicked
    @FXML
    void onAddButton(ActionEvent event){}
    //function for when the remove button is clicked
    @FXML
    void onRemoveButton(ActionEvent event){}

    @FXML
    void onBackButton(ActionEvent event){}
}
