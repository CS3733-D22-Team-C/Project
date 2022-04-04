package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.security;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class SecuritySRFormEvaluator extends ServiceRequestFormEvaluator {

    public SecuritySRFormEvaluator()
    {}



    /**
     * Determine if the Security Type of SecurityServiceRequest has been filled
     * @param securityType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSecurityTypeFilled(String securityType)
    {
        if(securityType.length() == 0 && securityType == null)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[15];
        }
        else
        {
            return null;
        }
    }
}
