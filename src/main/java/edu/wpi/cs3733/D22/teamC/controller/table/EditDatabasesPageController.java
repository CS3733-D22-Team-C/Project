package edu.wpi.cs3733.D22.teamC.controller.table;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.CSVComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDatabasesPageController implements Initializable {

    @FXML private VBox insertVbox;

    CSVComponent insertController;

    final static String TABLE_SKELETON = "view/table/base_view.fxml";
    final static String TABLE_EMPLOYEE = "view/table/employee/table_insert.fxml";
    final static String TABLE_LOCATION = "view/table/locations/table_insert.fxml";
    final static String TABLE_MEDICAL_EQUIPMENT = "view/table/medical_equipment/table_insert.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInsert();
    }
    @FXML
    void clickEmployee(ActionEvent event) {
        App.instance.setSkeletonView(TABLE_SKELETON, TABLE_EMPLOYEE);

    }

    @FXML
    void clickLocations(ActionEvent event) {
        App.instance.setSkeletonView(TABLE_SKELETON, TABLE_LOCATION);
    }

    @FXML
    void clickMedicalEquipment(ActionEvent event) {
        App.instance.setSkeletonView(TABLE_SKELETON, TABLE_MEDICAL_EQUIPMENT);

    }

    public void setInsert() {
        String viewFile = "view/location/map/controls/csv_page.fxml";

        App.View<CSVComponent> view = App.instance.loadView(viewFile);
        insertVbox.getChildren().add(view.getNode());
        insertController = view.getController();
    }

}
