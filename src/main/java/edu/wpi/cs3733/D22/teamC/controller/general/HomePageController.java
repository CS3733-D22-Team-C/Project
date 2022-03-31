package edu.wpi.cs3733.D22.teamC.controller.general;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomePageController {

    @FXML
    public void onClickExit(ActionEvent actionEvent) {
        App.instance.getStage().close();
    }

    @FXML
    public void onClickServiceRequests(ActionEvent actionEvent) {
        App.instance.setView("view/general/view-service.fxml");
    }

    @FXML
    public void onClickMaps(ActionEvent actionEvent) {
        //Need path to map page
    }
}
