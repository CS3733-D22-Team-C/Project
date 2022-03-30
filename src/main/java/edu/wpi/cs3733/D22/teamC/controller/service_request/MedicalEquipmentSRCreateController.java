package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentTable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRCreateController implements Initializable {

    //Fields
    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField equipID;
    @FXML private TextField location;

    //Dropdowns

    @FXML private JFXComboBox<String> priority;
    @FXML private JFXComboBox<String> status;
    @FXML private JFXComboBox<String> equipType;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    //For table
    @FXML private JFXTreeTableView<MedicalEquipmentTable> table;
    ObservableList<MedicalEquipmentTable> METList = FXCollections.observableArrayList();
    final TreeItem<MedicalEquipmentTable> root = new RecursiveTreeItem<MedicalEquipmentTable>(METList, RecursiveTreeObject::getChildren);


    ObservableList<MedicalEquipmentTable> data;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //For equipment type drop down
        equipType.getItems().add("Bed (20)");
        equipType.getItems().add("Recliners (6)");
        equipType.getItems().add("Portable X-Ray");
        equipType.getItems().add("Infusion Pumps (30)");

        //For priority dropdown
        priority.getItems().add("Low");
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //For status dropdown
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");

        //Columns for table
        JFXTreeTableColumn<MedicalEquipmentTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<MedicalEquipmentTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<MedicalEquipmentTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<MedicalEquipmentTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<MedicalEquipmentTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(80);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().equipmentTypeProperty();
            }
        });
        JFXTreeTableColumn<MedicalEquipmentTable, String> typeIDCol = new JFXTreeTableColumn<>("EquipID");
        typeIDCol.setPrefWidth(80);
        typeIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MedicalEquipmentTable, String> param) {
                return param.getValue().getValue().equipmentIDProperty();
            }
        });

        //Practice classes to add
        MedicalEquipmentTable met1 = new MedicalEquipmentTable("Bed", "15", "123456",
              "Room 202", "Done", "High");
        MedicalEquipmentTable met2 = new MedicalEquipmentTable("Infusion Pump", "35", "392843",
               "Room 305", "Blank", "Low");
        METList.add(met1);
        METList.add(met2);

        //Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, typeCol, typeIDCol);

        table.setRoot(root);
        table.setShowRoot(false);


    }
    @FXML
    void clickGoBack(ActionEvent event) {

    }

    @FXML
    void clickReset(ActionEvent event) {
        //clearing text fields/areas
        assigneeID.clear();
        location.clear();
        equipID.clear();
        description.clear();

        //Clearing combo boxes
        priority.valueProperty().set(null);
        status.valueProperty().set(null);
        equipType.valueProperty().setValue(null);

    }

    @FXML
    MedicalEquipmentServiceRequest clickSubmit(ActionEvent event) {
        MedicalEquipmentServiceRequest medEquip = new MedicalEquipmentServiceRequest();

        //Sets from textFields
        medEquip.setAssigneeID(assigneeID.getText());
        medEquip.setDescription(description.getText());
        medEquip.setLocation(location.getText());

        //Sets from combo boxes
        medEquip.setStatus(status.getValue());
        medEquip.setPriority(priority.getValue());
        medEquip.setEquipmentType(equipType.getValue());

        //Dealing with the equipment type and the enumerator
        int type = medEquip.getEquipEnum(equipType.getValue());
        String num = equipID.getText();
        medEquip.setEquipmentID(type + num);
        medEquip.setMet();
        clickReset(event);

        METList.add(medEquip.getMet());
        System.out.println(medEquip.getAssigneeID());
        System.out.println(medEquip.getStatus());
        System.out.println(medEquip.getEquipmentID());
        return medEquip;

    }


}
