package edu.wpi.cs3733.D22.teamC.error.userinputerrorrecord;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_item.ServiceRequestValidationErrorItem;

public class ApplicationUserInputErrorRecord {
    public static final ErrorItem[] applicationErrorList = {
            new ServiceRequestValidationErrorItem(1, "Required field Location ID is missing"),
            new ServiceRequestValidationErrorItem(2, "Required field AssigneeID is missing"),
            new ServiceRequestValidationErrorItem(3, "Required field Status is missing"),
            new ServiceRequestValidationErrorItem(4, "Required field Priority is missing"),
            new ServiceRequestValidationErrorItem(5, "Assignee ID does not exist"),
            new ServiceRequestValidationErrorItem(6, "Location ID does not exist"),
            new ServiceRequestValidationErrorItem(7, "Maintenance Type needs to be filled"),
            new ServiceRequestValidationErrorItem(8, "Lab Type needs to be filled"),
            new ServiceRequestValidationErrorItem(9, "Patient ID needs to be filled"),
            new ServiceRequestValidationErrorItem(10, "Equipment Type needs to be filled"),
            new ServiceRequestValidationErrorItem(11, "Equipment ID needs to be filled"),
            new ServiceRequestValidationErrorItem(12, "Patient ID needs to be filled"),
            new ServiceRequestValidationErrorItem(13, "Medicine needs to be filled"),
            new ServiceRequestValidationErrorItem(14, "Dosage needs to be filled"),
            new ServiceRequestValidationErrorItem(15, "Sanitation Type needs to be filled"),
            new ServiceRequestValidationErrorItem(16, "Security Type needs to be filled")
};

}
