package edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FacilityMaintenanceSRTableDisplay extends ServiceRequestTableDisplay<FacilityMaintenanceSR> {
    public class FacilityMaintenanceSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty maintenanceType;

        public FacilityMaintenanceSRTableEntry(FacilityMaintenanceSR facilityMaintenanceSR) {
            super(facilityMaintenanceSR);

            this.maintenanceType = new SimpleStringProperty(facilityMaintenanceSR.getMaintenanceType());
        }
    }

    public FacilityMaintenanceSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(FacilityMaintenanceSR object) {
        addEntry(new FacilityMaintenanceSRTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);

        addColumn(
                table,
                "Maintenance Type",
                1f * Integer.MAX_VALUE * 16.66,
                (FacilityMaintenanceSRTableEntry entry) -> {return entry.maintenanceType;}
        );
    }
}
