package edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicineDeliverySRTableDisplay extends ServiceRequestTableDisplay<MedicineDeliverySR> {
    public class MedicineDeliverySRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty medicine;
        public StringProperty dosage;
        public StringProperty patientID;

        public MedicineDeliverySRTableEntry(MedicineDeliverySR medicineDeliverySR) {
            super(medicineDeliverySR);

            this.medicine   = new SimpleStringProperty(medicineDeliverySR.getMedicine());
            this.dosage     = new SimpleStringProperty(medicineDeliverySR.getDosage());
            this.patientID  = new SimpleStringProperty(medicineDeliverySR.getPatientID());
        }
    }

    public MedicineDeliverySRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(MedicineDeliverySR object) {
        addEntry(new MedicineDeliverySRTableEntry(object));
    }

    @Override
    public void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "ID",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.id;}
        );

        addColumn(
                table,
                "Assignee",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.assigneeID;}
        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.location;}
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.status;}
        );

        addColumn(
                table,
                "Priority",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.priority;}
        );

        addColumn(
                table,
                "Medicine",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.medicine;}
        );

        addColumn(
                table,
                "Patient ID",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> {return entry.patientID;}
        );
    }
}
