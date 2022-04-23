package edu.wpi.cs3733.D22.teamC.models.location;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LocationTableDisplay extends TableDisplay<Location> {
    public class LocationTableEntry extends TableDisplayEntry {
        // Properties
        private StringProperty id;
        private StringProperty floor;
        private StringProperty building;
        private StringProperty nodeType;
        private StringProperty longName;
        private StringProperty shortName;
        private IntegerProperty x, y;

        public LocationTableEntry(Location location) {
            super(location);
            
            id          = new SimpleStringProperty((location.getID()));
            floor       = new SimpleStringProperty(new FloorDAO().getByID(location.getFloor().getID()).getShortName());
            //floor       = new SimpleStringProperty(location.getFloor());
            building    = new SimpleStringProperty(location.getBuilding());
            nodeType    = new SimpleStringProperty(location.getNodeType().toString());
            longName    = new SimpleStringProperty(location.getLongName());
            shortName   = new SimpleStringProperty(location.getShortName());
            x           = new SimpleIntegerProperty(location.getX());
            y           = new SimpleIntegerProperty(location.getY());
        }

        @Override
        public void RefreshEntry() {
            id.setValue(object.getID());
            floor.setValue((new FloorDAO()).getByID(object.getFloor().getID()).getShortName());
            building.setValue(object.getBuilding());
            nodeType.setValue(object.getNodeType().toString());
            longName.setValue(object.getLongName());
            shortName.setValue(object.getShortName());
            x.setValue(object.getX());
            y.setValue(object.getY());
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
                "Long Name",
                1f * Integer.MAX_VALUE * 29.0,
                (LocationTableEntry entry) -> {return entry.longName;}
        );

        addColumn(
                table,
                "Short Name",
                1f * Integer.MAX_VALUE * 20.0,
                (LocationTableEntry entry) -> {return entry.shortName;}
        );

        addColumn(
                table,
                "Node Type",
                1f * Integer.MAX_VALUE * 12.0,
                (LocationTableEntry entry) -> {return entry.nodeType;}
        );

        addColumn(
                table,
                "Floor",
                1f * Integer.MAX_VALUE * 8.0,
                (LocationTableEntry entry) -> {return entry.floor;}
        );

        addColumn(
                table,
                "Building",
                1f * Integer.MAX_VALUE * 10.0,
                (LocationTableEntry entry) -> {return entry.building;}
        );

        addColumn(
                table,
                "X",
                1f * Integer.MAX_VALUE * 10.0,
                (LocationTableEntry entry) -> {return entry.x;}
        );

        addColumn(
                table,
                "Y",
                1f * Integer.MAX_VALUE * 10.0,
                (LocationTableEntry entry) -> {return entry.y;}
        );
    }
}
