package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.FloorMapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapCounter;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.models.builders.DialogBuilder;
import edu.wpi.cs3733.D22.teamC.models.builders.NotificationBuilder;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages Medical Equipment data for MapControllers. An optional manager for Map to work with Medical Equipment Functionality.
 */
public class MedicalEquipmentManager extends ManagerMapNodes<MedicalEquipment> {
    // Constants
    public static final String[] TOKEN_PATHS = {
            "view/map/nodes/medical_equipment/bed.fxml",
            "view/map/nodes/medical_equipment/recliner.fxml",
            "view/map/nodes/medical_equipment/xray.fxml",
            "view/map/nodes/medical_equipment/pump.fxml"
    };
    private final static Pair<Integer, Integer> COUNTER_OFFSET = new Pair<>(12, -12);

    // Variables
    List<MapCounter> counters = new ArrayList<>();
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;
    BiConsumer<Floor, Floor> onChangeFloorEvent = (f1, f2) -> this.drawCounters();
    private final MedicalEquipmentToken[] overlays = new MedicalEquipmentToken[MedicalEquipment.EquipmentType.values().length];

    AtomicBoolean updateAutomation = new AtomicBoolean(false);
    AtomicBoolean askUpdateAutomation = new AtomicBoolean(true);

    public MedicalEquipmentManager(FloorMapViewController mapViewController) {
        super(mapViewController);

        mapViewController.getLocationManager().onPreviewLocationEvents.add(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.add(onFocusLocationEvent);
        mapViewController.getFloorManager().onChangeCurrentEvents.add(onChangeFloorEvent);

        // Create Overlays
        List<Location> locations = new LocationDAO().getAll();
        List<String> locationIDs = locations.stream().map(Location::getID).collect(Collectors.toList());
        List<MedicalEquipment> medicalEquipments = new MedicalEquipmentDAO().getAll();
        medicalEquipments = medicalEquipments.stream().filter(medicalEquipment -> !locationIDs.contains(medicalEquipment.getLocationID())).collect(Collectors.toList());

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            App.View<MedicalEquipmentToken> view = App.instance.loadView(TOKEN_PATHS[equipmentType.ordinal()]);

            // Setup Controller
            MedicalEquipmentToken controller = view.getController();
            overlays[equipmentType.ordinal()] = controller;

            // Position Overlay
            getMapController().getBottomOverlay().getChildren().add(view.getNode());
            controller.setType(equipmentType);

            // Set Overlays Counts
            List<MedicalEquipment> medicalEquipmentsByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
            overlays[equipmentType.ordinal()].setMedicalEquipments(medicalEquipmentsByType);

            overlays[equipmentType.ordinal()].setVisible(mapViewController.getMapControlsController().getTokenChecked());
        }

        focusLocation(mapViewController.getLocationManager().getCurrent());
        drawCounters();
    }

    public void shutdown() {
        mapViewController.getLocationManager().onPreviewLocationEvents.remove(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.remove(onFocusLocationEvent);
        mapViewController.getFloorManager().onChangeCurrentEvents.remove(onChangeFloorEvent);

        previewLocation(null);
        focusLocation(null);

        // Delete Overlays
        for (MedicalEquipmentToken overlay : overlays) {
            overlay.root.getChildren().clear();
            getMapController().getBottomOverlay().getChildren().remove(overlay.root);
        }

        // Delete Counters
        deleteCounters();
    }

    //#region Object Manipulation
        public List<MedicalEquipment> getAllByLocation(Location location) {
            return new MedicalEquipmentDAO().getEquipmentByLocation(location.getID());
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

        public void showTokens(boolean show) {
            if (previewed != null) ((MedicalEquipmentNode) previewed).toPreviewMode();
            if (focused != null) ((MedicalEquipmentNode) focused).toFocusMode();
            for (MedicalEquipmentToken overlay : overlays) {
                overlay.setVisible(show);
            }
        }
    //#endregion

    //#region Free Medical Equipment
        public int getFreeCount(MedicalEquipment.EquipmentType equipmentType) {
            return overlays[equipmentType.ordinal()].getCount();
        }

        public MedicalEquipment removeFree(MedicalEquipment.EquipmentType equipmentType) {
            MedicalEquipmentToken overlay = overlays[equipmentType.ordinal()];
            return overlay.removeMedicalEquipment();
        }

        public void addFree(MedicalEquipment medicalEquipment) {
            MedicalEquipmentToken overlay = overlays[medicalEquipment.getEquipmentType().ordinal()];
            overlay.addMedicalEquipment(medicalEquipment);
        }
    //#endregion

    //#region Medical Equipment Manipulation
        public void moveMedicalEquipment(MedicalEquipment medicalEquipment, Location location) {
            if (location != null) {
                String movementMessage = medicalEquipment + " moved to " + location.getLongName() + "";

                // Alert Prompt - Update Medical Equipment
                if (askUpdateAutomation.get()) {
                    boolean updateable = false;
                    String prompt = movementMessage;
                    movementMessage += ".";

                    switch (location.getNodeType()) {
                        case DIRT:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                                prompt += ", which is a DIRT location. Would you like to update this equipment's status to be Dirty?";
                                updateable = true;
                            }
                            break;
                        case STOR:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)) {
                                prompt += ", which is a STOR location. Would you like to update this equipment's status to be Available?";
                                updateable = true;
                            }
                            break;
                        default:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable)) {
                                prompt += ". Would you like to update this equipment's status to be Unavailable?";
                                updateable = true;
                            }
                            break;
                    }

                    if (updateable) {
                        Alert alert = DialogBuilder.createAlertWithOptOut(Alert.AlertType.NONE, "Update Medical Equipment Status",
                                null, prompt, "Do not ask again",
                                param -> askUpdateAutomation.set(!param), ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(btnType -> {
                            if (btnType.getButtonData() == ButtonBar.ButtonData.YES) {
                                updateAutomation.set(true);
                            } else if (btnType.getButtonData() == ButtonBar.ButtonData.NO) {
                                updateAutomation.set(false);
                            }
                        });
                    }
                }

                // Update Medical Equipment
                if (updateAutomation.get()) {
                    switch (location.getNodeType()) {
                        case DIRT:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Dirty);
                            movementMessage += " Its status has been updated to Dirty.";
                            break;
                        case STOR:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Available);
                            movementMessage += " Its status has been updated to Available.";
                            break;
                        default:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Unavailable);
                            movementMessage += " Its status has been updated to Unavailable.";
                            break;
                    }
                }

                // Push Notification
                NotificationBuilder.createNotification("Medical Equipment Moved", movementMessage);

                // Service Request Automation
                switch (medicalEquipment.getEquipmentType()) {
                    case Bed:
                        if (medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                            List<MedicalEquipment> dirtyBeds = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor().getID()).stream().filter(
                                    medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)
                                            && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)
                            ).collect(Collectors.toList());
                            if (dirtyBeds.size() >= 6) {
                                createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("OR Bed Park Floor L1"),
                                        "As there are already " + dirtyBeds.size() + " Unavailable beds on " + mapViewController.getFloorManager().getByID(location.getFloor().getID()).getLongName() + ",");
                            }
                        }
                        break;
                    case Infusion_Pump:
                        if (medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                            List<MedicalEquipment> dirtyPumps = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor().getID()).stream().filter(
                                    medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)
                                            && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)
                            ).collect(Collectors.toList());
                            if (dirtyPumps.size() >= 10) {
                                createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("West Plaza"),
                                        "As there are already " + dirtyPumps.size() + " Unavailable beds on " + mapViewController.getFloorManager().getByID(location.getFloor().getID()).getLongName() + ",");
                            } else {
                                List<MedicalEquipment> cleanPumps = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor().getID()).stream().filter(
                                        medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)
                                                && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)
                                ).collect(Collectors.toList());
                                if (cleanPumps.size() < 5) {
                                    createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("West Plaza"),
                                            ((cleanPumps.size() == 1) ? "As there is only 1 Available pump remaining on " : "As there are" + (cleanPumps.size() == 0 ? "" : " only") + " " + cleanPumps.size() + " Available pumps remaining on ") + mapViewController.getFloorManager().getByID(location.getFloor().getID()).getLongName() + ",");
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            // Make Updates
            medicalEquipment.setLocationID(location == null ? "" : location.getID());
            new MedicalEquipmentDAO().update(medicalEquipment);
            onUpdateDataEvents.forEach(Runnable::run);
        }

        private void createServiceRequest(MedicalEquipment medicalEquipment, Location defaultLocation, String notification) {
            MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

            serviceRequest.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setCreator(App.instance.getUserAccount());
            serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setModifier(App.instance.getUserAccount());

            serviceRequest.setRequestType(ServiceRequest.RequestType.Medical_Equipment);
            serviceRequest.setPriority(ServiceRequest.Priority.Low);
            serviceRequest.setStatus(ServiceRequest.Status.Blank);
            serviceRequest.setDescription("");

            serviceRequest.setEquipmentType(medicalEquipment.getEquipmentType());
            serviceRequest.setEquipment(medicalEquipment);
            serviceRequest.setEquipmentStatus(MedicalEquipmentSR.EquipmentStatus.Available);
            serviceRequest.setLocation(defaultLocation);

            MedicalEquipmentSRDAO dao = new MedicalEquipmentSRDAO();
            dao.insert(serviceRequest);
            serviceRequest = dao.getByID(serviceRequest.getID());

            notification += " Service Request " + serviceRequest + " has been created to clean " + medicalEquipment;
            if (defaultLocation != null) {
                notification += " at " + defaultLocation + ".";
            } else {
                notification += ".";
            }

            // Push Notification
            NotificationBuilder.createNotification("Medical Equipment Service Request", notification);
        }
    //#endregion

    //#region Counters
        public void showCounters(boolean show) {
            counters.forEach(mapCounter -> mapCounter.setActive(show));
        }

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
                counter.getNode().getStyleClass().add("medical-equipment-counter");

                // Set Counter
                counter.setCount(getAllByLocation(location).size());

                // Set On Click Functionality
                counter.onClickEvent = (mapCounter, event) -> {
                    mapViewController.getLocationManager().unfocus();
                    ((LocationMapNode) mapViewController.getLocationManager().getByLocation(mapCounter.getLocation())).onMouseClickedNode(event);
                    ((FloorMapViewController) mapViewController).getLocationInfoController().setCurrentTab(1);
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
