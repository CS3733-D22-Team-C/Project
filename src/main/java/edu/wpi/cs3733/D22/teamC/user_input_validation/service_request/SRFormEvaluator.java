package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ErrorRecord;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class SRFormEvaluator {

    /**
     * Determine if all the basic fields of a Service Request (locationID, assigneeID, status, and priority) have been filled,
     * @param location
     * @param assigneeID
     * @param status
     * @param priority
     * @return ArrayList <ServiceRequestUserInputValidationErrorItem>
     */
    public ArrayList<ServiceRequestUserInputValidationErrorItem> getBasicRequiredFieldsFilledValidationResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem>();

        errorList.add(checkLocationIDFilled(location));
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
    public ServiceRequestUserInputValidationErrorItem getValidateAssigneeIDResult(String assigneeID)
    {
        ServiceRequestDAO serviceRequestDaoVar = new ServiceRequestDAO();

        //Need access to an employeeDAO to check if the assigneeID exists (need to replace true below)

        if(true)
        {
            return null;
        }
        else
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[3];
        }
    }

    /**
     *Determine if the location ID sent from a ServiceRequestCreateController object exists in the Location Table database.
     * If the location ID does not exist in the Location Table database, return the corresponding ServiceRequestValidationErrorItem. Else, return null.
     * @param locationID The location ID of the service request
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem getValidateLocationIDResult(String locationID)
    {
        LocationDAO locationDaoVar = new LocationDAO();

        if(locationID.isEmpty())
        {
            return null;
        }
        else
        {
            String locationConv = locationID;

            if(locationDaoVar.getByID(locationConv) == null) //assumes the user inputs a location ID in the service request location field.
            {
                return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[4];
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Determine if the LocationID of a ServiceRequest has been filled.
     * @param locationID
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkLocationIDFilled(String locationID)
    {
        if(locationID.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[0];
        }
        else
        {
            int locationIDConv = Integer.parseInt(locationID);
            int locationIDLength = (int)(Math.log10(locationIDConv)+1);

            if(locationIDLength == 0)
            {
                return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[0];
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Determine if the status of a ServiceRequest has been filled.
     * @param status
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkStatusFilled(SingleSelectionModel status)
    {
       if(status.isEmpty())
       {
           return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[1];
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
    public ServiceRequestUserInputValidationErrorItem checkPriorityFilled(SingleSelectionModel priority)
    {
        if(priority.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[2];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine whether or not there are any user input errors on a Service Request
     * @param l
     * @return boolean
     */
    public boolean noServiceRequestFormUserInputErrors(ArrayList <ServiceRequestUserInputValidationErrorItem> l)
    {
        for(int x = 0; x < l.size(); x++)
        {
            if(l.get(x) != null)
            {
                return false;
            }
        }
        return true;
    }

}
