package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapController;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.floor.FloorManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class Manager<T extends IDEntity> {
        // Variables
    // Tracks the currently selected object for this manager.
    protected T current;
    // All data for this manager. Used for managers with data preservation for stashing changes.
    protected List<T> all;
    // On change events for the manager to invoke when current changes.
    public List<BiConsumer<T, T>> onChangeCurrentEvents = new ArrayList<>();

        // References
    // A reference to the overall map view controller.
    protected MapViewController mapViewController;


    public Manager(MapViewController mapViewController) {
        this.mapViewController = mapViewController;
    }

    //#region Current Object Manipulation
        /**
         * Changes the currently tracked object for this manager.
         * @param object The new object to be tracked by this manager.
         */
        public void changeCurrent(T object) {
            onChangeCurrentEvents.forEach(event -> event.accept(current, object));
            current = object;
        }

        public T getCurrent() {
            return current;
        }
    //#endregion

    //#region Object Manipulation
        public List<T> getAll() {
            return all;
        }

        public T getByID(String id) {
            List<T> objs = all.stream().filter(obj -> obj.getID().equals(id)).collect(Collectors.toList());
            return objs.size() > 0 ? objs.get(0) : null;
        }

        public void addObject(T object) {
            all.add(object);
        }

        public void removeObject(T object) {
            all.remove(object);
            if (current == object) changeCurrent(null);
        }
    //#endregion

    //#region Getters
        public MapViewController getMapViewController() {
            return mapViewController;
        }

        public MapController getMapController() {
            return mapViewController.getMapController();
        }
    //#endregion
}
