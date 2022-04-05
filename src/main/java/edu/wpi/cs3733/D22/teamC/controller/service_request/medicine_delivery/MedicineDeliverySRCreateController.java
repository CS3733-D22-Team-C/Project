package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
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
        MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();

        if (assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty()
        || medicine.getText().isEmpty() || dosage.getText().isEmpty() || patientID.getText().isEmpty()) {
            return null;
        }

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
}
