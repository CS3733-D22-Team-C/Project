package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.medical_equipment;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestValidationErrorItem;

public class MedicalEquipmentSRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }
}
