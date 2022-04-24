package edu.wpi.cs3733.D22.teamC.controller.service_request.landing_page;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceExceptionAPI;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    // Child Controllers
    ServiceRequestScrollPane childController;
    ServiceRequestAPIScrollPane childAPIController;

    // Show labels with names
    @FXML private ImageView eye;
    private boolean canSee = false;

    @FXML private JFXButton edit;
    @FXML private JFXButton resolve;
    @FXML private VBox scrollPaneButtons;

    // Table
    @FXML private JFXTreeTableView table;

    // Variables
    private ServiceRequestTableDisplay<ServiceRequest> tableDisplay;
    private boolean api = false;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAll();
        tableDisplay = new ServiceRequestTableDisplay<ServiceRequest>(table);
        serviceRequests.forEach(tableDisplay::addObject);

        // Set Row Interaction with Table Display
        setRowInteraction();

        // Add base buttons
        App.View view = App.instance.loadView(BUTTONS);
        this.childController = (ServiceRequestScrollPane) view.getController();
        scrollPaneButtons.getChildren().add(view.getNode());
        childController.setParentController(this);
        childController.changeNameVisibility(canSee);
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
    }

    //#region Page Navigation
    private void toDefaultPage(ServiceRequest serviceRequest) {
        switch (serviceRequest.getStatus()) {
            case Blank:
                toEditPage(serviceRequest);
                break;
            case Processing:
                toResolvePage(serviceRequest);
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
}