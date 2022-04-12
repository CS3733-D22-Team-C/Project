package edu.wpi.cs3733.D22.teamC.error.error_item.service_request;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;

public class SRErrorItem extends ErrorItem {
    private String reasonForValidationError;

    public SRErrorItem(int errorNumber, String reasonForValidationError)
    {
        super("User Input Validation", errorNumber);
        this.reasonForValidationError = reasonForValidationError;
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


