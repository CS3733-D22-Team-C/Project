package edu.wpi.cs3733.D22.teamC.models.service_request.sanitation;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SanitationSRTableDisplay extends ServiceRequestTableDisplay<SanitationSR> {
    public class SanitationSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty sanitationType;

        public SanitationSRTableEntry(SanitationSR sanitationSR) {
            super(sanitationSR);

            this.sanitationType = new SimpleStringProperty(sanitationSR.getSanitationType());
        }
    }

    public SanitationSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(SanitationSR object) {
        addEntry(new SanitationSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Lab Type",
                1f * Integer.MAX_VALUE * 16.66,
                (SanitationSRTableEntry entry) -> {return entry.sanitationType;}
        );
    }
}
