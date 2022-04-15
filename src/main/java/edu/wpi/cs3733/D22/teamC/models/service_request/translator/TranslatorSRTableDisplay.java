package edu.wpi.cs3733.D22.teamC.models.service_request.translator;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TranslatorSRTableDisplay extends ServiceRequestTableDisplay<TranslatorSR> {

    public class TranslatorSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty language;
        public StringProperty patientID;

        public TranslatorSRTableEntry(TranslatorSR translatorSR) {
            super(translatorSR);
            this.language = new SimpleStringProperty((translatorSR.getLanguage().toString()));
            this.patientID = new SimpleStringProperty(translatorSR.getPatientID());

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
                "Language",
                1f * Integer.MAX_VALUE * 16.66,
                (TranslatorSRTableEntry entry) -> {return entry.language;}
        );

        addColumn(
                table,
                "Patient ID",
                1f * Integer.MAX_VALUE * 16.66,
                (TranslatorSRTableEntry entry) -> {return entry.patientID;}
        );
    }
}
