package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;

public class ServiceRequestFormEvaluator {

    public ServiceRequestFormEvaluator() {}

    /**
     * Determine if the assigneeID sent from a ServiceRequestCreateController object is valid through checking its length and if it exists in the Employee Table database.
     * If the assigneeID is too short, return 1. Else if the assigneeID does not exist in the Employee Table of the database, return 2. Else, return 0.
     * @param ID The assigneeID of the service request.
     * @return int.
     */
    public int getValidateAssigneeIDResult(String ID)
    {
        ServiceRequestDAOImpl serviceRequestDaoVar = new ServiceRequestDAOImpl();

        if(ID.length() < 10)
        {
            return 1; //Return 1 if the assigneeID is not the proper length
        }
        else
        {
            return 0;
        }

        /*
        else if() //Return 2 if the assigneeID is not in the database, waiting on EmployeeDAO
        {
            return 2;
        }
        */
    }

    /**
     *Determine if the location ID sent from a ServiceRequestCreateController object exists in the Location Table database.
     * If the location ID does not exist in the Location Table database, return 3. Else, return 0.
     * @param locationID The location ID of the service request
     * @return int
     */
    public int getValidateLocationIDResult(String locationID)
    {
        LocationDAOImpl locationDaoVar = new LocationDAOImpl();

        if(locationDaoVar.getLocation(locationID) == null) //assumes the user inputs a location ID in the service request location field.
        {
            return 3;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Determine if all required fields of a service request are filled. The required fields for all service requests
     * If all required fields are not filled, return 4. Else, return 0.
     * @return
     */
    public int getValidateAllRequiredFieldsFilledResult()
    {

    }
}
