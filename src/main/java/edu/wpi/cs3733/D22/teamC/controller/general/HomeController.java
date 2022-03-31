package edu.wpi.cs3733.D22.teamC.controller.general;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    public void onClickExit(ActionEvent actionEvent) {
        App.instance.getStage().close();
    }

    @FXML
    public void onClickServiceRequests(ActionEvent actionEvent) {
        App.instance.setView(App.instance.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    public void onClickMaps(ActionEvent actionEvent) {
        App.instance.setView(App.instance.VIEW_LOCATIONS_PATH);
    }
}
