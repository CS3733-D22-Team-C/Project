package edu.wpi.cs3733.D22.teamC.controller.credit;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditPageController implements Initializable {

    @FXML
    private JFXButton goBackButton;


    @FXML
    private void onGoBackButtonPressed(ActionEvent actionEvent){
        App.instance.setView(App.DASHBOARD_PATH);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
