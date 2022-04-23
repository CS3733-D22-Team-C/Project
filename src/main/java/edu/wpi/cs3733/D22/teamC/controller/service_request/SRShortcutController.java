package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.event.ActionEvent;

import java.sql.Timestamp;

public class SRShortcutController extends BaseServiceRequestResolveController {

    @Override
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.MAP_SIDEBAR);
    }


}