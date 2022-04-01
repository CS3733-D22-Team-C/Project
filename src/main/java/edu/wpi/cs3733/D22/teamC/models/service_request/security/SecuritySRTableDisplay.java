package edu.wpi.cs3733.D22.teamC.models.service_request.security;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SecuritySRTableDisplay extends ServiceRequestTableDisplay<SecuritySR> {
    public class SecuritySRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty securityType;

        public SecuritySRTableEntry(SecuritySR medicineDeliverySR) {
            super(medicineDeliverySR);

            this.securityType = new SimpleStringProperty(medicineDeliverySR.getSecurityType());
        }
    }

    public SecuritySRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(SecuritySR object) {
        addEntry(new SecuritySRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Security Type",
                1f * Integer.MAX_VALUE * 16.66,
                (SecuritySRTableEntry entry) -> {return entry.securityType;}
        );
    }
}
