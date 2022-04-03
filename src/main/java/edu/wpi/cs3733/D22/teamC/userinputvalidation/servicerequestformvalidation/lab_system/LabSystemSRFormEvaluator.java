package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.lab_system;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestValidationErrorItem;

public class LabSystemSRFormEvaluator extends ServiceRequestFormEvaluator {

    public LabSystemSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

}
