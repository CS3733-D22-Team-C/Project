package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.medical_equipment;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class MedicalEquipmentSRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

    public int getMedicalEquipmentSRFormValidationChecksResult(String ID, String location, String equipmentID)
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
