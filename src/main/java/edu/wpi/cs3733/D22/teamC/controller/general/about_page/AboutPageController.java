package edu.wpi.cs3733.D22.teamC.controller.general.about_page;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutPageController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) {
        App.instance.setView(App.DASHBOARD_PATH);
    }
}
