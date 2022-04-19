package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;

public class TranslatorSRFactory extends ServiceRequestFactory<TranslatorSR>{
    public TranslatorSR create(){
        TranslatorSR translatorSR = new TranslatorSR();

        TranslatorSR.Language language = TranslatorSR.Language.values()[generator.nextInt(TranslatorSR.Language.values().length)];
        String patientID = String.valueOf(generator.nextInt(200000));
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Translator;

        translatorSR.setRequestType(requestType);
        translatorSR.setLanguage(language);
        translatorSR.setPatientID(patientID);

        return super.create(translatorSR);
    }
}
