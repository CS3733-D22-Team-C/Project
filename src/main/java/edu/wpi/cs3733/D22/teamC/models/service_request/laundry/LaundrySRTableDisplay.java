package edu.wpi.cs3733.D22.teamC.models.service_request.laundry;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LaundrySRTableDisplay extends ServiceRequestTableDisplay<LaundrySR> {
    public class LaundrySRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty laundryType;

        public StringProperty quantity;

        public LaundrySRTableEntry(LaundrySR laundrySR) {
            super(laundrySR);

            this.laundryType = new SimpleStringProperty(laundrySR.getLaundryType().toString());
            this.quantity = new SimpleStringProperty(laundrySR.getQuantity().toString());
        }
    }

    public LaundrySRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(LaundrySR object) {
        addEntry(new LaundrySRTableDisplay.LaundrySRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Laundry Type",
                1f * Integer.MAX_VALUE * 16.66,
                (LaundrySRTableDisplay.LaundrySRTableEntry entry) -> {return entry.laundryType;}
        );
        addColumn(
                table,
                "Quantity",
                1f * Integer.MAX_VALUE * 16.66,
                (LaundrySRTableDisplay.LaundrySRTableEntry entry) -> {return entry.quantity;}
        );
    }
}

