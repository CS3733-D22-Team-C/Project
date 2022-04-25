
package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceExceptionAPI;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamU.MedicineRequest;
import edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.cs3733.D22.teamE.APIApp;
import edu.wpi.cs3733.D22.teamE.Run;
import edu.wpi.cs3733.D22.teamZ.api.API;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;


import java.io.IOException;

public class API_Practice {

    @FXML
    void clickTeamBAPI(ActionEvent event) throws edu.wpi.cs3733.D22.teamB.api.ServiceException, IOException {
        edu.wpi.cs3733.D22.teamB.api.API apiB = new edu.wpi.cs3733.D22.teamB.api.API();
        apiB.run(0,0,700,700,"", "hello", "goodbye");
    }

    @FXML
    void clickTeamZAPI(ActionEvent event) throws ServiceException {
        API apiZ = new API();
        apiZ.run(60,60,700,700,"edu/wpi/cs3733/D22/teamC/css/default.css", "hello", "goodbye");
    }

    @FXML
    void clickTeamCAPI(ActionEvent event) throws ServiceExceptionAPI, IOException {
        TeamCAPI apiC = new TeamCAPI();
        apiC.run(60,60,700,700,"", "hello", "goodbye");
    }
}
