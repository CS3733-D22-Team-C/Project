package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling LabSystemSRs
 */
public class LabSystemSRDAO extends DAO<LabSystemSR> {
    @Override
    protected Class<LabSystemSR> classType() {
        return LabSystemSR.class;
    }
}
