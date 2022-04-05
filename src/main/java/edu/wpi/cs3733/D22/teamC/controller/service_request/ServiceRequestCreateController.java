package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServiceRequestCreateController implements Initializable {
    // Fields
    @FXML protected TextField assigneeID;
    @FXML protected JFXTextArea description;
    @FXML protected TextField location;
    @FXML protected Label errorLabel;

    // Dropdowns
    @FXML protected JFXComboBox<String> priority;
    @FXML protected JFXComboBox<String> status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Priority dropdown
        for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
            priority.getItems().add(pri.toString());
        }

        // Status dropdown
        for (ServiceRequest.Status sta : ServiceRequest.Status.values()) {
            status.getItems().add(sta.toString());
        }

        //Restrict ID TextFields to only contain numeric values
        setIDFieldToNumeric(assigneeID);
        setIDFieldToNumeric(location);

        //Limit the length of TextFields and TextAreas so that users can input a limited number of characters:
        setTextLengthLimiter(assigneeID, 20);
        setTextLengthLimiter(location, 20);
        setTextLengthLimiter(description, 100);

        //Hide error label
        errorLabel.setVisible(false);
    }

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

    @FXML
    protected void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        // Clearing Fields
        assigneeID.clear();
        location.clear();
        description.clear();

        // Clearing Dropdowns
        priority.valueProperty().set(null);
        status.valueProperty().set(null);
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

    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l)
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

    private void addErrorToView(ServiceRequestUserInputValidationErrorItem i)
    {
        errorLabel.setText(errorLabel.getText() + "\n" + i.getReasonForValidationError());
    }

    public void resetErrorMessages()
    {
        errorLabel.setText("");
    }
}
