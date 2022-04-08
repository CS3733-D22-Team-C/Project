package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import javafx.fxml.FXML;

public abstract class InsertServiceRequestResolveController<T extends ServiceRequest> {
    // References
    BaseServiceRequestResolveController<T> parentController;
    protected boolean isEditMode;
    protected T serviceRequest;

    public void setup(BaseServiceRequestResolveController<T> baseServiceRequestResolveController, T serviceRequest, boolean isEditMode) {
        this.parentController = baseServiceRequestResolveController;
        this.isEditMode =isEditMode;
        this.serviceRequest = serviceRequest;
        //System.out.println(isEditMode);
    }

   public boolean requiredFieldsPresent(){return true;}
   public abstract void updateServiceRequest(T serviceRequest);

    @FXML
    protected void onFieldUpdated() {
        parentController.onFieldUpdated();
    }
    /**
     * Create Service Request DAO for insert form.
     * @return Service Request DAO of type T.
     */
    public abstract DAO<T> createServiceRequestDAO();
}
