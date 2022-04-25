package edu.wpi.cs3733.D22.teamC.models;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.LocationInfoController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.generic.SelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class SRShortcutSelectorWindow extends SelectorWindow<ServiceRequest> implements Initializable {
    private static final String SR_EDIT_VIEW_PATH = "view/service_request/popup_service_request_resolve.fxml";

    @FXML
    private GridPane root;

    BaseServiceRequestResolveController baseServiceRequestResolveController;
    ServiceRequest serviceRequest;

    public SRShortcutSelectorWindow(Consumer<ServiceRequest> consumer) {
        super(consumer);

        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(SR_EDIT_VIEW_PATH);
        this.baseServiceRequestResolveController = view.getController();

        root.getChildren().add(view.getNode());

        view.getController().setup(serviceRequest, true);
        ComponentWrapper.view.getController();

    }

    @Override
    protected String getView() {
        return "view/map/floor_map.fxml";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
