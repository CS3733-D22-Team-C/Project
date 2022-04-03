package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestValidationErrorItem;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }
}
