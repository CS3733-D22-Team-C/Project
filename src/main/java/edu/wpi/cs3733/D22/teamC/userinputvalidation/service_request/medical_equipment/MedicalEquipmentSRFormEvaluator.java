package edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.ServiceRequestValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.ServiceRequestFormEvaluator;

public class MedicalEquipmentSRFormEvaluator extends ServiceRequestFormEvaluator {

    private static final ServiceRequestValidationErrorItem[] medicalEquipmentServiceRequestValidationErrorItemList = {
                    new ServiceRequestValidationErrorItem(10, "Equipment Type needs to be filled"),
                    new ServiceRequestValidationErrorItem(11, "Equipment ID needs to be filled")
            };

    public MedicalEquipmentSRFormEvaluator() {}

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

   public ServiceRequestValidationErrorItem checkEquipmentTypeFilled(String equipType)
   {
        return null;
   }

   public ServiceRequestValidationErrorItem checkEquipmentIDFilled(String equipID)
   {
        return null;
   }
}
