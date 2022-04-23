package edu.wpi.cs3733.D22.teamC.controller.map.data.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment.MedicalEquipmentCounter;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceRequestNode extends MapNode<ServiceRequest> {
    // Constants
    private static final String TOKEN_PATH = "view/map/nodes/service_request/node.fxml";
    private static final Pair<Integer, Integer>[] TOKEN_OFFSETS = new Pair[] {
            new Pair(-30, -60),
            new Pair(-30, -30),
            new Pair(0, -60),
            new Pair(0, -30),
            new Pair(30, -60),
            new Pair(30, -30),
            new Pair(60, -60),
            new Pair(60, -30),
            new Pair(90, -60),
            new Pair(90, -30)
    };

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
            controller.setPosition(TOKEN_OFFSETS[requestType.ordinal()].getKey(), TOKEN_OFFSETS[requestType.ordinal()].getValue());
            controller.setType(requestType);
            controller.setup(this);

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
//                token.setVisible((counter.getCount() != 0));
            }
        }

        public void toFocusMode() {
            for (ServiceRequestToken token : tokens) {
//                counter.setVisible(true);
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
