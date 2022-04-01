package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class FacilityMaintenanceSR extends ServiceRequest {
    protected String maintenanceType;   // TODO: Make Enum

    //For the table
    enum MaintenanceType {
        Cleaning,   //0
        Organizing; //1
    } //TODO make more enum types

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

    //Not useful function, still implemented in controllers
    public int getMaintenanceTypeEnum(String type)
    {
        MaintenanceType number = null;

        if(type.contains("Cleaning"))
       {
           number = MaintenanceType.Cleaning;
       }
       else if(type.contains("Organizing"))
       {
           number = MaintenanceType.Organizing;
       }

       switch(number)
       {
           case Cleaning:
                return 1;
           case Organizing:
               return 2;
           default:
               return -1;
       }
    }


}
