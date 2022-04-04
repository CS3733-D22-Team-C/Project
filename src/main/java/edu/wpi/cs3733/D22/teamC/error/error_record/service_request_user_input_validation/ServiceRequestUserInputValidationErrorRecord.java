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
            new ServiceRequestUserInputValidationErrorItem(7, "Maintenance Type needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(8, "Lab Type needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(9, "Patient ID needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(10, "Equipment Type needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(11, "Equipment ID needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(12, "Patient ID needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(13, "Medicine needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(14, "Dosage needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(15, "Sanitation Type needs to be filled"),
            new ServiceRequestUserInputValidationErrorItem(16, "Security Type needs to be filled")
};

}
