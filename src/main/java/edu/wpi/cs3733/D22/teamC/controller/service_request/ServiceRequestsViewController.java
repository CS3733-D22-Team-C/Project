package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.sun.tools.jconsole.JConsoleContext;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest.Status.*;

public class ServiceRequestsViewController implements Initializable {
    public static final String BASE_PATH = "view/service_request/";

    public static final String CREATE_FORM = "create_form.fxml";
    public static final String RESOLVE_FORM = "resolve_form.fxml";

    // Buttons
    @FXML private JFXButton selectButton;
    @FXML private JFXComboBox<String> serviceType;
    @FXML private JFXButton edit;
    @FXML private JFXButton resolve;

    // Table
    @FXML private JFXTreeTableView table;

    // Variables
    private ServiceRequestTableDisplay<ServiceRequest> tableDisplay;
    private ServiceRequest activeServiceRequest;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Service Request Dropdown
        for (ServiceRequest.RequestType requestType : ServiceRequest.RequestType.values()) {
            serviceType.getItems().add(requestType.toString());
        }

        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAOImpl();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAllServiceRequests();
        tableDisplay = new ServiceRequestTableDisplay<ServiceRequest>(table);
        serviceRequests.forEach(tableDisplay::addObject);

        // Set Row Interaction with Table Display
        setRowInteraction();
    }

    //#region Button Events
        @FXML
        void onSelectButton(ActionEvent event) {
            if (serviceType.getValue() != null) {
                App.instance.setView(generatePath(ServiceRequest.RequestType.valueOf(serviceType.getValue()), true));
            }
        }

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
            System.out.println(generatePath(serviceRequest.getRequestType(), true));
            App.View<ServiceRequestResolveController> view = App.instance.loadView(generatePath(serviceRequest.getRequestType(), false));
            view.getController().setup(serviceRequest, true);
            App.instance.setView(view.getNode());
        }

        private void toResolvePage(ServiceRequest serviceRequest) {
            System.out.println(generatePath(serviceRequest.getRequestType(), false));
            App.View<ServiceRequestResolveController> view = App.instance.loadView(generatePath(serviceRequest.getRequestType(), false));
            view.getController().setup(serviceRequest, false);
            App.instance.setView(view.getNode());
        }

        /**
         * Generate view for given service request and context.
         * @param requestType The service request type for which view we are creating.
         * @param create Boolean for generating the create form path (true), or resolve form (false).
         * @return The generated path name.
         */
        private String generatePath(ServiceRequest.RequestType requestType, boolean create) {
            String pathName = BASE_PATH;
            pathName += (requestType.toString()).toLowerCase();
            pathName += "/";
            pathName += (create) ? CREATE_FORM : RESOLVE_FORM;
            return pathName;
        }
    //#endregion
}
