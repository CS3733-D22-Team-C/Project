package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class SanitationServiceRequest extends ServiceRequest {
    protected String sanitationType;    // TODO: Make Enum

    public String getSanitationType() {
        return sanitationType;
    }

    public void setSanitationType(String sanitationType) {
        this.sanitationType = sanitationType;
    }
}
