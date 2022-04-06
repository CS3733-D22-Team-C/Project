package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseServiceRequestController implements Initializable {

    //FXML
    @FXML
    private TextField assigneeID;

    @FXML
    private JFXTextArea description;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField location;

    @FXML
    private JFXComboBox<?> priority;

    @FXML
    private Button resetButton;

    @FXML
    private Button submitButton;

    @FXML
    private VBox fieldsBox;

    @FXML
    private JFXTreeTableView<?> table;


    @FXML
    void clickGoBack(ActionEvent event) {

    }

    @FXML
    void clickReset(ActionEvent event) {

    }

    @FXML
    void clickSubmit(ActionEvent event) {

    }

    protected void setSRPane(String viewFile) {
        App.View view = App.instance.loadView(viewFile);
        fieldsBox.getChildren().add(view.getNode());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSRPane("view/service_request/sanitation/create_fields.fxml");
    }
}
