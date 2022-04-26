package edu.wpi.cs3733.D22.teamC.controller.service_request.landing_page;

import edu.wpi.cs3733.D22.teamC.controller.component.sidebar.SidebarMenuController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestScrollPane implements Initializable {

    ServiceRequestLandingPage parentController;
    Label[] allLabels;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allLabels = new Label[]{brandon, brian, grace, jack, matthew, mia, nelson, nick, vagmi, vishnu};
    }

    /**
     * Sets the visibility of the name labels
     * @param canSee true if you can see the names, false if you can't see the names.
     */
    protected void changeNameVisibility(boolean canSee) {
        for (Label label : allLabels) {
            label.setVisible(canSee);
        }
    }

    @FXML
    void clickFacilityMaintenance() {
        parentController.goToSRPage(ServiceRequest.RequestType.Facility_Maintenance);
    }

    @FXML
    void clickLabSystem() {
        parentController.goToSRPage(ServiceRequest.RequestType.Lab_System);
    }

    @FXML
    void clickMedicalEquipment() {
        parentController.goToSRPage(ServiceRequest.RequestType.Medical_Equipment);
    }

    @FXML
    void clickMedicineDelivery() {
        parentController.goToSRPage(ServiceRequest.RequestType.Medicine_Delivery);
    }

    @FXML
    void clickSanitation() {
        parentController.goToSRPage(ServiceRequest.RequestType.Sanitation);
    }

    @FXML
    void clickSecurity() {
        parentController.goToSRPage(ServiceRequest.RequestType.Security);
    }

    @FXML
    void clickTranslator() {
        parentController.goToSRPage(ServiceRequest.RequestType.Translator);
    }

    @FXML
    void clickPatientTransport() {
        parentController.goToSRPage(ServiceRequest.RequestType.Patient_Transport);
    }

    @FXML
    void clickDeliverySystem() {
        parentController.goToSRPage(ServiceRequest.RequestType.Delivery_System);
    }

    @FXML
    void clickLaundry() {
        parentController.goToSRPage(ServiceRequest.RequestType.Laundry);
    }

    protected void setParentController(ServiceRequestLandingPage parentController) {
        this.parentController = parentController;
    }
}
