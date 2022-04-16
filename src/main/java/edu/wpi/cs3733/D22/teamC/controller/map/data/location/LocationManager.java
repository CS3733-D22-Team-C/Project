package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.Manager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationManager extends ManagerMapNodes<Location> {
    // Variables
    private boolean isEditMode;

    public LocationManager(MapViewController mapViewController) {
        super(mapViewController);
    }

    //#region Location Manipulation
        public void resetLocation(Location location) {
            LocationDAO locationDAO = new LocationDAO();
            Location original = locationDAO.getByID(location.getID());

            if (original != null) {
                location.Copy(original);
                ((LocationMapNode) getByLocation(location)).updatePosition();
            } else {
                all.remove(location);
                removeNode(getByLocation(location));
            }
        }

        public void deleteLocation(Location location) {
            LocationDAO locationDAO = new LocationDAO();
            Location original = locationDAO.getByID(location.getID());

            if (original != null) locationDAO.delete(location);

            all.remove(location);
            removeNode(getByLocation(location));
        }
    //#endregion

    //#region Map Node Manipulation
        public LocationMapNode drawNode(Location location) {
            return new LocationMapNode(this, location);
        }
    //#endregion

    //#region Content Changes
        public void renderFloor(Floor floor) {
            allNodes.forEach(this::removeNode);
            allNodes = new ArrayList<>();

            List<Location> renderLocations;
            if (!isEditMode) {
                all = new FloorDAO().getAllLocations(floor.getID());
                renderLocations = all;
            } else {
                renderLocations = all.stream().filter(location -> location.getFloor().equals(floor.getID())).collect(Collectors.toList());
            }
            renderLocations.forEach(location -> allNodes.add(drawNode(location)));
        }
    //#endregion

    //#region Edit Mode
        /**
         * Switch usage of all based on Location Manager mode.
         * @param editing The mode the Location Manager is to be entering.
         */
        public void switchMode(boolean editing) {
            this.isEditMode = editing;

            if (editing) {
                all = new LocationDAO().getAll();
            } else {
                all = new FloorDAO().getAllLocations(mapViewController.getFloorManager().getCurrent().getID());
            }
        }

        /**
         * Save changes by inserting all Location data.
         */
        public void saveChanges() {
            if (isEditMode) {
                LocationDAO dao = new LocationDAO();
                dao.deleteAllFromTable();
                all.forEach(dao::insert);
            }
        }
    //#endregion
}
