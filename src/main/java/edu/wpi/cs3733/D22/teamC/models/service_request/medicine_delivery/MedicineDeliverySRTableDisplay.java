package edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicineDeliverySRTableDisplay extends ServiceRequestTableDisplay<MedicineDeliverySR> {
    public class MedicineDeliverySRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty medicine;
        public StringProperty dosage;
        public StringProperty patientName;

        public MedicineDeliverySRTableEntry(MedicineDeliverySR medicineDeliverySR) {
            super(medicineDeliverySR);
            
            PatientDAO patientDAO = new PatientDAO();

            this.medicine   = new SimpleStringProperty(medicineDeliverySR.getMedicine());
            this.dosage     = new SimpleStringProperty(medicineDeliverySR.getDosage());
            this.patientName = new SimpleStringProperty((patientDAO.getByID(medicineDeliverySR.getPatientID()) != null)
                    ? patientDAO.getByID(medicineDeliverySR.getPatientID()).toString() : "N/A");
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
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Medicine",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> entry.medicine
        );

        addColumn(
                table,
                "Patient Name",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicineDeliverySRTableEntry entry) -> entry.patientName
        );
    }
}
