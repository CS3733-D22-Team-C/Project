package edu.wpi.cs3733.D22.teamC.controller.map.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Manager<T> {
    // Variables

    // Tracks the currently selected object for this manager.
    T current;

    // Tracks any already loaded data for this manager.
    List<T> loaded;

    // On change events for the manager to invoke when current .
    List<BiConsumer<T, T>> onCurrentChangeEvents = new ArrayList<>();

    // References

    // List of map nodes of this type being display.
    List<MapNode<T>> mapNodes = new ArrayList<>();

    /**
     * Changes the currently tracked object for this manager.
     * @param object The new object to be tracked by this manager.
     */
    public void changeCurrent(T object) {
        onCurrentChangeEvents.forEach(event -> event.accept(current, object));
        current = object;
    }
}
