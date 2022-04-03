package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.sanitation;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class SanitationSRFormEvaluator extends ServiceRequestFormEvaluator {

    public SanitationSRFormEvaluator() {}

    @Override
    public int getValidateAssigneeIDResult(String ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public int getValidateLocationIDResult(String location) {
        return super.getValidateLocationIDResult(location);
    }
}
