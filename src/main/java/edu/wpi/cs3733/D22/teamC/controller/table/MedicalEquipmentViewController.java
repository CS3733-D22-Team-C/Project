package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.builders.NotificationBuilder;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.medical_equipment.MedicalEquipmentTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicalEquipmentViewController extends InsertTableViewController<MedicalEquipment> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private SearchableComboBox<String> locationID;
    @FXML private ComboBox<MedicalEquipment.EquipmentType> typeComboBox;
    @FXML private TextField number;
    @FXML private ComboBox<MedicalEquipment.EquipmentStatus> statusComboBox;//
    @FXML Label title;

    @FXML JFXButton mapViewButton;

    Location location;
    private ValidationSupport validation;

    public void initialize(URL location, ResourceBundle resources) {

        SVGParser svgParser = new SVGParser();
        String locationIcon = svgParser.getPath("static/icons/location_icon.svg");
        SVGGlyph locationContent = new SVGGlyph(locationIcon);
        locationContent.setSize(20);
        mapViewButton.setGraphic(locationContent);


        title.setText("Add Equipment");
        locationID.setEditable(false);

        //make a list of roles from the enum and put it into the combo box
        typeComboBox.getItems().setAll(MedicalEquipment.EquipmentType.values());
        statusComboBox.getItems().setAll(MedicalEquipment.EquipmentStatus.values());

        validation = new ValidationSupport();
        validation.registerValidator(locationID, Validator.createEmptyValidator("location required"));
        validation.registerValidator(typeComboBox, Validator.createEmptyValidator("type required"));
        validation.registerValidator(number, Validator.createEmptyValidator("number required"));
        validation.registerValidator(statusComboBox, Validator.createEmptyValidator("status required"));
        validation.setErrorDecorationEnabled(false);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public MedicalEquipment setValues(MedicalEquipment object) {
        //fields in table
        object.setStatus(statusComboBox.getValue());
        object.setLocation(location);
        object.setEquipmentType(typeComboBox.getValue());
        object.setTypeNumber(Integer.parseInt(number.getText()));
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(MedicalEquipment object) {
        location = (object == null) ? new Location() : object.getLocation();

        title.setText((object == null) ? "Add Equipment" : "Edit Equipment");
        locationID.setValue((object == null) ? "" : location.getShortName());
        typeComboBox.setValue((object == null) ? null : object.getEquipmentType());
        number.setText(String.valueOf((object == null) ? "" : object.getTypeNumber()));
        statusComboBox.setValue((object == null) ? null : object.getStatus());

    }
    //#endregion

    public boolean checkFieldsFilled() {

        validation.setErrorDecorationEnabled(true);
        if (locationID.getValue().equals("")) return false;
        return !(typeComboBox.getValue()==null
                || number.getText().equals("")
                || statusComboBox.getValue()==null);
    }

    public void setLocation(Location location) {
        this.location = location;

        String locationName = "";
        if (location != null) {
            locationName = location.getShortName();
        }

        locationID.setValue(locationName);
    }

    //#region Abstraction
    public MedicalEquipment createObject() {
        return new MedicalEquipment();
    }

    public TableDisplay<MedicalEquipment> createTableDisplay(JFXTreeTableView table){
        TableDisplay<MedicalEquipment> tableDisplay = new MedicalEquipmentTableDisplay(table);

        // Query Database
        MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
        List<MedicalEquipment> medicalEquipments = medicalEquipmentDAO.getAll();

        // Add Table Entries
        medicalEquipments.forEach(tableDisplay::addObject);

        return tableDisplay;
    }

    public MedicalEquipmentDAO createDAO() {
        return new MedicalEquipmentDAO();
    }

    @Override
    public String getObjectName() {
        return "Medical Equipment";
    }

    //#endregion

    //#region FXML Events
        @FXML
        void clickConfirm(ActionEvent event) {
                if (checkFieldsFilled()) {
                    if (parentController.currentObj == null) {
                        if (!medicineExists(typeComboBox.getValue(), Integer.parseInt(number.getText()))){
                            addObject();
                            parentController.setCurrentObj(null);
                            parentController.setRemoveDisable(true);
                        } else {
                            String createdSRNotification = "Medical Equipment Type and Number Already Exists";
                            NotificationBuilder.createNotification("Already Exists", createdSRNotification);
                        }
                    }
                    else{
                        if ((!parentController.currentObj.getEquipmentType().equals(typeComboBox.getValue()))
                            || !(parentController.currentObj.getTypeNumber()== Integer.parseInt(number.getText()))){
                            //this code runs if user changed the tpye or number. We must check that chages are OK

                            if (!medicineExists(typeComboBox.getValue(), Integer.parseInt(number.getText()))){
                                updateObject();
                            } else {
                                String createdSRNotification = "Cannot Modify to Already Existing Type-Number Combo";
                                NotificationBuilder.createNotification("Already Exists", createdSRNotification);
                            }
                        } else {
                            updateObject();
                        }
                    }
                    validation.setErrorDecorationEnabled(false);
                }

        }


        @FXML
        void onFieldUpdated() {
            if (!number.getText().matches("\\d*"))
                number.setText("");
        }

        @FXML
        void goToMapView() {
            new MapSelectorWindow(this::setLocation);
        }
    //#endregion

    private boolean medicineExists(MedicalEquipment.EquipmentType medType, int medNumber){
        List<MedicalEquipment> medicalEquipmentList = new MedicalEquipmentDAO().getAll();

        for (MedicalEquipment a_medicalEquipment : medicalEquipmentList){
            if ((a_medicalEquipment.getEquipmentType().equals(medType)) &&
                    (a_medicalEquipment.getTypeNumber() == medNumber)){
                //true as in the medicine already EXISTS!
                return true;
            }
        }
        return false;
    }
}