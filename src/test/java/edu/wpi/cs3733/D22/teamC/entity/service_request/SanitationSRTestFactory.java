package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;

public class SanitationSRTestFactory extends ServiceRequestTestFactory<SanitationSR> {
    public SanitationSR create() {
        SanitationSR sanitationSR = new SanitationSR();

        SanitationSR.SanitationType sanitationType = SanitationSR.SanitationType.values()[getGenerator().nextInt(4)];
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Sanitation;

        sanitationSR.setRequestType(requestType);
        sanitationSR.setSanitationType(sanitationType);

        return super.create(sanitationSR);
    }
}
