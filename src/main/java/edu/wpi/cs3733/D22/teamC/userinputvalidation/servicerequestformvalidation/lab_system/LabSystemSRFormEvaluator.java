package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.lab_system;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class LabSystemSRFormEvaluator extends ServiceRequestFormEvaluator {

    public LabSystemSRFormEvaluator() {}

    public int getLabSystemSRFormValidationChecksResult(String ID, String location)
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
