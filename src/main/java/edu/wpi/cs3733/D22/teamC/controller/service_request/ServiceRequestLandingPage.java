package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import edu.wpi.cs3733.D22.teamC.API;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestsViewController.CREATE_FORM;

public class ServiceRequestLandingPage implements Initializable {

    App app = new App();
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
    @FXML private Label vishu;
    @FXML private ImageView eye;

    @FXML
    private GridPane root;

    private boolean canSee = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater( () -> root.requestFocus() );
        changeNameVisibility(canSee);

    }

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

    @FXML
    void clickFacilityMaintenance(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Facility_Maintenance);
    }

    @FXML
    void clickLabSystem(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Lab_System);
    }

    @FXML
    void clickMedicalEquipment(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Medical_Equipment);
    }

    @FXML
    void clickMedicineDelivery(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Medicine_Delivery);
    }

    @FXML
    void clickSanitation(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Sanitation);
    }

    @FXML
    void clickSecurity(ActionEvent event) {
        goToSRPage(ServiceRequest.RequestType.Security);
    }

    @FXML
    void clickDeliverySystem(ActionEvent event) {
        //goToSRPage(ServiceRequest.RequestType.Delivery_System);

    }

    private void changeNameVisibility(boolean canSee) {//throws FileNotFoundException {
        brandon.setVisible(canSee);
        brian.setVisible(canSee);
        grace.setVisible(canSee);
        jack.setVisible(canSee);
        matthew.setVisible(canSee);
        mia.setVisible(canSee);
        nelson.setVisible(canSee);
        nick.setVisible(canSee);
        vagmi.setVisible(canSee);
        vishu.setVisible(canSee);
    }

    private void goToSRPage(ServiceRequest.RequestType type){
            App.View<BaseServiceRequestCreateController> view = App.instance.loadView(CREATE_FORM);
            view.getController().setup(type);
            App.instance.setView(view.getNode());
    }


    @FXML
    void clickBackToTable(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    void clickLaundry(ActionEvent event) { goToSRPage(ServiceRequest.RequestType.Laundry);
    }
}