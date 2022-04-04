package edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.error.error_item.ServiceRequestValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.ServiceRequestFormEvaluator;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    private static final ServiceRequestValidationErrorItem[] medicineDeliveryServiceRequestValidationErrorItemList = {
            new ServiceRequestValidationErrorItem(12, "Patient ID needs to be filled"),
            new ServiceRequestValidationErrorItem(13, "Medicine needs to be filled"),
            new ServiceRequestValidationErrorItem(14, "Dosage needs to be filled")
    };

    public MedicineDeliverySRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

    @Override
    public ServiceRequestValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    public ServiceRequestValidationErrorItem checkPatientIDFilled(int patientID)
    {
        return null;
    }

    public ServiceRequestValidationErrorItem checkMedicineFilled(String medicine)
    {
        return null;
    }

    public ServiceRequestValidationErrorItem checkDosageFilled(String dosage)
    {
        return null;
    }
}
