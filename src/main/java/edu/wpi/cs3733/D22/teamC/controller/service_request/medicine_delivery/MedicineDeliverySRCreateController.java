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

        int assigneeIDInt = Integer.parseInt(assigneeID.getText());
        int locationInt = Integer.parseInt(location.getText());
        int patientIDInt = Integer.parseInt(patientID.getText());

        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = mDSRFE.getMedicineDeliverySRValidationTestResult(locationInt, assigneeIDInt, patientIDInt, medicine.getText(), dosage.getText(), status.getSelectionModel(), priority.getSelectionModel());

        if(errors.isEmpty())
        {
            MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();

            // Set from Field
            medicineDeliverySR.setAssigneeID(assigneeID.getText());
            medicineDeliverySR.setDescription(description.getText());
            medicineDeliverySR.setLocation(location.getText());

            medicineDeliverySR.setMedicine(medicine.getText());
            medicineDeliverySR.setDosage(dosage.getText());
            medicineDeliverySR.setPatientID(patientID.getText());

            // Set from Dropdowns
            medicineDeliverySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            medicineDeliverySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));

            medicineDeliverySR.setRequestType(ServiceRequest.RequestType.Medicine_Delivery);

            clickReset(event);

            // Add to Table List
            tableList.add(new MedicineDeliverySRTable(medicineDeliverySR));

            return medicineDeliverySR;
        }
        else
        {
            generateErrorMessages(errors);
            return null;
        }
    }

    @Override
    public void generateErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.generateErrorMessages(l);
    }
}
