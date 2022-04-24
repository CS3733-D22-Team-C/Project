package edu.wpi.cs3733.D22.teamC.controller.map.data.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment.MedicalEquipmentCounter;
import edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment.MedicalEquipmentNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ServiceRequestManager extends ManagerMapNodes<ServiceRequest> {
    // Variables
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;


    public ServiceRequestManager(MapViewController mapViewController) {
        super(mapViewController);

        mapViewController.getLocationManager().onPreviewLocationEvents.add(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.add(onFocusLocationEvent);

        focusLocation(mapViewController.getLocationManager().getCurrent());
    }

    public void shutdown() {
        mapViewController.getLocationManager().onPreviewLocationEvents.remove(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.remove(onFocusLocationEvent);

        previewLocation(null);
        focusLocation(null);
    }

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

}
