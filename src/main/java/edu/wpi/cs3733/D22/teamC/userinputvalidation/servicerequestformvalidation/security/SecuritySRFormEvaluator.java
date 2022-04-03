package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.security;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class SecuritySRFormEvaluator extends ServiceRequestFormEvaluator {

    public SecuritySRFormEvaluator()
    {}

    @Override
    public int getValidateAssigneeIDResult(String ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public int getValidateLocationIDResult(String location) {
        return super.getValidateLocationIDResult(location);
    }
}
