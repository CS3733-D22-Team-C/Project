package edu.wpi.cs3733.D22.teamC.controller.dashboard;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.profile.DashboardAssignedTableDisplay;
import edu.wpi.cs3733.D22.teamC.controller.profile.DashboardCreatedTableDisplay;
import edu.wpi.cs3733.D22.teamC.controller.service_request.SegmentBarController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController<T extends ServiceRequest> implements Initializable {

    private static final String FROM_DASH_RESOLVE_FORM = "view/service_request/skeleton/from_dash_resolve.fxml";
    private static final String CREATE_FORM = "view/service_request/skeleton/create_form.fxml";
    @FXML
    private JFXTreeTableView assignedTable;
    @FXML
    private JFXTreeTableView createdTable;
    @FXML
    private Label greetingLabel;
    @FXML
    private VBox assignedTableBox;
    @FXML
    private VBox createdTableBox;

    private Employee employee;
    private DashboardAssignedTableDisplay assignedTableDisplay;
    private DashboardCreatedTableDisplay createdTableDisplay;

    SegmentBarController insertAssignedTableBarController;
    SegmentBarController insertCreatedTableBarController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assignedTableDisplay = new DashboardAssignedTableDisplay(assignedTable);
        createdTableDisplay = new DashboardCreatedTableDisplay(createdTable);

        setOtherRowInteraction();
        setRowInteraction();


        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();

        List<ServiceRequest> assignedServiceRequests = serviceRequestDAO.getAllSRByAssignee(App.instance.getUserAccount().getID());
        assignedServiceRequests.forEach(assignedTableDisplay::addObject);
        List<ServiceRequest> createdServiceRequests = serviceRequestDAO.getAllSRByCreator(App.instance.getUserAccount().getID());
        createdServiceRequests.forEach(createdTableDisplay::addObject);

        setGreetingLabel(App.instance.getUserAccount().getUsername());

        //SegmentedBars
        setAssignedTableSegmentedBarInsert();
        insertAssignedTableBarController.preSetup();
        for (ServiceRequest serviceRequest : assignedServiceRequests) {
            insertAssignedTableBarController.updateNumbers(serviceRequest.getStatus(), true);
        }
        insertAssignedTableBarController.setup(true);

        setCreatedTableSegmentedBarInsert();
        insertCreatedTableBarController.preSetup();
        for (ServiceRequest serviceRequest : createdServiceRequests) {
            insertCreatedTableBarController.updateNumbers(serviceRequest.getStatus(), true);
        }
        insertCreatedTableBarController.setup(true);

    }

    public void setGreetingLabel(String username) {
        greetingLabel.setText("Hello, " + username + "!");
    }

    public void setAssignedTableSegmentedBarInsert(){
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertAssignedTableBarController = view.getController();
        assignedTableBox.getChildren().add(0, view.getNode());
    }

    public void setCreatedTableSegmentedBarInsert(){
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertCreatedTableBarController = view.getController();
        createdTableBox.getChildren().add(0, view.getNode());
    }

    protected void setRowInteraction() {
        assignedTable.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        ServiceRequest SR = (ServiceRequest) row.getItem().object;
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(FROM_DASH_RESOLVE_FORM);
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
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(FROM_DASH_RESOLVE_FORM);
                        view.getController().setup(SR, true);
                        App.instance.setView(view.getNode());
                    }
                }
            });
            return row ;
        });
    }
}
