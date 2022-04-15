package edu.wpi.cs3733.D22.teamC.entity.service_request.laundry;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling LaundrySRs
 */

public class LaundrySRDAO extends DAO<LaundrySR> {

    @Override
    protected Class <LaundrySR> classType()
    {
        return LaundrySR.class;
    }
}
