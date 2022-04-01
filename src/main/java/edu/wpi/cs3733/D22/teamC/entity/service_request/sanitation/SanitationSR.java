package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class SanitationSR extends ServiceRequest {
    protected SanitationType sanitationType;

    public enum SanitationType {
        General,            // 1
        Hazardous,          // 2
        Biohazard,          // 3
        Daily_Cleaning;     // 4
    }

    public SanitationType getSanitationType() {
        return sanitationType;
    }

    public void setSanitationType(SanitationType sanitationType) {
        this.sanitationType = sanitationType;
    }

    //Not useful function, still implemented in controllers
//    public int getSanitationTypeEnum(String sanitationType) {
//        SanitationType number;
//
//        if(sanitationType.contains("General")) {
//            number = SanitationType.General;
//        }
//        else if(sanitationType.contains("Hazardous")) {
//            number = SanitationType.Hazardous;
//        }
//        else if(sanitationType.contains("Biohazard")) {
//            number = SanitationType.Biohazard;
//        }
//        else {
//            number = SanitationType.Daily_Cleaning;
//        }
//
//        switch(number) {
//            case General:
//                return 1;
//            case Hazardous:
//                return 2;
//            case Biohazard:
//                return 3;
//            case Daily_Cleaning:
//                return 4;
//            default:
//                return -1;
//        }
//    }
}
