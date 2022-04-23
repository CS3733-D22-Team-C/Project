package edu.wpi.cs3733.D22.teamC.controller.dashboard;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController<T extends ServiceRequest> implements Initializable {

    private static final String RESOLVE_FORM = "view/service_request/skeleton/resolve_form.fxml";
    private static final String CREATE_FORM = "view/service_request/skeleton/create_form.fxml";
    @FXML
    private JFXTreeTableView assignedTable;
    @FXML
    private JFXTreeTableView createdTable;
    @FXML
    private Label greetingLabel;

    private Employee employee;
    private DashboardAssignedTableDisplay assignedTableDisplay;
    private DashboardCreatedTableDisplay createdTableDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assignedTableDisplay = new DashboardAssignedTableDisplay(assignedTable);
        createdTableDisplay = new DashboardCreatedTableDisplay(createdTable);

        setRowInteraction();
        setOtherRowInteraction();

        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();

        serviceRequestDAO.getAllSRByAssignee(App.instance.getUserAccount().getID()).forEach(assignedTableDisplay::addObject);
        serviceRequestDAO.getAllSRByCreator(App.instance.getUserAccount().getID()).forEach(createdTableDisplay::addObject);

        setGreetingLabel(App.instance.getUserAccount().getUsername());
    }

    public void setGreetingLabel(String username) {
        greetingLabel.setText("Hello, " + username + "!");
    }

    protected void setRowInteraction() {
        assignedTable.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        ServiceRequest SR = (ServiceRequest) row.getItem().object;
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(RESOLVE_FORM);
                        view.getController().setup(SR, false);
                        App.instance.setView(view.getNode());
                    }
                }
            });
            return row ;
        });
    }

    protected void setOtherRowInteraction() {
        createdTable.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        ServiceRequest SR = (ServiceRequest) row.getItem().object;
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(CREATE_FORM);
                        view.getController().setup(SR, false);
                        App.instance.setView(view.getNode());
                    }
                }
            });
            return row ;
        });
    }

}
