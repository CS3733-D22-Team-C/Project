package edu.wpi.cs3733.D22.teamC.error.error_item;

public class ErrorItem {
    protected String errorType;
    protected int errorID;
    protected String reasonForValidationError;

    public ErrorItem(String errorType, int errorID, String reasonForValidationError) {
        this.errorType = errorType;
        this.errorID = errorID;
        this.reasonForValidationError = reasonForValidationError;
    }

    public String getErrorType() {
        return errorType;
    }

    public int getErrorNumber() {
        return errorID;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorID = errorNumber;
    }

    public String getReasonForValidationError() {
        return reasonForValidationError;
    }

    public void setReasonForValidationError(String reasonForValidationError) {
        this.reasonForValidationError = reasonForValidationError;
    }
}
