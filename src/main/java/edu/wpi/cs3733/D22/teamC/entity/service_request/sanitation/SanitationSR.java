package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class SanitationSR extends ServiceRequest {
    protected String sanitationType;    // TODO: Make Enum

    enum sanitationEnum {
        GENERAL,    // 1
        HAZARDOUS,  // 2
        BIOHAZARD,  // 3
        DAILY       // 4
    }

    public String getSanitationType() {
        return sanitationType;
    }

    public void setSanitationType(String sanitationType) {
        this.sanitationType = sanitationType;
    }

    public int getSanitationTypeEnum(String sanitationType) {
        sanitationEnum number;

        if(sanitationType.contains("General")) {
            number = sanitationEnum.GENERAL;
        }
        else if(sanitationType.contains("Hazardous")) {
            number = sanitationEnum.HAZARDOUS;
        }
        else if(sanitationType.contains("Biohazard")) {
            number = sanitationEnum.BIOHAZARD;
        }
        else {
            number = sanitationEnum.DAILY;
        }

        switch(number) {
            case GENERAL:
                return 1;
            case HAZARDOUS:
                return 2;
            case BIOHAZARD:
                return 3;
            case DAILY:
                return 4;
            default:
                return -1;
        }
    }
}
