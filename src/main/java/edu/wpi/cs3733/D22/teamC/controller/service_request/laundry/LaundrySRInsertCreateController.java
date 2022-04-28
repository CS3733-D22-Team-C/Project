package edu.wpi.cs3733.D22.teamC.controller.service_request.laundry;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.laundry.LaundrySRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LaundrySRInsertCreateController implements InsertServiceRequestCreateController<LaundrySR>, Initializable {
    @FXML
    private SearchableComboBox<LaundrySR.LaundryType> laundryType;
    @FXML
    private TextField quantity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (LaundrySR.LaundryType laundry : LaundrySR.LaundryType.values()) {
            laundryType.getItems().add(LaundrySR.LaundryType.valueOf(laundry.toString()));
        }
    }

    @Override
    public void clearFields() {
        laundryType.valueProperty().setValue(null);
        quantity.setText(null);
    }

    @Override
    public LaundrySR createServiceRequest() {
        LaundrySR laundrySR = new LaundrySR();

        laundrySR.setLaundryType(LaundrySR.LaundryType.valueOf(String.valueOf(laundryType.getValue())));
        laundrySR.setQuantity(quantity.getText());

        return laundrySR;
    }

    @Override
    public DAO<LaundrySR> createServiceRequestDAO() {
        return new LaundrySRDAO();
    }

    @Override
    public ServiceRequestTableDisplay<LaundrySR> setupTable(JFXTreeTableView<?> table) {
        return new LaundrySRTableDisplay(table);
    }

    @Override
    public boolean requiredFieldsPresent(){
        if(laundryType.getValue() == null)
            return false;
        return !quantity.getText().equals("");
    }

    public LaundrySR createNewServiceRequest(){
        return new LaundrySR();
    }



}

