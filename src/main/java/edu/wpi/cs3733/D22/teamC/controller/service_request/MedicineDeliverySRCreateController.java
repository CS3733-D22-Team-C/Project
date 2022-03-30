package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliveryServiceRequest;
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

    // TODO: Table Declaration
//    // For table
//    @FXML private JFXTreeTableView<MedicalEquipmentSRTable> table;
//    ObservableList<MedicalEquipmentSRTable> METList = FXCollections.observableArrayList();
//    final TreeItem<MedicalEquipmentSRTable> root = new RecursiveTreeItem<MedicalEquipmentSRTable>(METList, RecursiveTreeObject::getChildren);
//
//    ObservableList<MedicalEquipmentSRTable> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        // TODO: Table Initialization
//        MedicalEquipmentSRTable.createTableColumns(table);
//        table.setRoot(root);
//        table.setShowRoot(false);
    }

    @FXML
    void clickReset(ActionEvent event) {
        super.clickReset(event);

        medicine.clear();
        dosage.clear();
        patientID.clear();
    }

    @FXML
    MedicineDeliveryServiceRequest clickSubmit(ActionEvent event) {
        MedicineDeliveryServiceRequest medicineDeliverySR = new MedicineDeliveryServiceRequest();

        // Set from Field
        medicineDeliverySR.setAssigneeID(assigneeID.getText());
        medicineDeliverySR.setDescription(description.getText());
        medicineDeliverySR.setLocation(location.getText());

        medicineDeliverySR.setMedicine(medicine.getText());
        medicineDeliverySR.setDosage(dosage.getText());
        medicineDeliverySR.setPatientID(patientID.getText());

        // Set from Dropdowns
        medicineDeliverySR.setStatus(status.getValue());
        medicineDeliverySR.setPriority(priority.getValue());

        clickReset(event);

        // TODO: Table Entry
//        MedicineDeliverySRTable tableEntry = new MedicineDeliverySRTable(medicineDeliverySR);
//        METList.add(medicineDeliverySR);

        return medicineDeliverySR;
    }
}
