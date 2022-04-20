package edu.wpi.cs3733.D22.teamC.models.service_request.translator;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TranslatorSRTableDisplay extends ServiceRequestTableDisplay<TranslatorSR> {

    public class TranslatorSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty language;
        public StringProperty patient;

        public TranslatorSRTableEntry(TranslatorSR translatorSR) {
            super(translatorSR);

            PatientDAO patientDAO = new PatientDAO();
            this.language = new SimpleStringProperty((translatorSR.getLanguage().toString()));
            this.patient = new SimpleStringProperty((translatorSR.getPatient() != null)
                    ? translatorSR.getPatient().toString() : "N/A");

        }
    }

    public TranslatorSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(TranslatorSR object) {
        addEntry(new TranslatorSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Patient Name",
                1f * Integer.MAX_VALUE * 16.66,
                (TranslatorSRTableEntry entry) -> {return entry.patient;}
        );

        addColumn(
                table,
                "Language",
                1f * Integer.MAX_VALUE * 16.66,
                (TranslatorSRTableEntry entry) -> {return entry.language;}
        );


    }
}
