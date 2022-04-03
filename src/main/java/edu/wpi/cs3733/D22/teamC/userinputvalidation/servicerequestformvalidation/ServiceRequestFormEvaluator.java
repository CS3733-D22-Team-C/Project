package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import java.util.ArrayList;

public class ServiceRequestFormEvaluator {

    protected static final ServiceRequestValidationErrorItem[] serviceRequestValidationGeneralErrorItemList =
            {new ServiceRequestValidationErrorItem(1, "Required field Location ID is missing"),
            new ServiceRequestValidationErrorItem(2, "Required field AssigneeID is missing"),
            new ServiceRequestValidationErrorItem(3, "Required field Status is missing"),
            new ServiceRequestValidationErrorItem(4, "Required field Priority is missing"),
                    new ServiceRequestValidationErrorItem(5, "Assignee ID does not exist"),
                    new ServiceRequestValidationErrorItem(6, "Location ID does not exist"),
            };


    public ServiceRequestFormEvaluator() {}

    /**
     * Determine if the assigneeID sent from a ServiceRequestCreateController object is valid through checking if it exists in the Employee Table database.
     * If the assigneeID does not exist in the Employee Table of the database, return the corresponding ServiceRequestValidationErrorItem. Else, return null.
     * @param assigneeID The assigneeID of the service request.
     * @return ServiceRequestValidationErrorItem.
     */
    public ServiceRequestValidationErrorItem getValidateAssigneeIDResult(int assigneeID)
    {
        ServiceRequestDAOImpl serviceRequestDaoVar = new ServiceRequestDAOImpl();

        //Need access to an employeeDAO to check if the assigneeID exists (need to replace true below)

        if(true)
        {
            return null;
        }
        else
        {
            return serviceRequestValidationGeneralErrorItemList[4];
        }

    }

    /**
     *Determine if the location ID sent from a ServiceRequestCreateController object exists in the Location Table database.
     * If the location ID does not exist in the Location Table database, return the corresponding ServiceRequestValidationErrorItem. Else, return null.
     * @param locationID The location ID of the service request
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestValidationErrorItem getValidateLocationIDResult(int locationID)
    {
        LocationDAOImpl locationDaoVar = new LocationDAOImpl();

        if(locationDaoVar.getLocation(locationID) == null) //assumes the user inputs a location ID in the service request location field.
        {
            return serviceRequestValidationGeneralErrorItemList[5];
        }
        else
        {
            return null;
        }

    }

    /**
     * Determine if the assigneeID of a ServiceRequest is filled.
     * @param assigneeID
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestValidationErrorItem checkAssigneeIDFilled(int assigneeID)
    {
        int assigneeIDLength = (int)(Math.log10(assigneeID)+1);

        if(assigneeIDLength == 0)
        {
            return serviceRequestValidationGeneralErrorItemList[1];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the LocationID of a ServiceRequest has been filled.
     * @param locationID
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestValidationErrorItem checkLocationIDFilled(int locationID)
    {
        int locationIDLength = (int)(Math.log10(locationID)+1);

        if(locationIDLength == 0)
        {
            return serviceRequestValidationGeneralErrorItemList[0];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the status of a ServiceRequest has been filled.
     * @param status
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestValidationErrorItem checkStatusFilled(String status)
    {
       if(status.length() == 0)
       {
           return serviceRequestValidationGeneralErrorItemList[2];
       }
       else
       {
           return null;
       }
    }

    /**
     * Determine if the priority of a ServiceRequest has been filled.
     * @param priority
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestValidationErrorItem checkPriorityFilled(String priority)
    {
        if(priority.length() == 0)
        {
            return serviceRequestValidationGeneralErrorItemList[3];
        }
        else
        {
            return null;
        }
    }

}
