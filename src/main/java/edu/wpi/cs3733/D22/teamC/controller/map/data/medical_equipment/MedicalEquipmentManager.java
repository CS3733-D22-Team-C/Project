package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages Medical Equipment data for MapControllers. An optional manager for Map to work with Medical Equipment Functionality.
 */
public class MedicalEquipmentManager extends ManagerMapNodes<MedicalEquipment> {
    // Variables
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;

    public MedicalEquipmentManager(MapViewController mapViewController) {
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
                    ((MedicalEquipmentNode) focused).toPreviewMode();
                    previewed = focused;
                    focused = null;
                } else if (location != null) {
                    previewed = new MedicalEquipmentNode(this, location);
                    ((MedicalEquipmentNode) previewed).toPreviewMode();
                }
            }
        }

        public void focusLocation(Location location) {
            if (focused != null) {
                removeNode(focused);
                focused = null;
            }

            if (previewed != null && previewed.getLocation() == location) {
                ((MedicalEquipmentNode) previewed).toFocusMode();
                focused = previewed;
                previewed = null;
            } else if (location != null) {
                focused = new MedicalEquipmentNode(this, location);
                ((MedicalEquipmentNode) focused).toFocusMode();
            }
        }
    //#endregion

    //#region Map Node Manipulation
        @Override
        public void removeNode(MapNode<MedicalEquipment> mapNode) {
            ((MedicalEquipmentNode) mapNode).removeNode();
        }
    //#endregion
}
