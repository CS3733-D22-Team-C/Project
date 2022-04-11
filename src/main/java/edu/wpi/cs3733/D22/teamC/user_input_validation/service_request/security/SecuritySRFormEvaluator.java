package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.security;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class SecuritySRFormEvaluator extends ServiceRequestFormEvaluator {

    public SecuritySRFormEvaluator()
    {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getSecuritySRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel securityType)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkSecurityTypeFilled(securityType));

        return errorList;
    }

    /**
     * Determine if the Security Type of SecurityServiceRequest has been filled
     * @param securityType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSecurityTypeFilled(SingleSelectionModel securityType)
    {
        if(securityType.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[14];
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean noServiceRequestFormUserInputErrors(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        return super.noServiceRequestFormUserInputErrors(l);
    }
}
