package edu.wpi.cs3733.D22.teamC.userinputvalidation.servicerequestformvalidation;

//Note: If user input validation expands beyond service requests forms as development continues, then user validation can be further abstracted when refactoring.

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;

public class ServiceRequestFormEvaluator {

    protected static final ServiceRequestValidationErrorItem[] serviceRequestValidationErrorItemList =
            {new ServiceRequestValidationErrorItem(1, "Required field Location ID is missing"),
            new ServiceRequestValidationErrorItem(2, "Required field AssigneeID is missing"),
            new ServiceRequestValidationErrorItem(3, "Required field Status is missing"),
            new ServiceRequestValidationErrorItem(4, "Required field Priority is missing"),
            new ServiceRequestValidationErrorItem(5, "Required field Equipment ID is missing"),
            new ServiceRequestValidationErrorItem(6, "Required field Equipment Type is missing"),
            new ServiceRequestValidationErrorItem(7, "Required field Maintenance Type is missing"),
            new ServiceRequestValidationErrorItem(8, "Required field Lab Type is missing"),
            new ServiceRequestValidationErrorItem(9, "Required field Patient ID is missing"),
            new ServiceRequestValidationErrorItem(10, "Required field Dosage is missing"),
            new ServiceRequestValidationErrorItem(11, "Required field Medicine is missing"),
            new ServiceRequestValidationErrorItem(12, "Required field Sanitation Type is missing"),
            new ServiceRequestValidationErrorItem(13, "Required field Security Type is missing"),
            new ServiceRequestValidationErrorItem(14, "Assignee ID is too short and does not exist"),
            new ServiceRequestValidationErrorItem(15, "Assignee ID does not exist"),
            new ServiceRequestValidationErrorItem(16, "Location ID does not exist"),
            new ServiceRequestValidationErrorItem(17, "Equipment ID does not exist"),
            new ServiceRequestValidationErrorItem(18, "Maintenance type does not exist"),
            new ServiceRequestValidationErrorItem(19, "Invalid patient ID"),
            new ServiceRequestValidationErrorItem(20, "Specified medicine does not exist"),
            new ServiceRequestValidationErrorItem(21, "Invalid dosage quantity")};

    public ServiceRequestFormEvaluator() {}

    /**
     * Determine if the assigneeID sent from a ServiceRequestCreateController object is valid through checking its length and if it exists in the Employee Table database.
     * If the assigneeID is too short, return 5. Else if the assigneeID does not exist in the Employee Table of the database, return 2. Else, return 0.
     * @param assigneeID The assigneeID of the service request.
     * @return int.
     */
    public int getValidateAssigneeIDResult(String assigneeID)
    {
        ServiceRequestDAOImpl serviceRequestDaoVar = new ServiceRequestDAOImpl();
        int assigneeIDCopy = Integer.parseInt(assigneeID);

        if(assigneeID.length() < 10)
        {
            return 5; //Return 1 if the assigneeID is not the proper length
        }
        else
        {
            return 0;
        }
    }

    /**
     *Determine if the location ID sent from a ServiceRequestCreateController object exists in the Location Table database.
     * If the location ID does not exist in the Location Table database, return 6. Else, return 0.
     * @param locationID The location ID of the service request
     * @return int
     */
    public int getValidateLocationIDResult(String locationID)
    {
        LocationDAOImpl locationDaoVar = new LocationDAOImpl();

        if(locationDaoVar.getLocation(locationID) == null) //assumes the user inputs a location ID in the service request location field.
        {
            return 6;
        }
        else
        {
            return 0;
        }
    }

    //Overload these methods in each subclass below, it doesn't make sense to have only one type of check for all required fields.

    /**
     * Determine if all required fields of a Service Request are filled. The required fields for a Service Request are at least assigneeID, LocationID, Status, and Priority.
     * If all required fields are not filled, return 4. Else, return 0.
     * @param locationID The location ID of s service request
     * @param assigneeID The assigneeID of a service request
     * @param status The current status of a service request
     * @param priority The priority of a service request
     * @return int
     */
    public int getValidateAllRequiredFieldsFilledResult(String locationID, String assigneeID, String status, String priority)
    {
        return 0;
    }
}
