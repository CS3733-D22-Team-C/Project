package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class FacilityMaintenanceSR extends ServiceRequest {
    protected MaintenanceType maintenanceType;   // TODO: Make Enum

    //For the table
    public enum MaintenanceType {
        Cleaning,   //0
        Organizing; //1
    } //TODO make more enum types

    public MaintenanceType getMaintenanceType() {return maintenanceType;}

    public void setMaintenanceType(MaintenanceType maintenanceType) {this.maintenanceType = maintenanceType;
//        if(maintenanceType.contains("Cleaning"))
//        {
//            this.maintenanceType = maintenanceType;
//        }
//        else if(maintenanceType.contains("Organizing"))
//        {
//            this.maintenanceType = maintenanceType;
//        }
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
