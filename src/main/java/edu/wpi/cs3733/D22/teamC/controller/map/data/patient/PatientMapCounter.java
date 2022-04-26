package edu.wpi.cs3733.D22.teamC.controller.map.data.patient;

import edu.wpi.cs3733.D22.teamC.controller.map.data.MapCounter;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

public class PatientMapCounter extends MapCounter {
    // Contants
    private new final String COUNTER_PATH = "view/map/nodes/patient/counter.fxml";

    public PatientMapCounter(Location location) {
        super(location);
    }

    @Override
    public String getView() {
        return COUNTER_PATH;
    }
}
