package edu.wpi.cs3733.D22.teamC.controller.service_request.laundry;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySRDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LaundrySRInsertResolveController extends InsertServiceRequestResolveController<LaundrySR> implements Initializable {
    @FXML
    private SearchableComboBox<String> laundryType;

    @FXML
    private TextField quantity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sanitation Type dropdown
        for (LaundrySR.LaundryType laundry : LaundrySR.LaundryType.values()) {
            laundryType.getItems().add(laundry.toString());
        }
    }

    @Override
    public void setup(BaseServiceRequestResolveController<LaundrySR> baseServiceRequestResolveController, LaundrySR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        laundryType.setDisable(!isEditMode);
        quantity.setEditable(isEditMode);

        laundryType.setPromptText(serviceRequest.getLaundryType().toString());
        quantity.setText(serviceRequest.getQuantity());
    }

    public boolean requiredFieldsPresent(){
        if(laundryType.getValue() == null && laundryType.getPromptText().equals(""))
            return false;
        if(quantity.getText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(LaundrySR serviceRequest){
        if(isEditMode){
            if(laundryType.getValue() != null)
                serviceRequest.setLaundryType(LaundrySR.LaundryType.valueOf(laundryType.getValue()));
            serviceRequest.setQuantity(quantity.getText());
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<LaundrySR> createServiceRequestDAO() {
        return new LaundrySRDAO();
    }


    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated();
    }

}
