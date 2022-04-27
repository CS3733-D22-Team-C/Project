package edu.wpi.cs3733.D22.teamC.controller.service_request.landing_page;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.SegmentBarController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceException;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest.Status.*;

public class ServiceRequestLandingPage implements Initializable {

    // Form Paths
    public static final String CREATE_FORM = "view/service_request/skeleton/create_form.fxml";
    public static final String RESOLVE_FORM = "view/service_request/skeleton/resolve_form.fxml";
    public static final String BASE_PATH = "view/service_request/";

    private ServiceRequest activeServiceRequest;

    // Button Paths
    public static final String API_BUTTONS = "view/service_request/landing_page/landing_page_api_buttons.fxml";
    public static final String BUTTONS = "view/service_request/landing_page/landing_page_buttons.fxml";

    //Name labels
    @FXML private Label brandon;
    @FXML private Label brian;
    @FXML private Label grace;
    @FXML private Label jack;
    @FXML private Label matthew;
    @FXML private Label mia;
    @FXML private Label nelson;
    @FXML private Label nick;
    @FXML private Label vagmi;
    @FXML private Label vishnu;
    @FXML private ImageView eye;
    
    // Child Controllers
    ServiceRequestScrollPane childController;
    ServiceRequestAPIScrollPane childAPIController;

    // Show labels with names
    @FXML private VBox tableBox;

    @FXML private VBox buttonsBox;


    @FXML private SearchableComboBox<ServiceRequest.RequestType> serviceReqTypeComboBox;
    @FXML private MFXButton SRTypeClearButton;

    private boolean canSee = false;

    @FXML private JFXButton edit;
    @FXML private JFXButton resolve;
    @FXML private JFXButton deleteButton;
    @FXML private VBox scrollPaneButtons;

    // Table
    @FXML private JFXTreeTableView table;

    // Variables
    private ServiceRequestTableDisplay<ServiceRequest> tableDisplay;
    private boolean api = false;
    private ServiceRequest.RequestType filterRequestType;

    SegmentBarController insertBarController;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Insert for Segmented Bar
        setSegmentedBarInsert();
        insertBarController.preSetup();

        //Service Request type dropdown
        serviceReqTypeComboBox.getItems().setAll(ServiceRequest.RequestType.values());

        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAll();
        tableDisplay = new ServiceRequestTableDisplay<ServiceRequest>(table);
        serviceRequests.forEach(tableDisplay::addObject);
        for (ServiceRequest serviceRequest : serviceRequests) {
            insertBarController.updateNumbers(serviceRequest.getStatus(), true);
        }

        insertBarController.setup(true);
        // Load the cards view
//        App.View cards = App.instance.loadView(App.SERVICE_REQUEST_CARDS);

//        cardsView.getChildren().add(cards.getNode());

        // Set Row Interaction with Table Display
        setRowInteraction();

        // Add base buttons
        App.View view = App.instance.loadView(BUTTONS);
        this.childController = (ServiceRequestScrollPane) view.getController();
        scrollPaneButtons.getChildren().add(view.getNode());
        childController.setParentController(this);
        childController.changeNameVisibility(canSee);

        if (!App.instance.getUserAccount().getAdmin()) {
            buttonsBox.getChildren().remove(deleteButton);
        } else {
            deleteButton.setDisable(true);
        }
    }

    //#region Button Events
    @FXML
    public void onEditButton(ActionEvent actionEvent) {
        toEditPage(activeServiceRequest);
    }

    @FXML
    public void onResolveButton(ActionEvent actionEvent) {
        toResolvePage(activeServiceRequest);
    }
    //#endregion

    /**
     * Sets row interaction by setting the onMouseClicked events for each row.
     */
    protected void setRowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    setActiveServiceRequest((ServiceRequest) row.getItem().object);

                    if (event.getClickCount() == 2) {
                        // Double Click shortcut to service request edit/resolve page
                        toDefaultPage(activeServiceRequest);
                    }
                }
            });
            return row ;
        });
    }

    /**
     * Sets the currently active Service Request (will have its information passed to edit/resolve pages).
     * @param serviceRequest The Service Request to be set as active.
     */
    private void setActiveServiceRequest(ServiceRequest serviceRequest) {
        activeServiceRequest = serviceRequest;

        // Update Edit/Resolve Buttons
        if (activeServiceRequest.getStatus() == Blank) {
            edit.setDisable(false);
            resolve.setDisable(true);
        } else if (activeServiceRequest.getStatus() == Processing) {
            edit.setDisable(false);
            resolve.setDisable(false);
        } else if (activeServiceRequest.getStatus() == Done) {
            edit.setDisable(true);
            resolve.setDisable(true);
        }
        deleteButton.setDisable(false);
    }

    //#region Page Navigation
    public void toDefaultPage(ServiceRequest serviceRequest) {
        switch (serviceRequest.getStatus()) {
            case Blank:
                toEditPage(serviceRequest);
                break;
            case Processing:
                toEditPage(serviceRequest);
                break;
            case Done:
                // "Done" Service Requests cannot be edited or resolved
                return;
        }
    }

    private void toEditPage(ServiceRequest serviceRequest) {
        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(RESOLVE_FORM);
        view.getController().setup(serviceRequest, true);
        App.instance.setView(view.getNode());
    }

    private void toResolvePage(ServiceRequest serviceRequest) {
        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(RESOLVE_FORM);
        view.getController().setup(serviceRequest, false);
        App.instance.setView(view.getNode());
    }

    /**
     * Generate view for given service request resolve page and context.
     * @param requestType The service request type for which view we are creating.
     * @return The generated path name.
     */
    private String generatePath(ServiceRequest.RequestType requestType) {
        String pathName = BASE_PATH;
        pathName += (requestType.toString()).toLowerCase();
        pathName += "/" + RESOLVE_FORM;
        return pathName;
    }
    //#endregion

    @FXML
    void canViewNames() {
        canSee = !canSee;
        if(api) {
            childAPIController.changeNameVisibility(canSee);
        }
        else {
            childController.changeNameVisibility(canSee);
        }

        if (canSee){
            //Closed Eye
            Image img1 = new Image("edu/wpi/cs3733/D22/teamC/static/images/Closed_Eye.png");
            eye.setImage(img1);
        } else {
            //Open Eye
            Image img1 = new Image("edu/wpi/cs3733/D22/teamC/static/images/Eye.png");
            eye.setImage(img1);
        }
    }

    protected void goToSRPage(ServiceRequest.RequestType type){
        App.View<BaseServiceRequestCreateController> view = App.instance.loadView(CREATE_FORM);
        view.getController().setup(type);
        App.instance.setView(view.getNode());
    }

    public void setSegmentedBarInsert() {
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertBarController = view.getController();
        tableBox.getChildren().add(0, view.getNode());
    }

    @FXML
    private void onAPIToggleButtonPress() {
        // Remove currently existing buttons
        scrollPaneButtons.getChildren().remove(0,1);
        api = !api;
        // Check what state the button is in, if selected swap everything for API service requests, else its regular requests
        if(api) {
            App.View view = App.instance.loadView(API_BUTTONS);
            this.childAPIController = (ServiceRequestAPIScrollPane) view.getController();
            scrollPaneButtons.getChildren().add(view.getNode());
            childAPIController.changeNameVisibility(canSee);
        }
        else {
            App.View view = App.instance.loadView(BUTTONS);
            this.childController = (ServiceRequestScrollPane) view.getController();
            scrollPaneButtons.getChildren().add(view.getNode());
            childController.setParentController(this);
            childController.changeNameVisibility(canSee);
        }
    }

    @FXML
    void onDeleteButton(){
        if (activeServiceRequest != null) {
            //Update SegementBar
            insertBarController.updateNumbers(activeServiceRequest.getStatus(), false);
            insertBarController.setup(false);

            ServiceRequestDAO srDao = new ServiceRequestDAO();
            srDao.delete(activeServiceRequest);
            tableDisplay.removeObject(activeServiceRequest);
            table.getSelectionModel().clearSelection();
            
            deleteButton.setDisable(true);
            edit.setDisable(true);
            resolve.setDisable(true);
        }
    }

    public void setServiceRequestFilterAndPopulate(ServiceRequest.RequestType type) {
        this.filterRequestType = type;

        ComponentWrapper.setValueSilently(serviceReqTypeComboBox, type);

        tableDisplay.emptyTable();

        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAll();
        if(filterRequestType != null){
            serviceRequests = serviceRequests.stream().filter(serviceRequest -> serviceRequest.getRequestType().equals(serviceReqTypeComboBox.getValue())).collect(Collectors.toList());
        }
        serviceRequests.forEach(tableDisplay::addObject);


        insertBarController.clear();
        for (ServiceRequest serviceRequest : serviceRequests) {
            if(serviceRequest.getRequestType().equals(serviceReqTypeComboBox.getValue()))
                insertBarController.updateNumbers(serviceRequest.getStatus(), true);
        }

        insertBarController.setup(false);
        setActiveServiceRequest(null);
    }


    @FXML
    void onClearSRTypeButtonClicked(ActionEvent event) {
        serviceReqTypeComboBox.setValue(null);
        insertBarController.clear();
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAll();

        for (ServiceRequest serviceRequest : serviceRequests) {
                insertBarController.updateNumbers(serviceRequest.getStatus(), true);
        }

        insertBarController.setup(false);

    }


    @FXML
    void onSRTypeSelected(ActionEvent event) {
        setServiceRequestFilterAndPopulate(serviceReqTypeComboBox.getValue());
    }
}
