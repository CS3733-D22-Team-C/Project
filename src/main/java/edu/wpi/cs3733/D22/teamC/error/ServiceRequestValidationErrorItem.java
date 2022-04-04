package edu.wpi.cs3733.D22.teamC.error;

public class ServiceRequestValidationErrorItem {
    private String reasonForValidationError;
    private int errorNumber;

    public ServiceRequestValidationErrorItem(int errorNumber, String reasonForValidationError)
    {
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


