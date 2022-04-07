package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;

public class MedicineDeliverySRFactory extends ServiceRequestFactory<MedicineDeliverySR> {
    public MedicineDeliverySR create() {

        MedicineDeliverySR serviceRequest = new MedicineDeliverySR();

        String medicine = "methamphetamine HCL";
        String dosage = "18mg oral";
        String patientID = "JohnCena";
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medicine_Delivery;

        serviceRequest.setRequestType(requestType);
        serviceRequest.setMedicine(medicine);
        serviceRequest.setDosage(dosage);
        serviceRequest.setPatientID(patientID);

        return super.create(serviceRequest);
    }
}
