package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation.ServiceRequestFormEvaluator;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicineDeliverySRFormEvaluator() {}

    public int getMedicineDeliverySRFormValidationChecksResult(String ID, String location, String patientID, String medicine, String dosage)
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
