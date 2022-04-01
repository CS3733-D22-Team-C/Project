package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

public class ServiceRequestFormEvaluator {

    protected String requestID;
    protected String location;

    public ServiceRequestFormEvaluator() {}

    public ServiceRequestFormEvaluator(ServiceRequestFormEvaluator srfe)
    {
        this.requestID = srfe.getRequestID();
        this.location = srfe.getLocation();
    }

    public int getValidateAssigneeIDResult(String ID)
    {
        return 0;
    }

    public int getValidateLocationIDResult(String location)
    {
        return 0;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getLocation() {
        return location;
    }
}
