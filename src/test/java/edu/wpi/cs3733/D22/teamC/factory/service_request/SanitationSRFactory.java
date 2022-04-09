package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

public class SanitationSRFactory extends ServiceRequestFactory<SanitationSR>  {
    public SanitationSR create() {
        SanitationSR sanitationSR = new SanitationSR();

        SanitationSR.SanitationType sanitationType = SanitationSR.SanitationType.values()[generator.nextInt(SanitationSR.SanitationType.values().length)];
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Sanitation;

        sanitationSR.setRequestType(requestType);
        sanitationSR.setSanitationType(sanitationType);

        return super.create(sanitationSR);
    }
}
