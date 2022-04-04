package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;

import java.util.ArrayList;

public class ServiceRequestFormEvaluator {

    public ServiceRequestFormEvaluator() {}

    /**
     * Determine if all the basic fields of a Service Request (locationID, assigneeID, status, and priority) have been filled,
     * @param locationID
     * @param assigneeID
     * @param status
     * @param priority
     * @return ArrayList <ServiceRequestUserInputValidationErrorItem>
     */
    public ArrayList<ServiceRequestUserInputValidationErrorItem> getBasicRequiredFieldsFilledValidationResult(int locationID, int assigneeID, String status, String priority)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem>();

        errorList.add(checkAssigneeIDFilled(assigneeID));
        errorList.add(checkLocationIDFilled(locationID));
        errorList.add(checkPriorityFilled(priority));
        errorList.add(checkStatusFilled(status));

        return errorList;
    }

    /**
     * Determine if the assigneeID sent from a ServiceRequestCreateController object is valid through checking if it exists in the Employee Table database.
     * If the assigneeID does not exist in the Employee Table of the database, return the corresponding ServiceRequestValidationErrorItem. Else, return null.
     * @param assigneeID The assigneeID of the service request.
     * @return ServiceRequestValidationErrorItem.
     */
    public ServiceRequestUserInputValidationErrorItem getValidateAssigneeIDResult(int assigneeID)
    {
        ServiceRequestDAOImpl serviceRequestDaoVar = new ServiceRequestDAOImpl();

        //Need access to an employeeDAO to check if the assigneeID exists (need to replace true below)

        if(true)
        {
            return null;
        }
        else
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[4];
        }
    }

    /**
     *Determine if the location ID sent from a ServiceRequestCreateController object exists in the Location Table database.
     * If the location ID does not exist in the Location Table database, return the corresponding ServiceRequestValidationErrorItem. Else, return null.
     * @param locationID The location ID of the service request
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem getValidateLocationIDResult(int locationID)
    {
        LocationDAOImpl locationDaoVar = new LocationDAOImpl();

        if(locationDaoVar.getLocation(locationID) == null) //assumes the user inputs a location ID in the service request location field.
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[5];
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
    public ServiceRequestUserInputValidationErrorItem checkAssigneeIDFilled(int assigneeID)
    {
        int assigneeIDLength = (int)(Math.log10(assigneeID)+1);

        if(assigneeIDLength == 0)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[1];
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
    public ServiceRequestUserInputValidationErrorItem checkLocationIDFilled(int locationID)
    {
        int locationIDLength = (int)(Math.log10(locationID)+1);

        if(locationIDLength == 0)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[0];
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
    public ServiceRequestUserInputValidationErrorItem checkStatusFilled(String status)
    {
       if(status.isEmpty())
       {
           return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[2];
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
    public ServiceRequestUserInputValidationErrorItem checkPriorityFilled(String priority)
    {
        if(priority.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[3];
        }
        else
        {
            return null;
        }
    }

}
