package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class FacilityMaintenanceFormEvaluator extends ServiceRequestFormEvaluator {

    protected String maintenanceType;

    public FacilityMaintenanceFormEvaluator() {}

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        if(maintenanceType.contains("Cleaning"))
        {
            this.maintenanceType = "Cleaning";
        }
        else if(maintenanceType.contains("Organizing"))
        {
            this.maintenanceType = "Organizing";
        }
    }

    @Override
    public int validateAssigneeID() {
        return super.validateAssigneeID();
    }

    @Override
    public int validateLocationID() {
        return super.validateLocationID();
    }
}
