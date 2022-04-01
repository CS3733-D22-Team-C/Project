package edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class MedicineDeliverySRTable extends RecursiveTreeObject<MedicineDeliverySRTable> {
    // Properties
    StringProperty medicine;
    StringProperty dosage;
    StringProperty patientID;

    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;

    public MedicineDeliverySRTable(MedicineDeliverySR medicineDeliverySR) {
        this.assigneeID = new SimpleStringProperty(medicineDeliverySR.getAssigneeID());
        this.location = new SimpleStringProperty(medicineDeliverySR.getLocation());
        this.status = new SimpleStringProperty(String.valueOf(medicineDeliverySR.getStatus()));
        this.priority = new SimpleStringProperty(String.valueOf(medicineDeliverySR.getPriority()));

        this.medicine = new SimpleStringProperty(medicineDeliverySR.getMedicine());
        this.dosage = new SimpleStringProperty(medicineDeliverySR.getDosage());
        this.patientID = new SimpleStringProperty(medicineDeliverySR.getPatientID());
    }

    public static void createTableColumns(JFXTreeTableView<MedicineDeliverySRTable> table) {
        //Columns for table
        JFXTreeTableColumn<MedicineDeliverySRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().priority;
            }
        });
        JFXTreeTableColumn<MedicineDeliverySRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().assigneeID;
            }
        });
        JFXTreeTableColumn<MedicineDeliverySRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().status;
            }
        });
        JFXTreeTableColumn<MedicineDeliverySRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().location;
            }
        });

        JFXTreeTableColumn<MedicineDeliverySRTable, String> medicineCol = new JFXTreeTableColumn<>("Medicine");
        medicineCol.setPrefWidth(80);
        medicineCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().medicine;
            }
        });

        JFXTreeTableColumn<MedicineDeliverySRTable, String> dosageCol = new JFXTreeTableColumn<>("Dosage");
        dosageCol.setPrefWidth(80);
        dosageCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().dosage;
            }
        });

        JFXTreeTableColumn<MedicineDeliverySRTable, String> patientCol = new JFXTreeTableColumn<>("Patient ID");
        patientCol.setPrefWidth(80);
        patientCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicineDeliverySRTable, String> param) {
                return param.getValue().getValue().patientID;
            }
        });

        // Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, medicineCol, dosageCol, patientCol);
    }
}
