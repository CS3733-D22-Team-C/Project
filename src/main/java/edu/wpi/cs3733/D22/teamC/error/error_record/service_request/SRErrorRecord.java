package edu.wpi.cs3733.D22.teamC.error.error_record.service_request;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request.SRErrorItem;

public class SRErrorRecord {
    public static final SRErrorItem[] serviceRequestUserInputValidationErrorList = {
            new SRErrorItem(1, "Required field Location ID is missing"),
            new SRErrorItem(2, "Required field Status is missing"),
            new SRErrorItem(3, "Required field Priority is missing"),
            new SRErrorItem(4, "Assignee ID does not exist"),
            new SRErrorItem(5, "Location ID does not exist"),
            new SRErrorItem(6, "Required field Maintenance Type is missing"),
            new SRErrorItem(7, "Required field Lab Type is missing"),
            new SRErrorItem(8, "Required field Patient ID is missing"),
            new SRErrorItem(9, "Required field Equipment Type is missing"),
            new SRErrorItem(10, "Required field Equipment ID is missing"),
            new SRErrorItem(11, "Required field Patient ID is missing"),
            new SRErrorItem(12, "Required field Medicine is missing"),
            new SRErrorItem(13, "Required field Dosage is missing"),
            new SRErrorItem(14, "Required field Sanitation Type is missing"),
            new SRErrorItem(15, "Required field Security Type is missing")
    };

}
