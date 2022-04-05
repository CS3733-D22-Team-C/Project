package edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;

public class ServiceRequestUserInputValidationErrorRecord {
    public static final ServiceRequestUserInputValidationErrorItem[] serviceRequestUserInputValidationErrorList = {
            new ServiceRequestUserInputValidationErrorItem(1, "Required field Location ID is missing"),
            new ServiceRequestUserInputValidationErrorItem(2, "Required field AssigneeID is missing"),
            new ServiceRequestUserInputValidationErrorItem(3, "Required field Status is missing"),
            new ServiceRequestUserInputValidationErrorItem(4, "Required field Priority is missing"),
            new ServiceRequestUserInputValidationErrorItem(5, "Assignee ID does not exist"),
            new ServiceRequestUserInputValidationErrorItem(6, "Location ID does not exist"),
            new ServiceRequestUserInputValidationErrorItem(7, "Required field Maintenance Type is missing"),
            new ServiceRequestUserInputValidationErrorItem(8, "Required field Lab Type is missing"),
            new ServiceRequestUserInputValidationErrorItem(9, "Required field Patient ID is missing"),
            new ServiceRequestUserInputValidationErrorItem(10, "Required field Equipment Type is missing"),
            new ServiceRequestUserInputValidationErrorItem(11, "Required field Equipment ID is missing"),
            new ServiceRequestUserInputValidationErrorItem(12, "Required field Patient ID is missing"),
            new ServiceRequestUserInputValidationErrorItem(13, "Required field Medicine is missing"),
            new ServiceRequestUserInputValidationErrorItem(14, "Required field Dosage is missing"),
            new ServiceRequestUserInputValidationErrorItem(15, "Required field Sanitation Type is missing"),
            new ServiceRequestUserInputValidationErrorItem(16, "Required field Security Type is missing")
};

}
