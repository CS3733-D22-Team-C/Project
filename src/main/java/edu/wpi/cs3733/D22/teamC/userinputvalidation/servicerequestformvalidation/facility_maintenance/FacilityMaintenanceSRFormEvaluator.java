package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    public int getFacilityMaintenanceSRFormValidationChecksResult(String ID, String location, String maintenanceType)
    {
        int validationResult = 0;
        return 0;
    }

    @Override
    public int getValidateAssigneeIDResult(String ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public int getValidateLocationIDResult(String location) {
        return super.getValidateLocationIDResult(location);
    }
}
