package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.Manager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages Location data for MapControllers. A mandatory manager for Maps to work.
 */
public class LocationManager extends ManagerMapNodes<Location> {
    // Variables
    private boolean isEditMode;
    private boolean[] filteredNodeTypes = new boolean[Location.NodeType.values().length];

    // Events
    public List<Consumer<Location>> onPreviewLocationEvents = new ArrayList<>();
    public List<Consumer<Location>> onFocusLocationEvents = new ArrayList<>();

    public LocationManager(MapViewController mapViewController) {
        super(mapViewController);

        Arrays.fill(filteredNodeTypes, true);
    }

    //#region Object Manipulation
        public void resetObject(Location location) {
            LocationDAO locationDAO = new LocationDAO();
            Location original = locationDAO.getByID(location.getID());

            if (original != null) {
                location.Copy(original);
                LocationMapNode locationMapNode = ((LocationMapNode) getByLocation(location));
                locationMapNode.updatePosition();
                locationMapNode.updateIcon();
                changeCurrent(current);
            } else {
                removeObject(location);
            }
        }

        @Override
        public void addObject(Location object) {
            super.addObject(object);
            if (object.getFloor().equals(mapViewController.getFloorManager().getCurrent().getID())) {
                nodes.add(new LocationMapNode(this, object));
            }
            updatesOccured();
        }

        @Override
        public void removeObject(Location object) {
            super.removeObject(object);
            if (nodes.contains(getByLocation(object))) removeNode(getByLocation(object));
            updatesOccured();
        }
    //#endregion

    //#region Map Node Manipulation
        @Override
        public void removeNode(MapNode<Location> mapNode) {
            ((Group) mapNode.getNode()).getChildren().clear();
            getMapController().getMap().getChildren().remove(mapNode);
        }
    //#endregion

    //#region Selection Status
        public void focus(MapNode<Location> mapNode) {
            if (focused != mapNode) {
                if (isFocusing()) unfocus();

                onFocusLocationEvents.forEach(event -> event.accept(mapNode.getLocation()));
                ((LocationMapNode) mapNode).onFocus();
                focused = mapNode;
            }
        }

        public void unfocus() {
            if (isFocusing()) {
                onFocusLocationEvents.forEach(event -> event.accept(null));
                ((LocationMapNode) focused).offFocus();
                focused = null;
            }
        }

        public void preview(MapNode<Location> mapNode) {
            if (previewed != mapNode) {
                if (isPreviewing()) unpreview();

                onPreviewLocationEvents.forEach(event -> event.accept(mapNode.getLocation()));
                ((LocationMapNode) mapNode).onPreview();
                previewed = mapNode;
            }
        }

        public void unpreview() {
            if (isPreviewing()) {
                onPreviewLocationEvents.forEach(event -> event.accept(null));
                ((LocationMapNode) previewed).offPreview();
                previewed = null;
            }
        }
    //#endregion

    //#region Content Changes
        public void renderFloor(Floor floor) {
            nodes.forEach(this::removeNode);
            nodes = new ArrayList<>();

            // Floor Filtering
            List<Location> renderLocations;
            if (!isEditMode) {
                all = new FloorDAO().getAllLocations(floor.getID());
                renderLocations = all;
            } else {
                renderLocations = all.stream().filter(location -> location.getFloor().equals(floor.getID())).collect(Collectors.toList());
            }

            // Node Type Filtering
            renderLocations = renderLocations.stream().filter(location -> filteredNodeTypes[location.getNodeType().ordinal()]).collect(Collectors.toList());

            renderLocations.forEach(location -> nodes.add(new LocationMapNode(this, location)));
        }

        public void changeFilters(boolean[] filteredNodeTypes) {
            this.filteredNodeTypes = filteredNodeTypes;
            renderFloor(getMapViewController().getFloorManager().getCurrent());
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
