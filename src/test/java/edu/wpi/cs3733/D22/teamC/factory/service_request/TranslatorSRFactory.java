package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;

public class TranslatorSRFactory extends ServiceRequestFactory<TranslatorSR>{
    public TranslatorSR create(){
        TranslatorSR translatorSR = new TranslatorSR();

        TranslatorSR.Language language = TranslatorSR.Language.values()[generator.nextInt(TranslatorSR.Language.values().length)];
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Translator;
    
        Patient testPatient = new Patient();
        PatientDAO patientDAO = new PatientDAO();
        patientDAO.insert(testPatient);
        
        translatorSR.setRequestType(requestType);
        translatorSR.setLanguage(language);
        translatorSR.setPatient(testPatient);

        return super.create(translatorSR);
    }
}
