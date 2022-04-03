package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestValidationErrorItem;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicineDeliverySRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }
}
