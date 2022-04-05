package edu.wpi.cs3733.D22.teamC.models.service_request.lab_system;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LabSystemSRTableDisplay extends ServiceRequestTableDisplay<LabSystemSR> {
    public class LabSystemSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty labType;

        public LabSystemSRTableEntry(LabSystemSR labSystemSR) {
            super(labSystemSR);

            this.labType = new SimpleStringProperty(labSystemSR.getLabType());
        }
    }

    public LabSystemSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(LabSystemSR object) {
        addEntry(new LabSystemSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Lab Type",
                1f * Integer.MAX_VALUE * 16.66,
                (LabSystemSRTableEntry entry) -> {return entry.labType;}
        );
    }
}
