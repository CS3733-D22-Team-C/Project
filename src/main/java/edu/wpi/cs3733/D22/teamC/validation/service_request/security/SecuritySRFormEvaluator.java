package edu.wpi.cs3733.D22.teamC.validation.service_request.security;

import edu.wpi.cs3733.D22.teamC.error.error_record.service_request.SRErrorRecord;
import edu.wpi.cs3733.D22.teamC.validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class SecuritySRFormEvaluator extends SRFormEvaluator {

    public SecuritySRFormEvaluator()
    {}

    public ArrayList<SRErrorItem> getSecuritySRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel securityType)
    {
        ArrayList <SRErrorItem> errorList = new ArrayList <SRErrorItem> ();

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
    public SRErrorItem checkSecurityTypeFilled(SingleSelectionModel securityType)
    {
        if(securityType.isEmpty())
        {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[14];
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
