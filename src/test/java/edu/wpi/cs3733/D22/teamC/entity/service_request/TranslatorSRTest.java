package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.ServiceRequestFactory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.TranslatorSRFactory;

public class TranslatorSRTest extends DAOTest<TranslatorSR> {
    public TranslatorSRTest() {
        super(new TranslatorSRFactory(), new TranslatorSRDAO());
    }
}
