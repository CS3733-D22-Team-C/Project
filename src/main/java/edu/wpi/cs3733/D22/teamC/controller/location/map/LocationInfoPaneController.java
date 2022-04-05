package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationInfoPaneController implements Initializable {

    @FXML
    TextField IDTextField;
    @FXML
    TextField LongNameTextField;
    @FXML
    TextField ShortNameTextField;
    @FXML
    TextField TypeTextField;
    @FXML
    VBox LocationVBox;

    // Parent Controller
    BaseMapViewController baseMapViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBaseMapViewController(BaseMapViewController baseMapViewController) {
        this.baseMapViewController = baseMapViewController;
    }

    /**
     * Sets location pane text fields to their respective types
     * @param ID Employee ID Number
     * @param longName Long name of the location
     * @param shortName Short name of the location
     * @param type Type of room that the location is
     */
    public void setTextFieldText(String ID, String longName, String shortName, String type) {
        IDTextField.setText(ID);
        LongNameTextField.setText(longName);
        ShortNameTextField.setText(shortName);
        TypeTextField.setText(type);
    }

    /**
     * Set all text fields within the location description panes to be editable/uneditable
     * @param editable boolean if fields should be editable
     */
    public void setEditable(boolean editable) {
        IDTextField.setEditable(editable);
        LongNameTextField.setEditable(editable);
        ShortNameTextField.setEditable(editable);
        TypeTextField.setEditable(editable);
    }

    // TODO: Hook this up to on hover functionality
    public void setVisible(boolean visible) {
        LocationVBox.setVisible(visible);
    }
}
