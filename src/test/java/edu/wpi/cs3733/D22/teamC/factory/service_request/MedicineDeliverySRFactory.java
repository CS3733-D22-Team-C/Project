package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;

public class MedicineDeliverySRFactory extends ServiceRequestFactory<MedicineDeliverySR>  {
    public MedicineDeliverySR create() {

        MedicineDeliverySR serviceRequest = new MedicineDeliverySR();

        String medicine = "methamphetamine HCL";
        String dosage = "18mg oral";
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medicine_Delivery;
    
        Patient testPatient = new Patient();
        PatientDAO patientDAO = new PatientDAO();
        patientDAO.insert(testPatient);

        serviceRequest.setRequestType(requestType);
        serviceRequest.setMedicine(medicine);
        serviceRequest.setDosage(dosage);
        serviceRequest.setPatient(testPatient);

        return super.create(serviceRequest);
    }
}
