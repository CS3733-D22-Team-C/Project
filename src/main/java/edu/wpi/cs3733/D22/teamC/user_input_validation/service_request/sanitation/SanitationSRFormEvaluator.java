package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class SanitationSRFormEvaluator extends ServiceRequestFormEvaluator {

    public SanitationSRFormEvaluator() {}



    /**
     * Determine if the Sanitation Type of Sanitation Service Request has been filled
     * @param sanitationType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSanitationTypeFilled(String sanitationType)
    {
        return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[14];
    }

}
