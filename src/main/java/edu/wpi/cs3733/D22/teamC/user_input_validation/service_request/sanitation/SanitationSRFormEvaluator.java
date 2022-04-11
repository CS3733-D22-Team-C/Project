package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class SanitationSRFormEvaluator extends ServiceRequestFormEvaluator {

    public SanitationSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getSanitationSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel sanitationType)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkSanitationTypeFilled(sanitationType));

        return errorList;
    }

    /**
     * Determine if the Sanitation Type of Sanitation Service Request has been filled
     * @param sanitationType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSanitationTypeFilled(SingleSelectionModel sanitationType)
    {
        if(sanitationType.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[13];
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
