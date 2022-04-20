package edu.wpi.cs3733.D22.teamC.models.service_request.lab_system;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.translator.TranslatorSRTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LabSystemSRTableDisplay extends ServiceRequestTableDisplay<LabSystemSR> {
    public class LabSystemSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty labType;
        public StringProperty patient;

        public LabSystemSRTableEntry(LabSystemSR labSystemSR) {
            super(labSystemSR);

            PatientDAO patientDAO = new PatientDAO();
            this.labType = new SimpleStringProperty(labSystemSR.getLabType().toString());
            this.patient = new SimpleStringProperty((patientDAO.getByID(labSystemSR.getPatientID()) != null)
                    ? patientDAO.getByID(labSystemSR.getPatientID()).toString() : "N/A");
        }
    }

    public LabSystemSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(LabSystemSR object) {
        addEntry(new LabSystemSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Lab Type",
                1f * Integer.MAX_VALUE * 16.66,
                (LabSystemSRTableEntry entry) -> {return entry.labType;}
        );

        addColumn(
                table,
                "Patient Name",
                1f * Integer.MAX_VALUE * 16.66,
                (LabSystemSRTableDisplay.LabSystemSRTableEntry entry) -> {return entry.patient;}
        );
    }
}
