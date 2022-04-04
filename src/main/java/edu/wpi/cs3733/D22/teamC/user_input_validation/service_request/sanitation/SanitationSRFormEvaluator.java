package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

import java.util.ArrayList;

public class SanitationSRFormEvaluator extends ServiceRequestFormEvaluator {

    public SanitationSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getSanitationSRValidationTestResult(int locationID, int assigneeID, String status, String priority, String sanitationType)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(locationID, assigneeID, status, priority));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(locationID));
        errorList.add(checkSanitationTypeFilled(sanitationType));

        return errorList;
    }

    /**
     * Determine if the Sanitation Type of Sanitation Service Request has been filled
     * @param sanitationType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSanitationTypeFilled(String sanitationType)
    {
        if(sanitationType.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[14];
        }
        else
        {
            return null;
        }
    }

}
