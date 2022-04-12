package edu.wpi.cs3733.D22.teamC.validation.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.error.error_record.service_request.SRErrorRecord;
import edu.wpi.cs3733.D22.teamC.validation.service_request.SRFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class FacilityMaintenanceSRFormEvaluator extends SRFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    public ArrayList<SRErrorItem> getFacilityMaintenanceSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel maintenanceType)
    {
        ArrayList <SRErrorItem> errorList = new ArrayList <SRErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkMaintenanceTypeFilled(maintenanceType));

        return errorList;
    }

    /**
     * Determine if the Maintenance Type of Facility Maintenance Service Request is filled
     * @param maintenanceType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public SRErrorItem checkMaintenanceTypeFilled(SingleSelectionModel maintenanceType)
    {
        if(maintenanceType.isEmpty())
        {
            return SRErrorRecord.serviceRequestUserInputValidationErrorList[5];
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
