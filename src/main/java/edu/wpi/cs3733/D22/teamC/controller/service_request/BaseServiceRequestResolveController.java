package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.builders.NotificationBuilder;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

public class BaseServiceRequestResolveController<T extends ServiceRequest> implements ServiceRequestController{

    @FXML private VBox fieldsBox;

    //Generic boxes
    @FXML private SearchableComboBox<Employee> assigneeID;
    @FXML private SearchableComboBox<Location> locationID;
    @FXML private SearchableComboBox<String> priority;
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
    protected InsertServiceRequestResolveController<T> insertController;

    // Variables
    protected ServiceRequest.RequestType requestType;

    private T serviceRequest;
    private boolean isEditMode;

    private Employee employee;
    private Location location;
    
    protected String srUUID;

    private Runnable goBackEvent = this::returnToSRPage;

    @FXML
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        SVGParser svgParser = new SVGParser();
        String employeeIcon = svgParser.getPath("static/icons/employee_icon.svg");
        String locationIcon = svgParser.getPath("static/icons/location_icon.svg");

        SVGGlyph employeeContent = new SVGGlyph(employeeIcon);
        SVGGlyph locationContent = new SVGGlyph(locationIcon);
        employeeContent.setSize(20);
        locationContent.setSize(20);
        mapViewButton.setGraphic(locationContent);
        employeeTableButton.setGraphic(employeeContent);
        this.isEditMode = isEditMode;
        this.requestType = serviceRequest.getRequestType();

        // Setup Insert
        setInsert(requestType);

        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        this.serviceRequest = serviceRequestDAO.getByID(serviceRequest.getID());
        srUUID = this.serviceRequest.getID();

        if (serviceRequest.getLocation() != null) location = new LocationDAO().getByID(serviceRequest.getLocation().getID());

        insertController.setup(this, this.serviceRequest, isEditMode);

        // Set initial values
        List<Employee> employees = new EmployeeDAO().getAll();
        ComponentWrapper.initializeComboBox(assigneeID, Employee::toString);
        assigneeID.getItems().setAll(employees);

        List<Location> locations = new LocationDAO().getAll();
        ComponentWrapper.initializeComboBox(locationID, Location::toString);
        locationID.getItems().setAll(locations);

        priority.setPromptText(serviceRequest.getPriority().toString());
        assigneeID.setPromptText(serviceRequest.getAssignee() == null ? 
                "" : serviceRequest.getAssignee().getLastName() + ", " + serviceRequest.getAssignee().getFirstName());
        locationID.setPromptText(location == null ? "" : location.getShortName());
        creationTime.setText(serviceRequest.getCreationTimestamp().toString());
        description.setText(serviceRequest.getDescription());

        assigneeID.setValue(serviceRequest.getAssignee());
        locationID.setValue(location);

        // Set labels
        requestID.setText(serviceRequest.toString()); // Output the SR number
        assigneeID.setEditable(false);
        locationID.setEditable(false);
        lastUpdated.setText(serviceRequest.getModifiedTimestamp().toString());
        
        if(serviceRequest.getCreator() != null) {
            createdBy.setText(serviceRequest.getCreator().getLastName() + ", " + serviceRequest.getCreator().getFirstName());
        } else {
            createdBy.setText("N/A");
        }
    
        if(serviceRequest.getModifier() != null) {
            updatedBy.setText(serviceRequest.getModifier().getLastName() + ", " + serviceRequest.getModifier().getFirstName());
        } else {
            updatedBy.setText("N/A");
        }
    
        if (isEditMode) {

            // Priority Dropdown
            for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
                priority.getItems().add(pri.toString());
            }

            // Sets status at bottom
            firstStatus.setText("  " + serviceRequest.getStatus().toString().toLowerCase() + "  ");
            secondStatus.setText("  " + serviceRequest.getStatus().toString().toLowerCase() + "  ");

            // Set fields editable
            priority.setDisable(false);
            employeeTableButton.setDisable(false);
            mapViewButton.setDisable(false);
            locationID.setDisable(false);
            assigneeID.setDisable(false);

            switch (requestType)
            {
                case Medical_Equipment:
                    title.setText("Edit Medical Equipment Service Request");
                    break;
                case Facility_Maintenance:
                    title.setText("Edit Facility Maintenance Service Request");
                    break;
                case Lab_System:
                    title.setText("Edit Lab System Service Request");
                    break;
                case Medicine_Delivery:
                    title.setText("Edit Medicine Delivery Service Request");
                    break;
                case Security:
                    title.setText("Edit Security Service Request");
                    break;
                case Sanitation:
                    title.setText("Edit Sanitation Service Request");
                    break;
                case Patient_Transport:
                    title.setText("Edit Patient Transport Service Request");
                    break;
                case Laundry:
                    title.setText("Edit Laundry Service Request");
                    break;
                case Delivery_System:
                    title.setText("Edit Delivery System Service Request");
                    break;
                case Translator:
                    title.setText("Edit Translator Service Request");
                    break;
                default:
            }

        } else {

            switch (requestType)
            {
                case Medical_Equipment:
                    title.setText("Resolve Medical Equipment Service Request");
                    break;
                case Facility_Maintenance:
                    title.setText("Resolve Facility Maintenance Service Request");
                    break;
                case Lab_System:
                    title.setText("Resolve Lab System Service Request");
                    break;
                case Medicine_Delivery:
                    title.setText("Resolve Medicine Delivery Service Request");
                    break;
                case Security:
                    title.setText("Resolve Security Service Request");
                    break;
                case Sanitation:
                    title.setText("Resolve Sanitation Service Request");
                    break;
                case Patient_Transport:
                    title.setText("Resolve Patient Transport Service Request");
                    break;
                case Laundry:
                    title.setText("Resolve Laundry Service Request");
                    break;
                case Delivery_System:
                    title.setText("Resolve Delivery System Service Request");
                    break;
                case Translator:
                    title.setText("Resolve Translator Service Request");
                    break;
                default:
            }
            // Sets status at bottom
            firstStatus.setText("  processing  ");
            secondStatus.setText("  resolve  ");

            // Set fields uneditable
            priority.setDisable(true);
            employeeTableButton.setDisable(true);
            mapViewButton.setDisable(true);
            assigneeID.setDisable(true);
            locationID.setDisable(true);
        }

    }
    @FXML
    void clickConfirm(ActionEvent event) {
        //Accessing Service Request in Database
        DAO<T> serviceRequestDAO = insertController.createServiceRequestDAO();
        T serviceRequest = serviceRequestDAO.getByID(srUUID);

        if(isEditMode)
        {
            if(priority.getValue() != null) {
                serviceRequest.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            }
            //Assignee ID
            serviceRequest.setAssignee(assigneeID.getValue());
            //Location
            serviceRequest.setLocation(locationID.getValue());
            //Status
            if(requiredFieldsPresent())
                serviceRequest.setStatus(ServiceRequest.Status.Processing);
            else
                serviceRequest.setStatus(ServiceRequest.Status.Blank);

            serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setModifier(App.instance.getUserAccount());

            insertController.updateServiceRequest(serviceRequest);

            //Notification
            String createdSRNotification = "Service Request " + serviceRequest + " has been edited";
            NotificationBuilder.createNotification("Service Request Edited", createdSRNotification);

        }
        else {
            //Set status to Done
            serviceRequest.setStatus(ServiceRequest.Status.Done);
            //Notification
            String createdSRNotification = "Service Request " + serviceRequest + " has been resolved";
            NotificationBuilder.createNotification("Service Request Resolved", createdSRNotification);

        }

        serviceRequest.setDescription(description.getText());
        serviceRequestDAO.update(serviceRequest);
        insertController.updateServiceRequest(serviceRequest);



        clickGoBack(null);

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        goBackEvent.run();

    }

    private void returnToSRPage(){
        App.instance.setView(App.SERVICE_REQUEST_DASHBOARD);
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
        if (assigneeID.getValue() == null)
            return false;
        if (locationID.getValue() == null)
            return false;
        return insertController.requiredFieldsPresent();
    }

    @FXML
    void statusUpdated() {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("  processing  ");
        }
        else
            secondStatus.setText("  blank  ");

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

            assigneeID.setValue(employee);
        }

        public void setLocation(Location location) {
            this.location = location;

            String locationName = "";
            if (location != null) {
                locationName = location.getShortName();
            }

            locationID.setValue(location);
        }

    //#endregion

    public void setGoBackEvent(Runnable goBackEvent) {
        this.goBackEvent = goBackEvent;
    }
}
