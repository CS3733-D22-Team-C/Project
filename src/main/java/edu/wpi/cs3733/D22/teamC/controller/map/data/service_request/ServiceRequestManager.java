package edu.wpi.cs3733.D22.teamC.controller.map.data.service_request;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapCounter;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ServiceRequestManager extends ManagerMapNodes<ServiceRequest> {
    // Constants
    private final static Pair<Integer, Integer> COUNTER_OFFSET = new Pair<>(-12, -12);

    // Variables
    List<MapCounter> counters = new ArrayList<>();
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;
    BiConsumer<Floor, Floor> onChangeFloorEvent = (f1, f2) -> this.drawCounters();


    public ServiceRequestManager(MapViewController mapViewController) {
        super(mapViewController);

        mapViewController.getLocationManager().onPreviewLocationEvents.add(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.add(onFocusLocationEvent);
        mapViewController.getFloorManager().onChangeCurrentEvents.add(onChangeFloorEvent);

        focusLocation(mapViewController.getLocationManager().getCurrent());
        drawCounters();
    }

    public void shutdown() {
        mapViewController.getLocationManager().onPreviewLocationEvents.remove(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.remove(onFocusLocationEvent);
        mapViewController.getFloorManager().onChangeCurrentEvents.remove(onChangeFloorEvent);

        previewLocation(null);
        focusLocation(null);

        deleteCounters();
    }

    //#region Object Manipulation
        public List<ServiceRequest> getAllByLocation(Location location) {
            return new ServiceRequestDAO().getAllSRByLocation(location.getID());
        }
    //#endregion

    //#region Location Changes
        public void previewLocation(Location location) {
            if (focused == null || focused.getLocation() != location) {
                if (previewed != null) {
                    removeNode(previewed);
                    previewed = null;
                }

                if (focused != null && focused.getLocation() == location) {
                    ((ServiceRequestNode) focused).toPreviewMode();
                    previewed = focused;
                    focused = null;
                } else if (location != null) {
                    previewed = new ServiceRequestNode(this, location);
                    ((ServiceRequestNode) previewed).toPreviewMode();
                }
            }
        }

        public void focusLocation(Location location) {
            if (focused != null) {
                removeNode(focused);
                focused = null;
            }

            if (previewed != null && previewed.getLocation() == location) {
                ((ServiceRequestNode) previewed).toFocusMode();
                focused = previewed;
                previewed = null;
            } else if (location != null) {
                focused = new ServiceRequestNode(this, location);
                ((ServiceRequestNode) focused).toFocusMode();
            }
        }
    //#endregion

    //#region Map Node Manipulation
        @Override
        public void removeNode(MapNode<ServiceRequest> mapNode) {
            ((ServiceRequestNode) mapNode).removeNode();
        }
    //#endregion

    //#region Counters
        private void drawCounters() {
            for (Location location : getMapViewController().getLocationManager().getAll()) {
                // Load Counter
                MapCounter counter = new MapCounter(location);
                counters.add(counter);

                // Set Counter Location
                LocationMapNode locationMapNode = (LocationMapNode) getMapViewController().getLocationManager().getByLocation(location);
                Group contextGroup = locationMapNode.getContextGroup();
                counter.setParent(contextGroup);
                counter.setPosition(COUNTER_OFFSET.getKey(), COUNTER_OFFSET.getValue());

                // Set CSS
                counter.getNode().getStyleClass().add("service-request-counter");

                // Set Counter
                counter.setCount(getAllByLocation(location).size());
            }
        }

        private void deleteCounters() {
            counters.forEach(MapCounter::delete);
            counters = new ArrayList<>();
        }
    //#endregion
}
