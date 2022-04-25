package edu.wpi.cs3733.D22.teamC.controller.service_request.landing_page;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceException;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestAPIScrollPane implements Initializable {

    Label[] allLabels;

    //Name labels
    @FXML private Label teamC;
    @FXML private Label api2;
    @FXML private Label api3;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allLabels = new Label[]{teamC, api2, api3};
    }

    /**
     * Sets the visibility of the name labels
     * @param canSee true if you can see the names, false if you can't see the names.
     */
    protected void changeNameVisibility(boolean canSee) {
        for (Label label : allLabels) {
            label.setVisible(canSee);
        }
    }

    @FXML
    void clickFacilityMaintenance() throws ServiceException, IOException {
        TeamCAPI tC = new TeamCAPI();
        tC.setOwner(App.instance.getStage());
        tC.run(50, 50, 1000, 1000, "../../css/base.css", "Floor 1", "Floor 2");
    }

    //TODO: Add other APIs here
}
