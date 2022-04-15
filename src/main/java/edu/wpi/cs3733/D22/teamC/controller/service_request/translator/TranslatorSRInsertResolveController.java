package edu.wpi.cs3733.D22.teamC.controller.service_request.translator;

import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TranslatorSRInsertResolveController extends InsertServiceRequestResolveController<TranslatorSR>, Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void updateServiceRequest(TranslatorSR serviceRequest) {
        
    }

    @Override
    public DAO<TranslatorSR> createServiceRequestDAO() {
        return null;
    }


}
