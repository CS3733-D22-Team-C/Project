package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;

public class FromDashBaseServiceRequestResolveController extends BaseServiceRequestResolveController{

    @Override
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.MY_TASKS_PATH);
    }
}
