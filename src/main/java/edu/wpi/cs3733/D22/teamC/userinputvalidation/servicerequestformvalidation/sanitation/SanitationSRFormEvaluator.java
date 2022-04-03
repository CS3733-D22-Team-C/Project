package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.sanitation;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestValidationErrorItem;

public class SanitationSRFormEvaluator extends ServiceRequestFormEvaluator {

    public SanitationSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }
}
