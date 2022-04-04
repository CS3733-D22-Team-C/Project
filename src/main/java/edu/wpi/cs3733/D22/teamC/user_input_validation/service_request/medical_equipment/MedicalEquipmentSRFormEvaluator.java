package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class MedicalEquipmentSRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

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

    /**
     * Determine if the Equipment Type of Medical Equipment Service Request has been filled
     * @param equipType
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public ServiceRequestUserInputValidationErrorItem checkEquipmentTypeFilled(String equipType)
   {
       if(equipType.length() == 0 && equipType == null)
       {
           return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[9];
       }
       else
       {
           return null;
       }
   }

    /**
     * Determine if the Equipment ID of a Medical Equipment Service Request has been filled
     * @param equipID
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public ServiceRequestUserInputValidationErrorItem checkEquipmentIDFilled(int equipID)
   {
       int equipIDLength = (int)(Math.log10(equipID)+1);

       if(equipIDLength == 0)
       {
           return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[10];
       }
       else
       {
           return null;
       }
   }
}
