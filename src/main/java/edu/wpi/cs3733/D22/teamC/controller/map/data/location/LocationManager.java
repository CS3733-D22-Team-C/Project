package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages Location data for MapControllers. A mandatory manager for Maps to work.
 */
public class LocationManager extends ManagerMapNodes<Location> {
    // Variables
    private boolean isEditMode;

    // Events
    public List<Runnable> onUpdateDataEvents = new ArrayList<>();

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
                changeCurrent(current);
            } else {
                removeObject(location);
            }
        }

        @Override
        public void addObject(Location object) {
            super.addObject(object);
            if (object.getFloor().equals(mapViewController.getFloorManager().getCurrent().getID())) {
                allNodes.add(drawNode(object));
            }
            updatesOccured();
        }

        @Override
        public void removeObject(Location object) {
            super.removeObject(object);
            if (allNodes.contains(getByLocation(object))) removeNode(getByLocation(object));
            updatesOccured();
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

            changeCurrent(null);
            renderFloor(mapViewController.getFloorManager().getCurrent());
        }

        public void updatesOccured() {
            onUpdateDataEvents.forEach(Runnable::run);
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

        public boolean isEditMode() {
            return isEditMode;
        }
    //#endregion
}
