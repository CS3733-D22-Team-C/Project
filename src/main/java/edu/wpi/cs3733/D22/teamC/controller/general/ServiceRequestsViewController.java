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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceRequestsViewController implements Initializable {
    public static final String MEDICAL_EQUIPMENT_CREATE_PATH = "view/service_request/medical_equipment/create_form.fxml";
    public static final String LAB_SYSTEM_CREATE_PATH = "view/service_request/lab_system/create_form.fxml";
    public static final String MEDICINE_DELIVERY_CREATE_PATH = "view/service_request/medicine_delivery/create_form.fxml";
    public static final String SANITATION_CREATE_PATH = "view/service_request/sanitation/create_form.fxml";
    public static final String FACILITY_MAINTENANCE_CREATE_PATH = "view/service_request/facility_maintenance/create_form.fxml";
    public static final String SECURITY_CREATE_PATH = "view/service_request/security/create_form.fxml";


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
    void onSelectButton(ActionEvent event) {
        switch (serviceType.getValue()) {
            case "Medical Equipment":
                App.instance.setView(MEDICAL_EQUIPMENT_CREATE_PATH);
                break;
            case "Facility Maintenance":
                App.instance.setView(FACILITY_MAINTENANCE_CREATE_PATH);
                break;
            case "Lab System":
                App.instance.setView(LAB_SYSTEM_CREATE_PATH);
                break;
            case "Medicine Delivery":
                App.instance.setView(MEDICINE_DELIVERY_CREATE_PATH);
                break;
            case "Sanitation":
                App.instance.setView(SANITATION_CREATE_PATH);
                break;
            case "Security":
                App.instance.setView(SECURITY_CREATE_PATH);
                break;
            default:
        }
    }
}
