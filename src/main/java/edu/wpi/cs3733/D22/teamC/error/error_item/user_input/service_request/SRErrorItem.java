package edu.wpi.cs3733.D22.teamC.error.error_item.user_input.service_request;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;

public class SRErrorItem extends ErrorItem {

    public SRErrorItem(int errorNumber, String reasonForValidationError)
    {
        super("User Input Validation", errorNumber, reasonForValidationError);
    }

}


