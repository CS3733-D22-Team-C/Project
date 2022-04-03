package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.general.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<?> equipmentID;
    @FXML private JFXComboBox<?> equipmentType;


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Sets status at bottom
        firstStatus.setText("processing");
        secondStatus.setText("resolve");

        super.initialize(url, rb);

        //Querying
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(Integer.parseInt(requestID.getText()));

        //Setting fields from Querying Database
        equipmentType.setPromptText(medicalEquipmentSR.getEquipmentType());
        equipmentID.setPromptText(medicalEquipmentSR.getEquipmentID());
        description.setText(medicalEquipmentSR.getDescription());
        creationTime.setText(medicalEquipmentSR.getCreationTimestamp().toString());
        System.out.println(medicalEquipmentSR.getCreationTimestamp().toString());


    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        //Accessing Service Request in Database
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(Integer.parseInt(requestID.getText()));

        //Updating to Done in Database
        medicalEquipmentSR.setStatus("Done"); //TODO Should be an enum
        medicalEquipmentSRDAOImpl.updateServiceRequest(medicalEquipmentSR);

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }



}
