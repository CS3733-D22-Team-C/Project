package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class LabSystemSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private TextField patientID;
    @FXML private JFXComboBox<String> labType;

    //For equipID dropdown
    private String lastType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);

        // Querying for full Medical Equipment Service Request
        LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAO();
        LabSystemSR labSystemSR = labSystemSRDAO.getByID(serviceRequest.getRequestID());

        // Setting fields from Querying Database
        labType.setPromptText(labSystemSR.getLabType().toString());
        patientID.setText(labSystemSR.getPatientID());
        description.setText(labSystemSR.getDescription());
        creationTime.setText(labSystemSR.getCreationTimestamp().toString());
        patientID.setEditable(false);


        if(isEditMode){
            // Equipment Type Dropdown
            for (LabSystemSR.LabType type : LabSystemSR.LabType.values()) {
                labType.getItems().add(type.toString());
            }
            labType.valueProperty().setValue(labType.getPromptText());
            title.setText("Edit Lab System Request");

            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");
            patientID.setEditable(true);
        }
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
        //Accessing Service Request in Database
        LabSystemSRDAO labSystemSRDAOImpl = new LabSystemSRDAO();
        LabSystemSR labSystemSR = labSystemSRDAOImpl.getByID(serviceRequest.getRequestID());
        if(isEditMode){
            //check if value has changed
            labSystemSR.setPatientID(patientID.getText());
            if(labType.getValue() != null) {
                labSystemSR.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
            }

            System.out.println(requiredFieldsPresent());
            //Status
            if(requiredFieldsPresent())
                labSystemSR.setStatus(ServiceRequest.Status.Processing);
            else
                labSystemSR.setStatus(ServiceRequest.Status.Blank);
            labSystemSRDAOImpl.update(labSystemSR);

        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    protected boolean requiredFieldsPresent(){
        if(labType.getValue() == null && labType.getPromptText().equals(""))
            return false;
        if(patientID.getText().equals(""))
            return false;
        return super.requiredFieldsPresent();
    }

    //On action (in scenebuilder)
    @FXML
    void statusUpdated(ActionEvent event) {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("processing");
        }
        else
            secondStatus.setText("blank");
    }

    //Just for textfields
    //on key pressed (in scenebuilder)
    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated(null);
    }



}
