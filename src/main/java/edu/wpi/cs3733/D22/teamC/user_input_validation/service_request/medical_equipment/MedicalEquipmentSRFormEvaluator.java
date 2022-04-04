package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

import java.util.ArrayList;

public class MedicalEquipmentSRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getMedicalEquipmentSRValidationTestResult(int locationID, int assigneeID, String status, String priority, String equipType, int equipID)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(locationID, assigneeID, status, priority));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(locationID));
        errorList.add(checkEquipmentIDFilled(equipID));
        errorList.add(checkEquipmentTypeFilled(equipType));

        return errorList;
    }

    /**
     * Determine if the Equipment Type of Medical Equipment Service Request has been filled
     * @param equipType
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public ServiceRequestUserInputValidationErrorItem checkEquipmentTypeFilled(String equipType)
   {
       if(equipType.isEmpty())
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
