package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;

public interface InsertServiceRequestCreateController<T extends ServiceRequest> {
    /**
     * Clear fields of insert form.
     */
    void clearFields();

    /**
     * Create Service Request for insert form.
     * @return Service Request of type T.
     */
    T createServiceRequest();

    /**
     * Create Service Request DAO for insert form.
     * @return Service Request DAO of type T.
     */
    ServiceRequestDAO<T> createServiceRequestDAO();

    /**
     * Setup Service Request Table Display of insert form.
     * @param table JFXTreeTableView component.
     * @return Service Request Table Display of type T.
     */
    ServiceRequestTableDisplay<T> setupTable(JFXTreeTableView<?> table);
}
