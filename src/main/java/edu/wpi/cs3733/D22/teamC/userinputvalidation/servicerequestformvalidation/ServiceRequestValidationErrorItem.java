package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation;

public class ServiceRequestValidationErrorItem {
    private String reasonForValidationError;
    private int errorNumber;

    public ServiceRequestValidationErrorItem(String reasonForValidationError, int errorNumber)
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


