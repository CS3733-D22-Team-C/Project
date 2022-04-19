package edu.wpi.cs3733.D22.teamC.entity.service_request.translator;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class TranslatorSRDAO extends DAO<TranslatorSR> {

    @Override
    protected Class<TranslatorSR> classType() {
        return TranslatorSR.class;
    }

}
