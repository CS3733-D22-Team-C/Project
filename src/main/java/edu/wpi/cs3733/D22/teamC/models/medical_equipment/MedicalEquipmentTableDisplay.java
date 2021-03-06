package edu.wpi.cs3733.D22.teamC.models.medical_equipment;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.*;

public class MedicalEquipmentTableDisplay extends TableDisplay<MedicalEquipment> {
    public class MedicalEquipmentTableEntry extends TableDisplayEntry {
        // Properties
        public StringProperty locationNameProperty;
        public StringProperty typeProperty;
        public IntegerProperty typeNumberProperty;
        public StringProperty statusProperty;

        public MedicalEquipmentTableEntry(MedicalEquipment medicalEquipment) {
            super(medicalEquipment);

            this.locationNameProperty   = new SimpleStringProperty((medicalEquipment.getLocation() == null) ? "" : new LocationDAO().getByID(medicalEquipment.getLocation().getID()).getShortName());
            this.typeProperty           = new SimpleStringProperty(medicalEquipment.getEquipmentType().toString());
            this.typeNumberProperty     = new SimpleIntegerProperty(medicalEquipment.getTypeNumber());
            this.statusProperty         = new SimpleStringProperty(medicalEquipment.getStatus().toString());
        }

        @Override
        public void RefreshEntry() {
            locationNameProperty.setValue(new LocationDAO().getByID(object.getLocation().getID()).getShortName());
            typeProperty.setValue(object.getEquipmentType().toString());
            typeNumberProperty.setValue(object.getTypeNumber());
            statusProperty.setValue(object.getStatus().toString());
        }
    }

    public MedicalEquipmentTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(MedicalEquipment object) {
        if (object != null) addEntry(new MedicalEquipmentTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        addColumn(
                table,
                "Type",
                1f * Integer.MAX_VALUE * 25,
                (MedicalEquipmentTableEntry entry) -> entry.typeProperty
        );

        addColumn(
                table,
                "Number",
                1f * Integer.MAX_VALUE * 25,
                (MedicalEquipmentTableEntry entry) -> entry.typeNumberProperty
        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 25,
                (MedicalEquipmentTableEntry entry) -> entry.locationNameProperty
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 25,
                (MedicalEquipmentTableEntry entry) -> entry.statusProperty
        );
    }
}
