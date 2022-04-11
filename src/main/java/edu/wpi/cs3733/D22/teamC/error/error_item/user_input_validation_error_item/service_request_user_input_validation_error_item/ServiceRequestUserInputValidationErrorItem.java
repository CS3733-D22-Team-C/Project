package edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;

public class ServiceRequestUserInputValidationErrorItem extends ErrorItem {


    public ServiceRequestUserInputValidationErrorItem(int errorNumber, String reasonForValidationError)
    {
        super("User Input Validation", errorNumber, reasonForValidationError);
    }

    public String getReasonForValidationError() {
        return reasonForValidationError;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setReasonForValidationError(String reasonForValidationError) {
        this.reasonForValidationError = reasonForValidationError;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }
}


