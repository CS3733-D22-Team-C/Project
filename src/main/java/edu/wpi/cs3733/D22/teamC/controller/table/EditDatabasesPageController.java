package edu.wpi.cs3733.D22.teamC.controller.table;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.CSVComponent;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDatabasesPageController implements Initializable {

    @FXML private VBox insertVbox;

    CSVComponent insertController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInsert();
    }
    @FXML
    void clickEmployee(ActionEvent event) {

    }

    @FXML
    void clickLocations(ActionEvent event) {
    }

    @FXML
    void clickMedicalEquipment(ActionEvent event) {

    }

    public void setInsert() {
        String viewFile = "view/location/map/csv_component.fxml";

        App.View<CSVComponent> view = App.instance.loadView(viewFile);
        insertVbox.getChildren().add(view.getNode());
        insertController = view.getController();
    }

}
