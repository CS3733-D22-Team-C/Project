package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.SkeletonController;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseTableViewController<T extends Object> implements Initializable, SkeletonController {
    // FXML
    @FXML private VBox insertBox;
    @FXML private JFXTreeTableView table;

    // Variables
    TableDisplay<T> tableDisplay;
    T currentObj;

    // References
    InsertTableViewController<T> insertController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowInteraction();
    }

    public void setUp(String insertPath){
        setInsert(insertPath);

        insertController.setup(this);

        tableDisplay = insertController.createTableDisplay(table);
    }

    public void setInsert(String path){
        App.View<InsertTableViewController<T>> view = App.instance.loadView(path);
        insertController = view.getController();
        insertBox.getChildren().add(view.getNode());
    }


    public void rowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<TableDisplay<T>.TableDisplayEntry> row = new TreeTableRow<TableDisplay<T>.TableDisplayEntry>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (row.getItem().object == currentObj) {
                        setCurrentObj(null);
                        table.getSelectionModel().clearSelection();
                    } else {
                        setCurrentObj(row.getItem().object);
                    }
                }
            });
            return row;
        });
    }

    public void setCurrentObj(T object) {
        currentObj = object;
        insertController.setFields(object);
    }

    //#region FXML Events
        @FXML
        public void onRemoveButtonClicked() {
            if (currentObj != null) {
                // Delete from DB
                insertController.deleteObject();

                // Delete from Table
                tableDisplay.removeObject(currentObj);

                // Delete from Insert
                insertController.setFields(null);
            }
        }

        @FXML
        public void onAddButtonClicked(){
            currentObj = null;
            insertController.setFields(null);
            table.getSelectionModel().clearSelection();
        }

        @FXML
        public void onBackButtonClicked() {
            App.instance.setView(App.LOGIN_PATH);
        }
    //#endregion
}
