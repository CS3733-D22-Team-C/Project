package edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation;

//I was thinking of creating an ErrorItem superclass has well, but this would involve some downcasting when returning ErrorItems in order for the inheritance to make sense, which is risky and probably not necessary.

public class ServiceRequestUserInputValidationErrorItem {
    private String reasonForValidationError;
    private int errorNumber;

    public ServiceRequestUserInputValidationErrorItem(int errorNumber, String reasonForValidationError)
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


