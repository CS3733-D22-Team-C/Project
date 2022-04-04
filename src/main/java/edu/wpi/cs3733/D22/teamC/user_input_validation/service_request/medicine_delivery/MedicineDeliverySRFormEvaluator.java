package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicineDeliverySRFormEvaluator() {}

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    public ServiceRequestUserInputValidationErrorItem checkPatientIDFilled(int patientID)
    {
        return null;
    }

    public ServiceRequestUserInputValidationErrorItem checkMedicineFilled(String medicine)
    {
        return null;
    }

    public ServiceRequestUserInputValidationErrorItem checkDosageFilled(String dosage)
    {
        return null;
    }
}
