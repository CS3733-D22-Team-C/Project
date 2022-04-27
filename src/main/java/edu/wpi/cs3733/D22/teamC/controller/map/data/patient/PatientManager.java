package edu.wpi.cs3733.D22.teamC.controller.map.data.patient;

import edu.wpi.cs3733.D22.teamC.controller.map.FloorMapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapCounter;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class PatientManager extends ManagerMapNodes<Patient> {
    // Constants
    private final static Pair<Double, Double> COUNTER_OFFSET = new Pair<>(-12.5, 10.0);

    // Variables
    List<MapCounter> counters = new ArrayList<>();
    BiConsumer<Floor, Floor> onChangeFloorEvent = (f1, f2) -> this.drawCounters();

    public PatientManager(MapViewController mapViewController) {
        super(mapViewController);

        mapViewController.getFloorManager().onChangeCurrentEvents.add(onChangeFloorEvent);

        drawCounters();
    }

    public void shutdown() {
        deleteCounters();
    }

    @Override
    public void removeNode(MapNode<Patient> mapNode) {}

    //#region Object Manipulation
        public List<Patient> getAllByLocation(Location location) {
            return new PatientDAO().getPatientByLocation(location.getID());
        }
    //#endregion

    //#region Counters
        public void showCounters(boolean show) {
            counters.forEach(mapCounter -> mapCounter.setActive(show));
        }

        private void drawCounters() {
            for (Location location : getMapViewController().getLocationManager().getAll()) {
                // Load Counter
                MapCounter counter = new PatientMapCounter(location);
                counters.add(counter);

                // Set Counter Location
                LocationMapNode locationMapNode = (LocationMapNode) getMapViewController().getLocationManager().getByLocation(location);
                Group contextGroup = locationMapNode.getContextGroup();
                counter.setParent(contextGroup);
                counter.setPosition(COUNTER_OFFSET.getKey(), COUNTER_OFFSET.getValue());

                // Set Counter
                counter.setCount(getAllByLocation(location).size());

                // Set On Click Functionality
                counter.onClickEvent = (mapCounter, event) -> {
                    mapViewController.getLocationManager().unfocus();
                    ((LocationMapNode) mapViewController.getLocationManager().getByLocation(mapCounter.getLocation())).onMouseClickedNode(event);
                    ((FloorMapViewController) mapViewController).getLocationInfoController().setCurrentTab(3);
                    locationMapNode.getNode().toFront();
                };
            }

            showCounters(((FloorMapViewController) mapViewController).getMapControlsController().getCounterChecked());
        }

        private void deleteCounters() {
            counters.forEach(MapCounter::delete);
            counters = new ArrayList<>();
        }

        public void updateCounter(Location location) {
            MapCounter counter = getCounterByLocation(location);
            if (counter != null) counter.setCount(getAllByLocation(location).size());
        }

        private MapCounter getCounterByLocation(Location location) {
            List<MapCounter> filtered = counters.stream().filter(counter -> counter.getLocation().getID().equals(location.getID())).collect(Collectors.toList());
            if (filtered.size() > 0) return filtered.get(0);
            return null;
        }
    //#endregion
}
