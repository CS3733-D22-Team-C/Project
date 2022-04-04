package edu.wpi.cs3733.D22.teamC.error.error_item;

public class ServiceRequestValidationErrorItem extends ErrorItem {
    private String reasonForValidationError;

    public ServiceRequestValidationErrorItem(int errorNumber, String reasonForValidationError)
    {
        this.errorType = "User Input Validation";
        this.reasonForValidationError = reasonForValidationError;
        this.errorNumber = errorNumber;
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


