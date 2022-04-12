package edu.wpi.cs3733.D22.teamC.validation.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.service_request.SRErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request.SRErrorRecord;
import edu.wpi.cs3733.D22.teamC.validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class SanitationSRFormEvaluator extends SRFormEvaluator {

    public SanitationSRFormEvaluator() {}

    public ArrayList<SRErrorItem> getSanitationSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel sanitationType)
    {
        ArrayList <SRErrorItem> errorList = new ArrayList <SRErrorItem> ();

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
    public SRErrorItem checkSanitationTypeFilled(SingleSelectionModel sanitationType)
    {
        if(sanitationType.isEmpty())
        {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[13];
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean noServiceRequestFormUserInputErrors(ArrayList<SRErrorItem> l) {
        return super.noServiceRequestFormUserInputErrors(l);
    }
}
