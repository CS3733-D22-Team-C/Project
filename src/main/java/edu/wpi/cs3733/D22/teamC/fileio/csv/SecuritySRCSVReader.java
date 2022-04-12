package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;

public class SecuritySRCSVReader extends CSVReader<SecuritySR> {
    @Override
    protected SecuritySR parseAttribute(SecuritySR object, String header, String value) {
        return null;
    }

    @Override
    protected SecuritySR createObject() {
        return null;
    }
}
