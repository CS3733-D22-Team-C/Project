package edu.wpi.cs3733.D22.teamC.models.medical_equipment;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.*;

public class MedicalEquipmentTableDisplay extends TableDisplay<MedicalEquipment> {
    public class MedicalEquipmentTableEntry extends TableDisplayEntry {
        // Properties
        public StringProperty idProperty;
        public StringProperty locationNameProperty;
        public StringProperty typeProperty;
        public IntegerProperty typeNumberProperty;
        public StringProperty statusProperty;

        public MedicalEquipmentTableEntry(MedicalEquipment medicalEquipment) {
            super(medicalEquipment);

            this.idProperty             = new SimpleStringProperty(medicalEquipment.getID());
            this.locationNameProperty   = new SimpleStringProperty(new LocationDAO().getByID(medicalEquipment.getLocationID()).getShortName());
            this.typeProperty           = new SimpleStringProperty(medicalEquipment.getEquipmentType().toString());
            this.typeNumberProperty     = new SimpleIntegerProperty(medicalEquipment.getTypeNumber());
            this.statusProperty         = new SimpleStringProperty(medicalEquipment.getStatus().toString());
        }

        @Override
        public void RefreshEntry() {
            idProperty.setValue(object.getID());
            locationNameProperty.setValue(object.getLocationID());
            typeNumberProperty.setValue(object.getTypeNumber());
            statusProperty.setValue(object.getStatus().toString());
        }
    }

    public MedicalEquipmentTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(MedicalEquipment object) {
        addEntry(new MedicalEquipmentTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
//        addColumn(
//                table,
//                "ID",
//                1f * Integer.MAX_VALUE * 16.66,
//                (MedicalEquipmentTableEntry entry) -> entry.idProperty
//        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentTableEntry entry) -> entry.locationNameProperty
        );

        addColumn(
                table,
                "Type",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentTableEntry entry) -> entry.typeProperty
        );

        addColumn(
                table,
                "Number",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentTableEntry entry) -> entry.typeNumberProperty
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 16.66,
                (MedicalEquipmentTableEntry entry) -> entry.statusProperty
        );
    }
}
