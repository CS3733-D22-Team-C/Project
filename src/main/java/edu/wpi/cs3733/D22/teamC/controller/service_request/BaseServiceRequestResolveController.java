package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.util.Locale;

public class BaseServiceRequestResolveController<T extends ServiceRequest> implements ServiceRequestController{

    @FXML private VBox fieldsBox;

    //Generic boxes
    @FXML private TextField assigneeID;
    @FXML private TextField locationID;
    @FXML private JFXComboBox<String> priority;
    @FXML private JFXTextArea description;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton confirmButton;

    //Top Labels
    @FXML private Label title;
    @FXML private Label requestID;
    @FXML private Label updatedBy;
    @FXML private Label lastUpdated;
    @FXML private Label createdBy;
    @FXML private Label creationTime;

    //Status Fields
    @FXML private Label secondStatus;
    @FXML private Label firstStatus;

    @FXML private JFXButton employeeTableButton;
    @FXML private JFXButton mapViewButton;

    // References
    private InsertServiceRequestResolveController<T> insertController;

    // Variables
    private ServiceRequest.RequestType requestType;

    private T serviceRequest;
    private boolean isEditMode;

    private Employee employee;
    private Location location;

    @FXML
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        this.isEditMode = isEditMode;
        this.requestType = serviceRequest.getRequestType();

        // Setup Insert
        setInsert(requestType);

        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        this.serviceRequest = serviceRequestDAO.getByID(serviceRequest.getID());

        if (serviceRequest.getLocation() != null) location = new LocationDAO().getByID(serviceRequest.getLocation());

        insertController.setup(this, this.serviceRequest, isEditMode);

        // Set initial values
        priority.setPromptText(serviceRequest.getPriority().toString());
        assigneeID.setText(serviceRequest.getAssignee() == null ? "" : serviceRequest.getAssignee().getLastName() + ", " + serviceRequest.getAssignee().getFirstName());
        locationID.setText(location == null ? "" : location.getShortName());
        creationTime.setText(serviceRequest.getCreationTimestamp().toString());
        description.setText(serviceRequest.getDescription());

        // Set labels
        requestID.setText(serviceRequest.getID()); //TODO print something else here or don't print maybe
        assigneeID.setEditable(false);
        locationID.setEditable(false);

        if (isEditMode) {
            // Set generic title (overridden in children)
            title.setText("Edit Medical Equipment Request");

            // Priority Dropdown
            for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
                priority.getItems().add(pri.toString());
            }

            // Sets status at bottom
            firstStatus.setText(serviceRequest.getStatus().toString().toLowerCase());
            secondStatus.setText(serviceRequest.getStatus().toString().toLowerCase());

            // Set fields editable
            priority.setDisable(false);
            employeeTableButton.setDisable(false);
            mapViewButton.setDisable(false);

        } else {
            // Set generic title (overridden in children)
            title.setText("Resolve Service Request");

            // Sets status at bottom
            firstStatus.setText("processing");
            secondStatus.setText("resolve");

            // Set fields uneditable
            priority.setDisable(true);
            employeeTableButton.setDisable(true);
            mapViewButton.setDisable(true);
        }

    }
    @FXML
    void clickConfirm(ActionEvent event) {
        //Accessing Service Request in Database
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        T serviceRequest = serviceRequestDAO.getByID(requestID.getText());

        if(isEditMode)
        {
            if(priority.getValue() != null) {
                serviceRequest.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            }
            //Assignee ID
            serviceRequest.setAssignee(this.serviceRequest.getAssignee());
            //Location
            serviceRequest.setLocation(location.getID());
            //Status
            if(requiredFieldsPresent())
                serviceRequest.setStatus(ServiceRequest.Status.Processing);
            else
                serviceRequest.setStatus(ServiceRequest.Status.Blank);

            serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setModifier(App.instance.getUserAccount());

            insertController.updateServiceRequest(serviceRequest);
        }
        else {
            //Set status to Done
            serviceRequest.setStatus(ServiceRequest.Status.Done);
        }

        serviceRequest.setDescription(description.getText());
        serviceRequestDAO.update(serviceRequest);
        insertController.updateServiceRequest(serviceRequest);

        clickGoBack(null);

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);

    }

    public void setInsert(ServiceRequest.RequestType requestType) {
        String viewFile = "view/service_request/" + requestType.toString().toLowerCase(Locale.ROOT) + "/resolve_insert.fxml";

        App.View<InsertServiceRequestResolveController<T>> view = App.instance.loadView(viewFile);
        insertController = view.getController();
        fieldsBox.getChildren().add(view.getNode());
    }

    boolean requiredFieldsPresent(){
        if (priority.getValue() == null && priority.getPromptText().equals(""))
            return false;
        if (assigneeID.getText().equals(""))
            return false;
        if (locationID.getText().equals(""))
            return false;
        return insertController.requiredFieldsPresent();
    }

    @FXML
    void statusUpdated() {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("processing");
        }
        else
            secondStatus.setText("blank");

        //check this
        //insertController.statusUpdated();
    }

    @FXML
    void goToEmployeeTable(ActionEvent event) {
        new EmployeeSelectorWindow(this::setEmployee);
    }

    @FXML
    void goToMapView(ActionEvent event) {
        new MapSelectorWindow(this::setLocation);
    }

    @FXML
    void statusKeyPressedUpdated(KeyEvent event) {
        statusUpdated();
    }

    public void onFieldUpdated() {
        statusUpdated();
    }

    //#region Selector Window Updaters
        public void setEmployee(Employee employee){
            serviceRequest.setAssignee(employee);

            String employeeName = "";
            if (employee != null) {
                employeeName = employee.getLastName() + ", " + employee.getFirstName();
            }

            assigneeID.setText(employeeName);
        }

        public void setLocation(Location location) {
            this.location = location;

            String locationName = "";
            if (location != null) {
                locationName = location.getShortName();
            }

            locationID.setText(locationName);
        }
    //#endregion
}
