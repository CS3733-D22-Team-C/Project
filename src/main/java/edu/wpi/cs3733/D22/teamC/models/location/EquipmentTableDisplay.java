package edu.wpi.cs3733.D22.teamC.models.location;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapSideViewController;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EquipmentTableDisplay extends TableDisplay<BaseMapSideViewController.Equipment> {
    public class EquipmentTableEntry extends TableDisplayEntry {
        // Properties
        private IntegerProperty numOfBeds;
        private IntegerProperty numOfRecliners;
        private IntegerProperty numOfXRays;
        private IntegerProperty numOfPumps;

        public EquipmentTableEntry(BaseMapSideViewController.Equipment equipment) {
            super(equipment);

            numOfBeds           = new SimpleIntegerProperty(equipment.numOfBeds);
            numOfRecliners      = new SimpleIntegerProperty(equipment.numOfRecliners);
            numOfXRays          = new SimpleIntegerProperty(equipment.numOfXRays);
            numOfPumps          = new SimpleIntegerProperty(equipment.numOfPumps);
        }
    }

    public EquipmentTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(BaseMapSideViewController.Equipment object) {
        addEntry(new EquipmentTableDisplay.EquipmentTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Beds",
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfBeds;}
        );

        addColumn(
                table,
                "Recliners",
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfRecliners;}
        );

        addColumn(
                table,
                "X-Rays",
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfXRays;}
        );

        addColumn(
                table,
                "Pumps",
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfPumps;}
        );
    }
}
