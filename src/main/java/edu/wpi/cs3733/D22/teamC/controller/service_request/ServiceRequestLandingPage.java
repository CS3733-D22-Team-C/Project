package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestsViewController.CREATE_FORM;

public class ServiceRequestLandingPage implements Initializable {

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
    @FXML private Image eyecon;


    private boolean canSee = false;
    private ServiceRequestsViewController serviceRequestsViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeNameVisibility(canSee);
        //        try {
//            changeNameVisibility(canSee);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void canViewNames() {
        canSee = !canSee;
        changeNameVisibility(canSee);

        if (canSee){
            Image img1 = new Image("edu/wpi/cs3733/D22/teamC/static/images/Eye.png");
            eye.setImage(img1);
        } else {
            Image img1 = new Image("edu/wpi/cs3733/D22/teamC/static/images/Lab_Systems(vial).png");
            eye.setImage(img1);
        }
        //        try {
//            changeNameVisibility(canSee);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void clickFacilityMaintenance(MouseEvent event) {
        //goToSRPage(ServiceRequest.RequestType.Facility_Maintenance);
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

    private void changeNameVisibility(boolean canSee) {//throws FileNotFoundException {
//        if (canSee){
//            eyecon = new Image(new FileInputStream("@../../../../../../../../../../../Icons/Sanitations(hand).png"));
//        }
//        else{
//            eyecon = new Image(new FileInputStream("@../../../../../../../../../../../Icons/Eye.png"));
//        }
//        eye.setImage(eyecon);
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

    private void replaceEye(){
        if (eye.isVisible()){
            eye.setVisible(false);
            //logic to add new image
        } else {

        }
    }

}