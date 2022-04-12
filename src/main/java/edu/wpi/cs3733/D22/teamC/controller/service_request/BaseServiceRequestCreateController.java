package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class BaseServiceRequestCreateController<T extends ServiceRequest> {
    // FXML
    @FXML
    private TextField assigneeID;

    @FXML
    private JFXTextArea description;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField locationField;

    @FXML
    private Label title;

    @FXML
    private JFXComboBox<String> priority;

    @FXML
    private Button resetButton;

    @FXML
    private Button submitButton;

    @FXML
    private VBox fieldsBox;

    @FXML
    private JFXTreeTableView<?> table;

    // References
    private InsertServiceRequestCreateController<T> insertController;

    // Variables
    private ServiceRequestTableDisplay<T> tableDisplay;
    private ServiceRequest.RequestType requestType;

    public void setup(ServiceRequest.RequestType requestType) {
        this.requestType = requestType;

        switch (requestType)
        {
            case Medical_Equipment:
                title.setText("Create Medical Equipment Service Request");
                break;
            case Facility_Maintenance:
                title.setText("Create Facility Maintenance Service Request");
                break;
            case Lab_System:
                title.setText("Create Lab System Service Request");
                break;
            case Medicine_Delivery:
                title.setText("Create Medicine Delivery Service Request");
                break;
            case Security:
                title.setText("Create Security Service Request");
                break;
            case Sanitation:
                title.setText("Create Sanitation Service Request");
                break;
            default:
        }

        // Setup Insert
        setInsert(requestType);

        // Priority dropdown
        for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
            priority.getItems().add(pri.toString());
        }

        // Restrict ID TextFields to only contain numeric values
        setIDFieldToNumeric(assigneeID);
        setIDFieldToNumeric(locationField);

        // Limit the length of TextFields and TextAreas so that users can input a limited number of characters:
        setTextLengthLimiter(assigneeID, 10);
        setTextLengthLimiter(locationField, 10);
        setTextLengthLimiter(description, 100);

        // Setup table
        tableDisplay = insertController.setupTable(table);

        // Insert to DB
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        for (T serviceRequest : serviceRequestDAO.getAll()) {
            tableDisplay.addObject(serviceRequest);
        }
    }


    boolean requiredFieldsPresent(){
        if (priority.getValue() == null && priority.getPromptText().equals(""))
            return false;
        if (assigneeID.getText().equals(""))
            return false;
        if (locationField.getText() .equals(""))
            return false;
        return insertController.requiredFieldsPresent();
    }


    public void setInsert(ServiceRequest.RequestType requestType) {
        String viewFile = "view/service_request/" + requestType.toString() + "/create_insert.fxml";

        App.View<InsertServiceRequestCreateController<T>> view = App.instance.loadView(viewFile);
        fieldsBox.getChildren().add(view.getNode());
        insertController = view.getController();
    }

    private void clearFields() {
        // Clearing Fields
        assigneeID.clear();
        locationField.clear();
        description.setText("");

        // Clearing Dropdowns
        priority.valueProperty().set(null);

        insertController.clearFields();
    }

    private void createServiceRequest() {
        // Create Service Request
        T serviceRequest = insertController.createServiceRequest();

//        serviceRequest.setAssignee(assigneeID.getText()); //TODO: Replace with Employee Selector
        serviceRequest.setLocation(locationField.getText());
        serviceRequest.setDescription(description.getText());
        serviceRequest.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));

        if (requiredFieldsPresent()) serviceRequest.setStatus(ServiceRequest.Status.Processing);
        else serviceRequest.setStatus(ServiceRequest.Status.Blank);

        // TODO: Timestamps should be handled by Hibernation, investigate later !!!
//        serviceRequest.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
//        serviceRequest.setCreatorID();
//        serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
//        serviceRequest.setModifierID()

        serviceRequest.setRequestType(requestType);

        // Insert to DB
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        serviceRequest.setRequestID(serviceRequestDAO.insert(serviceRequest));

        // Add to TableDisplay
        tableDisplay.addObject(serviceRequest);

        clearFields();
    }

    //#region FXML Buttons
    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    void clickReset(ActionEvent event) {
        clearFields();
    }

    @FXML
    void clickSubmit(ActionEvent event) {
        createServiceRequest();
        clearFields();
    }
    //#endregion

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
