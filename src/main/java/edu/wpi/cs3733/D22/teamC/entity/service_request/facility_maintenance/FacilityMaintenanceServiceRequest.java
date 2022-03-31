package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;

public class FacilityMaintenanceServiceRequest extends ServiceRequest {
    protected String maintenanceType;   // TODO: Make Enum

    //For the table
    enum maintenanceEnum {
        CLEANING, //1
        ORGANIZING; //2
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        if(maintenanceType.contains("Cleaning"))
        {
            this.maintenanceType = "Cleaning";
        }
        else if(maintenanceType.contains("Organizing"))
        {
            this.maintenanceType = "Organizing";
        }
    }

    public int getMaintenanceTypeEnum(String type)
    {
        maintenanceEnum number = null;

        if(type.contains("Cleaning"))
       {
           number = maintenanceEnum.CLEANING;
       }
       else if(type.contains("Organizing"))
       {
           number = maintenanceEnum.ORGANIZING;
       }

       switch(number)
       {
           case CLEANING:
                return 1;
           case ORGANIZING:
               return 2;
           default:
               return -1;
       }
    }


}
