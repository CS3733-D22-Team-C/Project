package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class SanitationSR extends ServiceRequest {
    protected SanitationType sanitationType;

    public enum SanitationType {
        General,
        Hazardous,
        Biohazard,
        Daily_Cleaning
    }

    public SanitationSR() {}
    public SanitationSR(ServiceRequest serviceRequest) {super(serviceRequest);}

    public SanitationType getSanitationType() {
        return sanitationType;
    }

    public void setSanitationType(SanitationType sanitationType) {
        this.sanitationType = sanitationType;
    }
}
