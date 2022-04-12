package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.service_request.SRErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class ServiceRequestCreateController <T extends ServiceRequest> implements Initializable {
    // Fields
    @FXML protected TextField assigneeID;
    @FXML protected JFXTextArea description;
    @FXML protected TextField location;
    @FXML protected Label errorLabel;

    // Dropdowns
    @FXML protected JFXComboBox<String> priority;
    @FXML protected JFXComboBox<String> status;

    // Table
    @FXML protected JFXTreeTableView table;

    // Variables
    protected ServiceRequestTableDisplay<T> tableDisplay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Hide error label
        errorLabel.setVisible(false);
    }

    @FXML
    protected void clickGoBack(ActionEvent event) {

    }

    @FXML
    protected void clickReset(ActionEvent event) {

    }

    /*
    @FXML
    protected ServiceRequest clickSubmit(ActionEvent event)
    {
        ServiceRequest sR = new ServiceRequest();
        //Downcasting as a result of the code here causes a ClassCastException, might need some advice getting around this to complete the abstraction.
        return sR;
    }
    */

    public void prepareErrorMessages(ArrayList<SRErrorItem> l)
    {
        errorLabel.setVisible(true);

        for(int i = 0; i < l.size(); i++)
        {
            if(l.get(i) != null)
            {
                addErrorToView(l.get(i));
            }
        }
    }

    private void addErrorToView(SRErrorItem i)
    {
        errorLabel.setText(errorLabel.getText() + "\n" + i.getReasonForValidationError());
    }

    public void resetErrorMessages()
    {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    //#region Field Constraints
    public void setIDFieldToNumeric(TextField tf)
    {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void setTextLengthLimiter(final TextField textF, final int maxLength) {
        textF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textF.getText().length() > maxLength) {
                    String s = textF.getText().substring(0, maxLength);
                    textF.setText(s);
                }
            }
        });
    }

    public void setTextLengthLimiter(final TextArea textA, final int maxLength) {
        textA.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textA.getText().length() > maxLength) {
                    String s = textA.getText().substring(0, maxLength);
                    textA.setText(s);
                }
            }
        });
    }
    //#endregion
}
