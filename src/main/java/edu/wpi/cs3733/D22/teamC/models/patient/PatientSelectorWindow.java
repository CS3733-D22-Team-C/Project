package edu.wpi.cs3733.D22.teamC.models.patient;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.models.generic.SelectorWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class PatientSelectorWindow extends SelectorWindow<Patient> implements Initializable {

    // FXML
    @FXML
    private JFXTreeTableView table;

    // Variables
    private PatientTableDisplay tableDisplay;
    private Patient activePatient;

    public PatientSelectorWindow(Consumer<Patient> consumer) {
        super(consumer);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Query Database
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        // Initialize Table
        tableDisplay = new PatientTableDisplay(table);
        patients.forEach(tableDisplay::addObject);

        setRowInteraction();
    }

    protected void setRowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<PatientTableDisplay.PatientTableEntry> row = new TreeTableRow<PatientTableDisplay.PatientTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    activePatient = (Patient) row.getItem().object;

                    if (event.getClickCount() == 2) {
                        // Double Click shortcut back to base page
                        onSelectionMade(activePatient);
                    }
                }
            });

            return row ;
        });
    }

    //#region FXML Events
    @FXML
    void clickSelect(ActionEvent event) {
        onSelectionMade(activePatient);
    }
    //#endregion

    @Override
    protected String getView() {
        return "view/selector/patient_table.fxml";
    }
}




