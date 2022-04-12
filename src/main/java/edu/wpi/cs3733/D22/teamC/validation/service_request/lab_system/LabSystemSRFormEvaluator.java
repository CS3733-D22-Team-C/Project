package edu.wpi.cs3733.D22.teamC.validation.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.service_request.SRErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request.SRErrorRecord;
import edu.wpi.cs3733.D22.teamC.validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class LabSystemSRFormEvaluator extends SRFormEvaluator {

    public LabSystemSRFormEvaluator() {}

    public ArrayList<SRErrorItem> getLabSystemSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel labType, String patientID)
    {
        ArrayList <SRErrorItem> errorList = new ArrayList <SRErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkLabTypeFilled(labType));
        errorList.add(checkPatientIDFilled(patientID));

        return errorList;
    }

    /**
     * Determine if the Lab Type of Lab System Service Request has been filled
     * @param labType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public SRErrorItem checkLabTypeFilled(SingleSelectionModel labType)
    {
        if(labType.isEmpty())
        {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[6];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the Patient ID of Lab System Service Request has been filled.
     * @param patientID
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public SRErrorItem checkPatientIDFilled(String patientID)
    {

        if(patientID.isEmpty())
        {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[10];
        }
        else
        {
            int patientIDConv = Integer.parseInt(patientID);
            int patientIDLength = (int)(Math.log10(patientIDConv)+1);

            if(patientIDLength == 0)
            {
                return SRErrorRecord.serviceRequestUserInputValidationErrorList[10];
            }
            else
            {
                return null;
            }
        }
    }

    @Override
    public boolean noServiceRequestFormUserInputErrors(ArrayList<SRErrorItem> l) {
        return super.noServiceRequestFormUserInputErrors(l);
    }
}
