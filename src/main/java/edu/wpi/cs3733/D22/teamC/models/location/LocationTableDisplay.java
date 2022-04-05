package edu.wpi.cs3733.D22.teamC.models.location;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LocationTableDisplay extends TableDisplay<Location> {
    public class LocationTableEntry extends TableDisplayEntry {
        // Properties
        private IntegerProperty id;
        private StringProperty floor;
        private StringProperty building;
        private StringProperty nodeType;
        private StringProperty longName;
        private StringProperty shortName;
        private IntegerProperty x, y;

        public LocationTableEntry(Location location) {
            super(location);

            id          = new SimpleIntegerProperty(location.getNodeID());
            floor       = new SimpleStringProperty((new FloorDAOImpl()).getFloor(location.getFloor()).getShortName());
            building    = new SimpleStringProperty(location.getBuilding());
            nodeType    = new SimpleStringProperty(location.getNodeType().toString());
            longName    = new SimpleStringProperty(location.getLongName());
            shortName   = new SimpleStringProperty(location.getShortName());
            x           = new SimpleIntegerProperty(location.getX());
            y           = new SimpleIntegerProperty(location.getY());
        }
    }

    public LocationTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(Location object) {
        addEntry(new LocationTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Location ID",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.id.asObject();}
        );

        addColumn(
                table,
                "Floor",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.floor;}
        );

        addColumn(
                table,
                "Building",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.building;}
        );

        addColumn(
                table,
                "Node Type",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.nodeType;}
        );

        addColumn(
                table,
                "Long Name",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.longName;}
        );

        addColumn(
                table,
                "Short Name",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.shortName;}
        );

        addColumn(
                table,
                "X",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.x;}
        );

        addColumn(
                table,
                "Y",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableEntry entry) -> {return entry.y;}
        );
    }
}
