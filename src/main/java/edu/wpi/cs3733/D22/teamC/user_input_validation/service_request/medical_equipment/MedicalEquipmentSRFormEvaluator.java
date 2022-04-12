package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class MedicalEquipmentSRFormEvaluator extends SRFormEvaluator {

    public MedicalEquipmentSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getMedicalEquipmentSRFormValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel equipType, SingleSelectionModel equipID)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkEquipmentIDFilled(equipID));
        errorList.add(checkEquipmentTypeFilled(equipType));

        return errorList;
    }

    /**
     * Determine if the Equipment Type of Medical Equipment Service Request has been filled
     * @param equipType
     * @return ServiceRequestUserInputValidationErrorItem
     */
   public ServiceRequestUserInputValidationErrorItem checkEquipmentTypeFilled(SingleSelectionModel equipType)
   {
       if(equipType.isEmpty())
       {
           return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[8];
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
   public ServiceRequestUserInputValidationErrorItem checkEquipmentIDFilled(SingleSelectionModel equipID)
   {
       if(equipID.isEmpty())
       {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[9];
       }
       else
       {
           return null;
       }
   }

}
