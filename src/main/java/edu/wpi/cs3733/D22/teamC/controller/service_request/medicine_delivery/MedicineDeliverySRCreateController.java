package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.lab_system.LabSystemSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery.MedicineDeliverySRFormEvaluator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicineDeliverySRCreateController extends ServiceRequestCreateController {
    // Fields
    @FXML private TextField medicine;
    @FXML private TextField dosage;
    @FXML private TextField patientID;

    // For table
    @FXML private JFXTreeTableView<MedicineDeliverySRTable> table;
    ObservableList<MedicineDeliverySRTable> tableList = FXCollections.observableArrayList();
    final TreeItem<MedicineDeliverySRTable> root = new RecursiveTreeItem<MedicineDeliverySRTable>(tableList, RecursiveTreeObject::getChildren);
    ObservableList<MedicineDeliverySRTable> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        MedicineDeliverySRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        //For Patient ID:
        setIDFieldToNumeric(patientID);

        setTextLengthLimiter(medicine, 20);
        setTextLengthLimiter(dosage, 20);
        setTextLengthLimiter(patientID, 20);
    }

    @Override
    public void setTextLengthLimiter(TextField textF, int maxLength) {
        super.setTextLengthLimiter(textF, maxLength);
    }

    @Override
    public void setIDFieldToNumeric(TextField tf) {
        super.setIDFieldToNumeric(tf);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);

        medicine.clear();
        dosage.clear();
        patientID.clear();
    }

    @FXML
    protected MedicineDeliverySR clickSubmit(ActionEvent event) {

        MedicineDeliverySRFormEvaluator mDSRFE = new MedicineDeliverySRFormEvaluator();

        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = mDSRFE.getMedicineDeliverySRValidationTestResult(location.getText(), assigneeID.getText(), patientID.getText(), medicine.getText(), dosage.getText(), status.getSelectionModel(), priority.getSelectionModel());

        if(noUserErrors(errors))
        {
            MedicineDeliverySR mdSR = new MedicineDeliverySR();

            mdSR.setAssigneeID(assigneeID.getText());
            mdSR.setLocation(location.getText());
            mdSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            mdSR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            mdSR.setDescription(description.getText());

            // Set from Field
            mdSR.setMedicine(medicine.getText());
            mdSR.setDosage(dosage.getText());
            mdSR.setPatientID(patientID.getText());

            mdSR.setRequestType(ServiceRequest.RequestType.Medicine_Delivery);

            clickReset(event);

            // Add to Table List
            tableList.add(new MedicineDeliverySRTable(mdSR));

            return mdSR;
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

    @Override
    public boolean noUserErrors(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        return super.noUserErrors(l);
    }
}
