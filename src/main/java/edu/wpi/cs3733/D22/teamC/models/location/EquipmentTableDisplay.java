package edu.wpi.cs3733.D22.teamC.models.location;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapSideViewController;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquipmentTableDisplay extends TableDisplay<BaseMapSideViewController.Equipment> {
    public class EquipmentTableEntry extends TableDisplayEntry {
        // Properties
        private StringProperty  numOfBeds;
        private IntegerProperty numOfRecliners;
        private IntegerProperty numOfXRays;
        private IntegerProperty numOfPumps;

        public EquipmentTableEntry(BaseMapSideViewController.Equipment equipment) {
            super(equipment);

            numOfBeds           = new SimpleStringProperty(equipment.numOfBeds);
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
        SVGParser svgParser = new SVGParser();
        String bedIcon = svgParser.getPath("static/icons/bed-solid.svg");
        String reclinerIcon = svgParser.getPath("static/icons/couch-solid.svg");
        String pumpIcon = svgParser.getPath("static/icons/pump-medical-solid.svg");
        String xRayIcon = svgParser.getPath("static/icons/x-ray-solid.svg");

        SVGGlyph bedContent = new SVGGlyph(bedIcon);
        SVGGlyph reclinerContent = new SVGGlyph(reclinerIcon);
        SVGGlyph xRayContent = new SVGGlyph(xRayIcon);
        SVGGlyph pumpContent = new SVGGlyph(pumpIcon);

        // Insert Columns for Table
        addColumn(
                table,
                "",
                bedContent,
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfBeds;}
        );

        addColumn(
                table,
                "",
                reclinerContent,
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfRecliners;}
        );

        addColumn(
                table,
                "",
                xRayContent,
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfXRays;}
        );

        addColumn(
                table,
                "",
                pumpContent,
                1f * Integer.MAX_VALUE * 25.0,
                (EquipmentTableEntry entry) -> {return entry.numOfPumps;}
        );
    }
}
