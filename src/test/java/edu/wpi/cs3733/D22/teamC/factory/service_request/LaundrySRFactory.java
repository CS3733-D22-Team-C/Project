package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;

public class LaundrySRFactory extends ServiceRequestFactory<LaundrySR> {
    public LaundrySR create() {

        LaundrySR serviceRequest = new LaundrySR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Laundry;
        LaundrySR.LaundryType laundryType = LaundrySR.LaundryType.values()[generator.nextInt(LaundrySR.LaundryType.values().length)];
        Integer quantity = 2;

        serviceRequest.setRequestType(requestType);
        serviceRequest.setLaundryType(laundryType);
        serviceRequest.setQuantity(quantity);

        return super.create(serviceRequest);

    }
}

