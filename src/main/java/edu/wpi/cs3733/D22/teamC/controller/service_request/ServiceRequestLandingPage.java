package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest.Status.*;

public class ServiceRequestLandingPage implements Initializable {

    // New Paths
    public static final String CREATE_FORM = "view/service_request/skeleton/create_form.fxml";
    public static final String RESOLVE_FORM = "view/service_request/skeleton/resolve_form.fxml";

    public static final String BASE_PATH = "view/service_request/";

    @FXML private VBox tableBox;
    private ServiceRequest activeServiceRequest;


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

    @FXML private JFXButton deleteButton;
    @FXML private HBox buttonsBox;

    private boolean canSee = false;

    @FXML
    private VBox cardsView;

    @FXML private JFXButton edit;
    @FXML private JFXButton resolve;

    // Table
    @FXML private JFXTreeTableView table;

    // Variables
    private ServiceRequestTableDisplay<ServiceRequest> tableDisplay;

    SegmentBarController insertBarController;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Insert for Segmented Bar
        setSegmentedBarInsert();
        insertBarController.preSetup();

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
        changeNameVisibility(canSee);

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
        changeNameVisibility(canSee);

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

    /**
     * Sets the visibility of the name labels
     * @param canSee true if you can see the names, false if you can't see the names.
     */
    private void changeNameVisibility(boolean canSee) {
        brandon.setVisible(canSee);
        brian.setVisible(canSee);
        grace.setVisible(canSee);
        jack.setVisible(canSee);
        matthew.setVisible(canSee);
        mia.setVisible(canSee);
        nelson.setVisible(canSee);
        nick.setVisible(canSee);
        vagmi.setVisible(canSee);
        vishnu.setVisible(canSee);
    }

    @FXML
    void clickFacilityMaintenance() {
        goToSRPage(ServiceRequest.RequestType.Facility_Maintenance);
    }

    @FXML
    void clickLabSystem() {
        goToSRPage(ServiceRequest.RequestType.Lab_System);
    }

    @FXML
    void clickMedicalEquipment() {
        goToSRPage(ServiceRequest.RequestType.Medical_Equipment);
    }

    @FXML
    void clickMedicineDelivery() {
        goToSRPage(ServiceRequest.RequestType.Medicine_Delivery);
    }

    @FXML
    void clickSanitation() {
        goToSRPage(ServiceRequest.RequestType.Sanitation);
    }

    @FXML
    void clickSecurity() {
        goToSRPage(ServiceRequest.RequestType.Security);
    }

    @FXML
    void clickTranslator() {
        goToSRPage(ServiceRequest.RequestType.Translator);
    }

    @FXML
    void clickPatientTransport() {
        goToSRPage(ServiceRequest.RequestType.Patient_Transport);
    }

    @FXML
    void clickDeliverySystem() {
        goToSRPage(ServiceRequest.RequestType.Delivery_System);
    }

    @FXML
    void clickLaundry() { goToSRPage(ServiceRequest.RequestType.Laundry);
    }

    private void goToSRPage(ServiceRequest.RequestType type){
        App.View<BaseServiceRequestCreateController> view = App.instance.loadView(CREATE_FORM);
        view.getController().setup(type);
        App.instance.setView(view.getNode());
    }

    public void setSegmentedBarInsert() {
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertBarController = view.getController();
        tableBox.getChildren().add(1, view.getNode());
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


        }
    }
}