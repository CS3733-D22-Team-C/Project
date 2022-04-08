package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "SANITATION_SR")
public class SanitationSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
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
