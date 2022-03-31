package edu.wpi.cs3733.D22.teamC.controller.general;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import javax.management.Query;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class serviceRequest implements Initializable {

    //Buttons
    @FXML private JFXButton selectButton;
    @FXML private JFXTreeTableView<ServiceRequestTable> table;
    @FXML private JFXComboBox<String> serviceType;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {



        //service request dropdown
        serviceType.getItems().add("Medical Equipment");
        serviceType.getItems().add("Facility Maintenance");
        serviceType.getItems().add("Lab System");
        serviceType.getItems().add("Medicine Delivery");
        serviceType.getItems().add("Sanitation");
        serviceType.getItems().add("Security");

        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAOImpl();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAllServiceRequests();

        ObservableList<ServiceRequestTable> METList = FXCollections.observableArrayList();
        final TreeItem<ServiceRequestTable> root = new RecursiveTreeItem<ServiceRequestTable>(METList, RecursiveTreeObject::getChildren);
        ServiceRequestTable.createTableColumns(table);

        table.setRoot(root);
        table.setShowRoot(false);



        for (ServiceRequest serviceRequest  :  serviceRequests)
        {
            ServiceRequestTable serviceRequestTable = new ServiceRequestTable(serviceRequest);
            METList.add(serviceRequestTable);
        }
    }
    @FXML
    void onSelectButton(ActionEvent event) { // TODO: Set views for every service request
        // TODO: Make it so that we just use constants instead of having the paths written fully
        switch (serviceType.getValue()){
            case "Medical Equipment":
                App.instance.setView("view/service_request/medical-equipment-view.fxml");
                break;
            case "Facility Maintenance":
                App.instance.setView("view/service_request/facility-maintenance.fxml");
                break;
            case "Lab System":
                App.instance.setView("view/service_request/lab-system-view.fxml");
                break;
            case "Medicine Delivery":
                App.instance.setView(App.instance.MEDICINE_DELIVERY);
                break;
            case "Sanitation":
                App.instance.setView("view/service_request/sanitation-view.fxml");
                break;
            case "Security":
                App.instance.setView("view/service_request/security-service-view.fxml");
                break;
            default:
        }

    }


}
