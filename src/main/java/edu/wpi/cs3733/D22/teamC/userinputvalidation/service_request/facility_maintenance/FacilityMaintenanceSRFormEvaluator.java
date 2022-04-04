package edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.error.error_item.ServiceRequestValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.userinputvalidation.service_request.ServiceRequestFormEvaluator;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    private static final ServiceRequestValidationErrorItem[] facilityMaintenanceServiceRequestValidationErrorItemList = {
       new ServiceRequestValidationErrorItem(7, "Maintenance Type needs to be filled")
            };

    public FacilityMaintenanceSRFormEvaluator() {}

    @Override
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

    @Override
    public ServiceRequestValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    public ServiceRequestValidationErrorItem checkMaintenanceTypeFilled(String maintenanceType)
    {
        if(maintenanceType.length() == 0)
        {

        }
        return null;
    }
}
