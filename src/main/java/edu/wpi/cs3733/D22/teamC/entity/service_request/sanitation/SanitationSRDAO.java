package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling SanitationSRs
 */
public class SanitationSRDAO extends DAO<SanitationSR> {

    @Override
    protected Class<SanitationSR> classType() {
        return SanitationSR.class;
    }
}
