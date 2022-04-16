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
import java.util.stream.Collectors;

/**
 * Manages Medical Equipment data for MapControllers. An optional manager for Map to work with Medical Equipment Functionality.
 */
public class MedicalEquipmentManager {
    // Variables
    private MedicalEquipmentNode previewed;
    private MedicalEquipmentNode focused;

    // References
    MapViewController mapViewController;

    public MedicalEquipmentManager(MapViewController mapViewController) {
        this.mapViewController = mapViewController;

        mapViewController.getLocationManager().onPreviewLocationEvents.add((oldLocation, newLocation) -> previewLocation(newLocation));
        mapViewController.getLocationManager().onFocusLocationEvents.add((oldLocation, newLocation) -> focusLocation(newLocation));
    }

    //#region Location Changes
        public void previewLocation(Location location) {
            if (previewed != null) {
                previewed.removeNode();
                previewed = null;
            }

            if (focused != null && focused.location == location) {
                focused.toPreviewMode();
                previewed = focused;
                focused = null;
            } else if (location != null) {
                previewed = new MedicalEquipmentNode(this, location);
                previewed.toPreviewMode();
            }
        }

        public void focusLocation(Location location) {
            if (focused != null) {
                focused.removeNode();
                focused = null;
            }

            if (previewed != null && previewed.location == location) {
                previewed.toFocusMode();
                focused = previewed;
                previewed = null;
            } else if (location != null) {
                focused = new MedicalEquipmentNode(this, location);
                focused.toFocusMode();
            }
        }
    //#endregion
}
