package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseTableViewController<T extends Object> implements Initializable {

    @FXML private VBox insertBox;
    InsertTableViewController insertController;

    @FXML private TextField row5;

    private T currentRow;

    @FXML private JFXTreeTableView table;
    TableDisplay<T> tableDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        setUp("view/Table/Locations/table_insert.fxml");
        rowInteraction();
    }

    //TODO make the controller abstrsct (more than just location)
    public void setUp(String insertPath){

        setInsert(insertPath);
        insertController.setup(this);

        //TODO add setUP function to viewController
        //this.viewController.setUp(table);

        //TODO to be abstracted l8ter
        resetTableView();
    }

    public void setInsert(String path){
        App.View<InsertTableViewController<T>> view = App.instance.loadView(path);
        insertController = view.getController();
        insertBox.getChildren().add(view.getNode());
    }

    //TODO
    public void rowInteraction(){

        //TODO figure out how to interact with certina row
        table.setRowFactory(tv -> {
            TreeTableRow<TableDisplay<T>.TableDisplayEntry> row = new TreeTableRow<TableDisplay<T>.TableDisplayEntry>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    currentRow = row.getItem().object;
                    insertController.getRowInfo(currentRow);
                }
            });
            return row ;
        });
    }

    public void backButton(){ App.instance.setView(App.HOME_PATH);}

    @FXML
    public void removeButton(){
        if (tableDisplay != null)
        {
            tableDisplay.removeObject(currentRow);
            insertController.deleteValue(currentRow);
            insertController.resetValues();
            //tableDisplay = null;
        }
    }

    void resetTableView() {
        tableDisplay = insertController.createTableDisplay(table);
    }

    @FXML
    public void addClicked(){
        insertController.addClicked();
    }

}
