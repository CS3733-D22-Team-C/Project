package edu.wpi.cs3733.D22.teamC.models.service_request.patient_transport;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientTransportSRTableDisplay extends ServiceRequestTableDisplay<PatientTransportSR> {

    public class PatientTransportSRTableEntry extends ServiceRequestTableEntry {
        public StringProperty patient;
        public StringProperty date;

        public PatientTransportSRTableEntry(PatientTransportSR patientTransportSR) {
            super(patientTransportSR);

            PatientDAO patientDAO = new PatientDAO();
            this.patient = new SimpleStringProperty(patientDAO.getByID(patientTransportSR.getPatientID()).getFirstName());
            this.date = new SimpleStringProperty(String.valueOf(patientTransportSR.getTransportTime()));
        }
    }
    public PatientTransportSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }
    @Override
    public void addObject(PatientTransportSR object) {
        addEntry(new PatientTransportSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        super.setPartialColumns(table);

        addColumn(
                table,
                "Patient Name",
                1f * Integer.MAX_VALUE * 16.66,
                (PatientTransportSRTableEntry entry) -> {return entry.patient;}
        );
        addColumn(
                table,
                "Transport Date",
                1f * Integer.MAX_VALUE * 16.66,
                (PatientTransportSRTableEntry entry) -> {return entry.date;}
        );

    }

}
