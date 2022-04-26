package edu.wpi.cs3733.D22.teamC.controller.map.data.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.FloorMapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceRequestNode extends MapNode<ServiceRequest> {
    // Constants
    private static final String TOKEN_PATH = "view/map/nodes/service_request/node.fxml";
    private static final Pair<Integer, Integer> TOKEN_ORIGIN = new Pair(-65, -59);
    private static final int TOKEN_OFFSET = 25;

    // References
    Group contextGroup;
    private ServiceRequestToken[] tokens = new ServiceRequestToken[ServiceRequest.RequestType.values().length];

    // Variables
    List<ServiceRequest> serviceRequests;

    public ServiceRequestNode(ServiceRequestManager manager, Location location) {
        super(manager, location);

        // Load FXML
        LocationMapNode locationMapNode = (LocationMapNode) manager.getMapViewController().getLocationManager().getByLocation(location);
        contextGroup = locationMapNode.getContextGroup();

        for (ServiceRequest.RequestType requestType : ServiceRequest.RequestType.values()) {
            App.View<ServiceRequestToken> view = App.instance.loadView(TOKEN_PATH);

            // Setup Controller
            ServiceRequestToken controller = view.getController();

            contextGroup.getChildren().add(view.getNode());

            int x = TOKEN_ORIGIN.getKey() + (requestType.ordinal() % 2) * TOKEN_OFFSET;
            int y = TOKEN_ORIGIN.getValue() + (requestType.ordinal() / 2) * TOKEN_OFFSET;
            controller.setPosition(x, y);
            controller.setType(requestType);
            controller.setup(this);

            // Set On Click Functionality
            controller.onClickEvent = event -> {
                ((FloorMapViewController) manager.getMapViewController()).getLocationInfoController().setCurrentTab(2);
                ((FloorMapViewController) manager.getMapViewController()).getLocationInfoController().setServiceRequestFilter(requestType);
            };

            tokens[requestType.ordinal()] = controller;
        }

        updateValues();
    }

    public void updateValues() {
        ServiceRequestDAO dao = new ServiceRequestDAO();
        serviceRequests = dao.getAllSRByLocation(location.getID());

        for (ServiceRequest.RequestType requestType : ServiceRequest.RequestType.values()) {
            List<ServiceRequest> serviceRequestsByType = serviceRequests.stream().filter(serviceRequest -> serviceRequest.getRequestType() == requestType).collect(Collectors.toList());
            tokens[requestType.ordinal()].setServiceRequests(serviceRequestsByType);
        }
    }

    //#region State Changes
        public void toPreviewMode() {
            for (ServiceRequestToken token : tokens) {
                token.setVisible(token.getActive() && ((FloorMapViewController) manager.getMapViewController()).getMapControlsController().getTokenChecked());
            }
        }

        public void toFocusMode() {
            for (ServiceRequestToken token : tokens) {
                token.setVisible(((FloorMapViewController) manager.getMapViewController()).getMapControlsController().getTokenChecked());
            }
        }

        public void removeNode() {
            for (ServiceRequestToken token : tokens) {
                token.root.getChildren().clear();
                contextGroup.getChildren().remove(token.root);
            }
        }
    //#endregion
}
