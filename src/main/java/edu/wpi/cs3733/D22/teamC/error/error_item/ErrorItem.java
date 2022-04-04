package edu.wpi.cs3733.D22.teamC.error.error_item;

public class ErrorItem {
    protected String errorType;
    protected int errorNumber;

    public ErrorItem(String errorType, int errorNumber) {
        this.errorType = errorType;
        this.errorNumber = errorNumber;
    }

    public String getErrorType() {
        return errorType;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }
}
