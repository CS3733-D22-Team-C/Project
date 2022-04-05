package edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicalEquipmentSRTableDisplay extends ServiceRequestTableDisplay<MedicalEquipmentSR> {
    public class MedicalEquipmentSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty equipmentID;
        public StringProperty equipmentType;

        public MedicalEquipmentSRTableEntry(MedicalEquipmentSR medicalEquipmentSR) {
            super(medicalEquipmentSR);

            this.equipmentID = new SimpleStringProperty(medicalEquipmentSR.getEquipmentID());
            this.equipmentType = new SimpleStringProperty(medicalEquipmentSR.getEquipmentType().toString());
        }
    }

    public MedicalEquipmentSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(MedicalEquipmentSR object) {
        addEntry(new MedicalEquipmentSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Equipment Type",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentSRTableEntry entry) -> {return entry.equipmentType;}
        );

        addColumn(
                table,
                "Equipment ID",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentSRTableEntry entry) -> {return entry.equipmentID;}
        );
    }
}
