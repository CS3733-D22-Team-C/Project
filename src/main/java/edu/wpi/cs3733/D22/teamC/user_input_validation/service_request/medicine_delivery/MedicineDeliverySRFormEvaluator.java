package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

import java.util.ArrayList;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicineDeliverySRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getMedicineDeliverySRValidationTestResult(int locationID, int assigneeID, int patientID, String medicine, String dosage, String status, String priority)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(locationID, assigneeID, status, priority));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(locationID));
        errorList.add(checkPatientIDFilled(patientID));
        errorList.add(checkMedicineFilled(medicine));
        errorList.add(checkDosageFilled(dosage));

        return errorList;
    }

    /**
     * Determine if the Patient ID of a MedicineDelivery Service Request has been filled
     * @param patientID
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkPatientIDFilled(int patientID)
    {
        return null;
    }

    /**
     * Determine if the Medicine of a MedicineDelivery Service Request has been filled
     * @param medicine
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkMedicineFilled(String medicine)
    {
        return null;
    }

    /**
     * Determine if the Dosage of a MedicineDelivery Service Request has been filled
     * @param dosage
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkDosageFilled(String dosage)
    {
        return null;
    }
}
