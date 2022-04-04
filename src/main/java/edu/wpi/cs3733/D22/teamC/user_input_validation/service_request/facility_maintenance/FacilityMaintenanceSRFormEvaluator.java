package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import java.util.ArrayList;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getFacilityMaintenanceSRValidationTestResult(int locationID, int assigneeID, String status, String priority, String maintenanceType)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> list = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        return null;
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateAssigneeIDResult(int assigneeID) {
        return super.getValidateAssigneeIDResult(assigneeID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateLocationIDResult(int locationID) {
        return super.getValidateLocationIDResult(locationID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    /**
     * Determine if the Maintenance Type of Facility Maintenance Service Request is filled
     * @param maintenanceType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkMaintenanceTypeFilled(String maintenanceType)
    {
        if(maintenanceType.length() == 0 && maintenanceType == null)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[6];
        }
        else
        {
            return null;
        }
    }
}
