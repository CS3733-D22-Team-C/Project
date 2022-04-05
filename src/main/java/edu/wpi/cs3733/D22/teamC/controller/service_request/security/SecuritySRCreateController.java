package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.security.SecuritySRTable;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation.SanitationSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.security.SecuritySRFormEvaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecuritySRCreateController extends ServiceRequestCreateController {

    // Dropdowns
    @FXML private JFXComboBox<String> secType;

    // For table
    @FXML private JFXTreeTableView<SecuritySRTable> table;
    ObservableList<SecuritySRTable> METList = FXCollections.observableArrayList();
    final TreeItem<SecuritySRTable> root = new RecursiveTreeItem<SecuritySRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<SecuritySRTable> data;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("Security Type 1");
        secType.getItems().add("Security Type 2");
        secType.getItems().add("Security Type 3");
        secType.getItems().add("Security Type 4");

        SecuritySRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);
        secType.valueProperty().setValue(null);
    }


    @FXML
    protected SecuritySR clickSubmit(ActionEvent event) {

        SecuritySRFormEvaluator sSRFE = new SecuritySRFormEvaluator();

        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = sSRFE.getSecuritySRValidationTestResult(location.getText(), assigneeID.getText(), status.getSelectionModel(), priority.getSelectionModel(), secType.getSelectionModel());

        if(errors.isEmpty())
        {
            SecuritySR securitySR = (SecuritySR) super.clickSubmit(event);

            //Sets from combo boxes
            securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

            securitySR.setRequestType(ServiceRequest.RequestType.Security);

            // Table Entry
            clickReset(event);
            SecuritySRTable met = new SecuritySRTable(securitySR);
            METList.add(met);
            return securitySR;
        }
        else
        {
            prepareErrorMessages(errors);
            return null;
        }
    }

    @Override
    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.prepareErrorMessages(l);
    }
}
