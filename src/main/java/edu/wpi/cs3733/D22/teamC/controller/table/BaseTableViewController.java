package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.SkeletonController;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseTableViewController<T extends IDEntity> implements Initializable, SkeletonController {
    // FXML
    @FXML private VBox insertBox;
    @FXML private JFXTreeTableView table;
    @FXML private JFXButton add;
    @FXML private JFXButton edit;
    @FXML private JFXButton remove;
    @FXML private StackPane stackPane;

    // Variables
    TableDisplay<T> tableDisplay;
    T currentObj;

    // References
    InsertTableViewController<T> insertController;

    final public static String DATABASE_PATH = "view/general/edit_databases_page.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowInteraction();
        edit.setDisable(true);
        remove.setDisable(true);
    }

    public void setUp(String insertPath){
        setInsert(insertPath);

        insertController.setup(this);

        tableDisplay = insertController.createTableDisplay(table);
    }

    public void setInsert(String path){
        App.View<InsertTableViewController<T>> view = App.instance.loadView(path);
        insertController = view.getController();
        stackPane.getChildren().add(view.getNode());
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
                event.consume();
            });
            return row;
        });
    }

    public void disableTable(boolean disable) {
        table.setDisable(disable);
    }

    public void setCurrentObj(T object) {
        currentObj = object;
        insertController.setFields(object);
        remove.setDisable(object == null);
        edit.setDisable(object == null);
    }

    //#region FXML Events
        @FXML
        public void onRemoveButtonClicked() {
            if (currentObj != null) {
                remove.setDisable(true);
                table.getSelectionModel().clearSelection();
                // Delete from DB
                insertController.deleteObject();

                // Delete from Table
                tableDisplay.removeObject(currentObj);

                // Delete from Insert
                insertController.setFields(null);
                table.getSelectionModel().clearSelection();
            }
        }

        @FXML
        public void onAddButtonClicked(){
            table.getSelectionModel().clearSelection();
            setCurrentObj(null);
            insertController.setFields(null);
            insertController.setVisible(true);
        }

        @FXML
        public void onEditButtonClicked() {
            insertController.setVisible(true);
        }

        @FXML
        public void onBackButtonClicked() {
            App.instance.setView(App.DATABASE_PAGE_PATH);
        }

        public void setRemoveDisable(boolean disabled) {remove.setDisable(disabled);}
    //#endregion
}
